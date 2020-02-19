package com.xhe.lynx.common.core.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Description RestTemplate 配置
 * @Author xhe
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/8/1
 */
@Configuration
public class RestConfig {

    /**
     * 最大链接数
     */
    private final int MAX_TOTAL = 200;
    /**
     * 同路由最大并发数
     */
    private final int MAX_PER_ROUTE = 50;
    /**
     * 读取超时时间 ms
     */
    private final int READ_TIMEOUT = 35000;
    /**
     * 链接超时时间 ms
     */
    private final int CONNECT_TIMEOUT = 10000;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(httpRequestFactory());
        return restTemplate;
    }

    /**
     * httpclient 实现的ClientHttpRequestFactory
     */
    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    /**
     * 使用连接池的 httpclient
     */
    @Bean
    public HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        // 最大链接数
        connectionManager.setMaxTotal(MAX_TOTAL);
        // 同路由并发数20
        connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);

        RequestConfig requestConfig = RequestConfig.custom()
                // 读超时
                .setSocketTimeout(READ_TIMEOUT)
                // 链接超时
                .setConnectTimeout(CONNECT_TIMEOUT)
                // 链接不够用的等待时间
                .setConnectionRequestTimeout(READ_TIMEOUT)
                .build();

        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(3, true))
                .build();
    }
}
