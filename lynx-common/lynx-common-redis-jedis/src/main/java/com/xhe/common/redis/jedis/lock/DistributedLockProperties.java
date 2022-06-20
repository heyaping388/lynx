package com.xhe.common.redis.jedis.lock;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xhe
 */
@Data
@ConfigurationProperties(prefix = "spring.redis.lock")
public class DistributedLockProperties {
    /**
     * Lock timeout: default 15 seconds
     */
    private long timeoutMillis = 15000;
    /**
     * Number of retries: default 10
     */
    private int retryTimes = 10;
    /**
     * Retry interval: default 500 ms
     */
    private long sleepMillis = 500;
    /**
     * redis lock prefix
     */
    private String prefix = "lock:";
}
