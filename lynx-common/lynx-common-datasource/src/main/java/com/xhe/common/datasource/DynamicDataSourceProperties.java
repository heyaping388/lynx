package com.xhe.common.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther: xhe
 * @Date: 2019/11/5 13:25
 * @Description: 多数据源属性
 */
@ConfigurationProperties(prefix = "dynamic")
public class DynamicDataSourceProperties {
    private Map<String, DataSourceProperties> datasource = new LinkedHashMap<>();

    public Map<String, DataSourceProperties> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, DataSourceProperties> datasource) {
        this.datasource = datasource;
    }
}
