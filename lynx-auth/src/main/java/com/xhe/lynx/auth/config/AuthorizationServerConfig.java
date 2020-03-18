package com.xhe.lynx.auth.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import java.util.Map;

/**
 * @Auther: xhe
 * @Date: 2019/12/12 15:36
 * @Description:
 */
@Slf4j
@Component
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;

    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients){
        Map<String, OAuth2ClientProperties.Registration> registration = oAuth2ClientProperties.getRegistration();
        Assert.notEmpty(registration,"未配置oauth2 client");
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        registration.forEach((key,client) -> {
            log.info("已加载 client {} : clientId = {} authorizationGrantType = {} clientSecret = {} scope = {}",
                    client.getClientName(),
                    client.getClientId(),
                    client.getAuthorizationGrantType(),
                    client.getClientSecret(),
                    client.getScope());
            builder.withClient(client.getClientId())
                    .authorizedGrantTypes(client.getAuthorizationGrantType().split(","))
                    .secret(passwordEncoder.encode(client.getClientSecret()))
                    .scopes(client.getScope().toArray(new String[client.getScope().size()]))
                    //token有效期24小时
                    .accessTokenValiditySeconds(24 * 60 * 60)
                    //刷新token有效期7天
                    .refreshTokenValiditySeconds(7 * 24 * 60 * 60);
        });
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .authenticationManager(this.authenticationManager)
                .tokenStore(tokenStore);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .allowFormAuthenticationForClients()
                .passwordEncoder(passwordEncoder)
                .checkTokenAccess("isAuthenticated()");
    }
}
