package com.xhe.lynx.auth.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Classname OAuth2ClientConfig
 * @Description 导入OAuth2ClientProperties
 * @Date 2020/3/3 15:06
 * @Created by xhe
 */
@Import(OAuth2ClientProperties.class)
@Configuration
public class OAuth2ClientConfig {
}
