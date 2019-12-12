package com.xhe.lynx.gateway.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @Auther: xhe
 * @Date: 2019/12/9 11:18
 * @Description:
 */
@Configuration
public class AuthRedisTokenStore {
    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean
    public TokenStore tokenStore(){
        RedisTokenStore tokenStore = new RedisTokenStore(connectionFactory);
        tokenStore.setPrefix("oauth:");
        return tokenStore;
    }
}
