package com.xhe.lynx.auth.store;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @Auther: xhe
 * @Date: 2019/12/12 17:37
 * @Description: redis TokenStore配置
 */
@Configuration
public class AuthRedisTokenStore {

    @Bean
    public TokenStore tokenStore(RedisConnectionFactory connectionFactory) {
        RedisTokenStore tokenStore = new RedisTokenStore(connectionFactory);
        tokenStore.setPrefix("oauth:");
        tokenStore.setAuthenticationKeyGenerator(new DefaultAuthenticationKeyGenerator() {
            @Override
            public String extractKey(OAuth2Authentication authentication) {
                return super.extractKey(authentication) + ":";
            }
        });
        return tokenStore;
    }
}
