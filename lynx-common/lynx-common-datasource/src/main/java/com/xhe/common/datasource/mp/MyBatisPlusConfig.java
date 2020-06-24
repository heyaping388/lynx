package com.xhe.common.datasource.mp;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @classname MyBatisPlusConfig
 * @description MyBatisPlus配置
 * @date 2020/6/23
 * @author xhe
 */
@Slf4j
@Configuration
public class MyBatisPlusConfig {

    @Bean
    public MybatisPlusProperties mybatisPlusProperties(){
        return new MybatisPlusProperties();
    }

    @Bean
    public ISqlInjector iSqlInjector(){
        return new SqlInjectorExpander();
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
