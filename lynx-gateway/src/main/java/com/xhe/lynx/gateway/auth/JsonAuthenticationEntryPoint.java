package com.xhe.lynx.gateway.auth;

import com.xhe.lynx.common.core.util.R;
import com.xhe.lynx.gateway.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Auther: xhe
 * @Date: 2019/12/9 10:59
 * @Description: 401未授权异常处理，转换为JSON
 */
@Slf4j
public class JsonAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        R r = R.failed("未登录，请先登录系统！");
        return ResponseUtil.responseWriter(exchange, HttpStatus.UNAUTHORIZED.value(),r);
    }
}
