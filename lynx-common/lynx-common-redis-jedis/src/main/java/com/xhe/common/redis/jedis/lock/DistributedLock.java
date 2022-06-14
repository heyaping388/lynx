package com.xhe.common.redis.jedis.lock;
/**
 * @author xhe
 */
public interface DistributedLock {

	boolean lock(String key);
	
	boolean lock(String key, int retryTimes);
	
	boolean lock(String key, int retryTimes, long sleepMillis);
	
	boolean lock(String key, long expire);
	
	boolean lock(String key, long expire, int retryTimes);
	
	boolean lock(String key, long expire, int retryTimes, long sleepMillis);
	
	boolean unLock(String key);

	boolean unLock(String prefix,String key);
}
