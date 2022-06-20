package com.xhe.common.redis.jedis.lock;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author rock.he
 */
public abstract class AbstractDistributedLock implements DistributedLock {

	@Autowired
	private DistributedLockProperties properties;

	@Override
	public boolean lock(String key) {
		return lock(properties.getPrefix()+key,properties.getTimeoutMillis(),properties.getRetryTimes(),properties.getSleepMillis());
	}

	@Override
	public boolean lock(String key, int retryTimes) {
		return lock(properties.getPrefix()+key,properties.getTimeoutMillis(), retryTimes,properties.getSleepMillis());
	}

	@Override
	public boolean lock(String key, int retryTimes, long sleepMillis) {
		return lock(properties.getPrefix()+key,properties.getTimeoutMillis(), retryTimes, sleepMillis);
	}

	@Override
	public boolean lock(String key, long expire) {
		return lock(properties.getPrefix()+key, expire,properties.getRetryTimes(),properties.getSleepMillis());
	}

	@Override
	public boolean lock(String key, long expire, int retryTimes) {
		return lock(properties.getPrefix()+key, expire, retryTimes,properties.getSleepMillis());
	}

	@Override
	public boolean unLock(String key) {
		return unLock(properties.getPrefix(), key);
	}
}
