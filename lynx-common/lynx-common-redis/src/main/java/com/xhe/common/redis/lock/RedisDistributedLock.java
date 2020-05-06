package com.xhe.common.redis.lock;

import io.lettuce.core.SetArgs;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @classname RedisDistributedLock
 * @description redis分布式锁实现
 * @date 2020/4/24 16:48
 * @author xhe
 */
@Slf4j
public class RedisDistributedLock extends AbstractDistributedLock {

	private RedisTemplate<Object, Object> redisTemplate;

	private ThreadLocal<String> lockFlag = new ThreadLocal<String>();

	public static final String UNLOCK_LUA;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

	public RedisDistributedLock(RedisTemplate<Object, Object> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}

	@Override
	public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
		boolean result = setRedis(key, expire);
		// 如果获取锁失败，按照传入的重试次数进行重试
		while((!result) && retryTimes-- > 0){
			try {
				log.debug("lock failed, retrying..." + retryTimes);
				Thread.sleep(sleepMillis);
			} catch (InterruptedException e) {
				return false;
			}
			result = setRedis(key, expire);
		}
		return result;
	}

	private boolean setRedis(String key, long expire) {
		try {
			log.debug("redis 加锁key:{}",key);
			String result = redisTemplate.execute((RedisCallback<String>) connection -> {
				RedisSerializer<String> keySerializer = (RedisSerializer<String>)redisTemplate.getKeySerializer();
				String uuid = UUID.randomUUID().toString();
				lockFlag.set(uuid);
				byte[] keyByte = keySerializer.serialize(key);
				byte[] valueByte = keySerializer.serialize(uuid);
				Object nativeConnection = connection.getNativeConnection();
				//lettuce连接包下 redis 单机模式setnx
				if(nativeConnection instanceof RedisAsyncCommands){
					RedisAsyncCommands commands = (RedisAsyncCommands)nativeConnection;
					//同步方法执行、setnx禁止异步
					return commands
							.getStatefulConnection()
							.sync()
							.set(keyByte,valueByte, SetArgs.Builder.nx().px(expire));
				}
				// lettuce连接包下 redis 集群模式setnx
				if(nativeConnection instanceof RedisAdvancedClusterAsyncCommands){
					RedisAdvancedClusterAsyncCommands<byte[],byte[]> clusterAsyncCommands = (RedisAdvancedClusterAsyncCommands) nativeConnection;
					return clusterAsyncCommands
							.getStatefulConnection()
							.sync()
							.set(keyByte,valueByte, SetArgs.Builder.nx().px(expire));
				}
				return "";
			});
			return "OK".equalsIgnoreCase(result);
		} catch (Exception e) {
			log.error("redis分布式锁加锁异常：", e);
		}
		return false;
	}

	@Override
	public boolean releaseLock(String prefix, String key) {
		// 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
		try {
			String fullKey = prefix+key;
			log.debug("redis 释放锁key:{}",fullKey);
			String args = lockFlag.get();
			// 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
			// spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
			Long result = redisTemplate.execute((RedisCallback<Long>) connection ->
					connection.scriptingCommands().eval(UNLOCK_LUA.getBytes(), ReturnType.INTEGER,1,fullKey.getBytes(Charset.forName("UTF-8")),args.getBytes(Charset.forName("UTF-8"))));
			if(log.isDebugEnabled()){
				log.debug("释放redis锁结果：{}",result != null && result > 0);
			}
			return result != null && result > 0;
		} catch (Exception e) {
			log.error("释放redis分布式锁时发生异常：", e);
		} finally {
			// 清除掉ThreadLocal中的数据，避免内存溢出
			lockFlag.remove();
		}
		return false;
	}
	
}
