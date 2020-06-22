package com.xhe.common.redis.lock;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @classname DistributedLockAutoConfiguration
 * @description redis分布式锁自动配置
 * @date 2020/4/26 09:48
 * @author xhe
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(DistributedLockProperties.class)
public class DistributedLockAutoConfigure {

	@Bean
	@ConditionalOnBean(RedisTemplate.class)
	@ConditionalOnProperty(prefix = "lynx.redis.lock",name = "enabled",havingValue = "true",matchIfMissing = true)
	public DistributedLock redisDistributedLock(RedisTemplate<Object, Object> redisTemplate){
		return new RedisDistributedLock(redisTemplate);
	}
	
}
