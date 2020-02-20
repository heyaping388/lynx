package com.xhe.common.datasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Auther: xhe
 * @Date: 2019/11/5 13:25
 * @Description: 多数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicContextHolder.peek();
    }

}
