package com.xhe.lynx.gateway.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Auther: xhe
 * @Date: 2019/12/9 17:00
 * @Description:
 */
@Slf4j
@Component
public class PermissionAuthManager implements ReactiveAuthorizationManager<AuthorizationContext> {


    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        return authentication.map(auth -> {
            ServerWebExchange exchange = authorizationContext.getExchange();
            ServerHttpRequest request = exchange.getRequest();
            boolean isPermission = this.hasPermission(auth, request.getMethodValue(), request.getURI().getPath());
            log.info("权限认证：{}",isPermission);
            return new AuthorizationDecision(isPermission);
        }).defaultIfEmpty(new AuthorizationDecision(false));
    }

    private boolean hasPermission(Authentication authentication,String methodValue,String path){
        log.info("当前方法值：{} 访问路径：{} ",methodValue,path);
        return true;
    }
}
