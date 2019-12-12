package com.xhe.lynx.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.xhe.lynx.common.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

/**
 * @Auther: xhe
 * @Date: 2019/12/9 09:49
 * @Description:
 */
@Slf4j
@Configuration
public class SentinelConfig {

    public SentinelConfig(){
        GatewayCallbackManager.setBlockHandler(new UrlBlockHandler());
    }

    /**
     * 限流、熔断统一处理类
     */
    public class UrlBlockHandler implements BlockRequestHandler {
        @Override
        public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
            R result = R.failed("系统繁忙，请稍后再试");
            log.info("触发限流...");
            return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(fromValue(result));
        }
    }
}
