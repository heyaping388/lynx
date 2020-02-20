package com.xhe.common.datasource.annotation;

import java.lang.annotation.*;

/**
 * @Auther: xhe
 * @Date: 2019/11/5 13:25
 * @Description: 多数据源注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {
    String value() default "";
}
