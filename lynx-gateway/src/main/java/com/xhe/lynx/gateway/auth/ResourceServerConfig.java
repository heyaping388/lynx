package com.xhe.lynx.gateway.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;

/**
 * @Auther: xhe
 * @Date: 2019/12/9 10:56
 * @Description: 资源服务器配置
 */
@Configuration
public class ResourceServerConfig {

    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private PermissionAuthManager permissionAuthManager;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        //认证处理器
        ReactiveAuthenticationManager gatewayAuthenticationManager = new GatewayAuthenticationManager(tokenStore);
        JsonAuthenticationEntryPoint entryPoint = new JsonAuthenticationEntryPoint();
        //token转换器
        ServerBearerTokenAuthenticationConverter tokenAuthenticationConverter = new ServerBearerTokenAuthenticationConverter();
        tokenAuthenticationConverter.setAllowUriQueryParameter(true);
        //oauth2认证过滤器
        AuthenticationWebFilter oauth2Filter = new AuthenticationWebFilter(gatewayAuthenticationManager);
        oauth2Filter.setServerAuthenticationConverter(tokenAuthenticationConverter);
        oauth2Filter.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(entryPoint));
        oauth2Filter.setAuthenticationSuccessHandler(new Oauth2AuthSuccessHandler());
        http.addFilterAt(oauth2Filter, SecurityWebFiltersOrder.AUTHENTICATION);

        ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchange = http.authorizeExchange();
        String[] urls = {"/oauth/**","/login.html","/user/login"};
        authorizeExchange.pathMatchers(urls).permitAll();
        authorizeExchange
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .anyExchange()
                    .access(permissionAuthManager)
                .and()
                    .exceptionHandling()
                        .accessDeniedHandler(new JsonAccessDeniedHandler())
                        .authenticationEntryPoint(entryPoint)
                .and()
                    .headers()
                        .frameOptions()
                        .disable()
                .and()
                    .httpBasic().disable()
                    .csrf().disable();
        return http.build();
    }
}
