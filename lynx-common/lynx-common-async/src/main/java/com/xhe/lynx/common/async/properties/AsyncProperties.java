package com.xhe.lynx.common.async.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @classname AsyncProperties
 * @description 异步线程池配置
 * @date 2020/3/20 9:12
 * @author  by xhe
 */
@Data
@ConfigurationProperties(prefix = "lynx.async")
public class AsyncProperties {
    private int maxPoolSize = 200;
    private int corePoolSize = 10;
    private int queueCapacity = 1024;
    private String threadNamePrefix = "asyncTask-";
}
