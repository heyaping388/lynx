package com.xhe.lynx.gateway.auth;

import com.xhe.lynx.common.core.util.R;
import com.xhe.lynx.gateway.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Auther: xhe
 * @Date: 2019/12/9 10:59
 * @Description: 403拒绝访问异常处理，转换为JSON
 */
@Slf4j
public class JsonAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException e) {
        R r = R.failed("无权限访问");
        return ResponseUtil.responseWriter(exchange, HttpStatus.FORBIDDEN.value(),r);
    }
}
