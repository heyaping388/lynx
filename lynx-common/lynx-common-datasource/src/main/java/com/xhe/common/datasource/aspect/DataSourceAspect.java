package com.xhe.common.datasource.aspect;

import com.xhe.common.datasource.annotation.DataSource;
import com.xhe.common.datasource.config.DynamicContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

/**
 * @Auther: xhe
 * @Date: 2019/11/5 13:25
 * @Description: 多数据源，切面处理类
 */
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class DataSourceAspect {

    @Pointcut("@annotation(com.xhe.common.datasource.annotation.DataSource) " +
            "|| @within(com.xhe.common.datasource.annotation.DataSource)")
    public void dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Class<?> targetClass = point.getTarget().getClass();
        Method method = signature.getMethod();

        DataSource targetDataSource = targetClass.getAnnotation(DataSource.class);
        DataSource methodDataSource = method.getAnnotation(DataSource.class);
        if(targetDataSource != null || methodDataSource != null){
            String value;
            if(methodDataSource != null){
                value = methodDataSource.value();
            }else {
                value = targetDataSource.value();
            }

            DynamicContextHolder.push(value);
            log.debug("set datasource is {}", value);
        }

        try {
            return point.proceed();
        } finally {
            DynamicContextHolder.poll();
            log.debug("clean datasource");
        }
    }
}