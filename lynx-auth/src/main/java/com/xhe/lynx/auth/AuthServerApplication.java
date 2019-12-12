package com.xhe.lynx.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: xhe
 * @Date: 2019/12/12 15:41
 * @Description:
 */
@EnableDiscoveryClient
@SpringBootApplication
public class AuthServerApplication {

    public static void main(String[] args){
        SpringApplication.run(AuthServerApplication.class,args);
    }
}
