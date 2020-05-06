package com.xhe.common.redis.lock;

/**
 * @classname DistributedLock
 * @description 分布式锁接口
 * @date 2020/4/24 16:48
 * @author xhe
 */
public interface DistributedLock {

	boolean lock(String key);
	
	boolean lock(String key, int retryTimes);
	
	boolean lock(String key, int retryTimes, long sleepMillis);
	
	boolean lock(String key, long expire);
	
	boolean lock(String key, long expire, int retryTimes);
	
	boolean lock(String key, long expire, int retryTimes, long sleepMillis);
	
	boolean releaseLock(String key);

	boolean releaseLock(String prefix, String key);
}
