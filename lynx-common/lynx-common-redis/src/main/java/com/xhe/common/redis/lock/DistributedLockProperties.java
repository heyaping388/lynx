package com.xhe.common.redis.lock;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * @author xhe
 * @classname DistributedLockProperties
 * @description
 * @date 2020/4/26 11:49
 */
@Data
@ConfigurationProperties(prefix = "lynx.redis.lock")
public class DistributedLockProperties {
    /**
     * 锁超时时间：默认15S
     */
    private long timeoutMillis = 15000;
    /**
     * 重试次数：默认最大值
     */
    private int retryTimes = Integer.MAX_VALUE;
    /**
     * 重试间隔：默认500毫秒
     */
    private long sleepMillis = 500;
    /**
     * redis锁key前缀：默认lock
     */
    private String prefix = "lock:";
}
