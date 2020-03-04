package com.xhe.lynx.common.swagger.annotation;

import com.xhe.lynx.common.swagger.config.SwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;
import java.lang.annotation.*;

/**
 * @author xhe
 * @date 2020/3/4
 * 开启swagger注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerAutoConfiguration.class})
public @interface EnableSwagger2 {
}
