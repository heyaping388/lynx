package com.xhe.lynx.gateway.util;

import com.alibaba.fastjson.JSONObject;
import com.xhe.lynx.common.core.util.R;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.nio.charset.Charset;

/**
 * @Auther: xhe
 * @Date: 2019/11/5 13:25
 * @Description: webflux response工具
 */
@UtilityClass
public class ResponseUtil {
    /**
     * webflux的response返回json对象
     */
    public static Mono<Void> responseWriter(ServerWebExchange exchange, int httpStatus, R r) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.valueOf(httpStatus));
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        DataBuffer buffer = dataBufferFactory.wrap(JSONObject.toJSONString(r).getBytes(Charset.defaultCharset()));
        return response.writeWith(Mono.just(buffer)).doOnError((error) -> {
            DataBufferUtils.release(buffer);
        });
    }
}
