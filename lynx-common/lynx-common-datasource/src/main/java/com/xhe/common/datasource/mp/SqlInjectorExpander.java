package com.xhe.common.datasource.mp;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.injector.methods.LogicDeleteByIdWithFill;
import java.util.List;

/**
 * @author xhe
 * @classname MySqlInjector
 * @description 自定义增强sql注入器
 * @date 2020/5/13 10:17
 */
public class SqlInjectorExpander extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        //可选增加自定义方法
        /**
         * 以下 3 个为内置选装件
         * 头 2 个支持字段筛选函数
         */
        methodList.add(new InsertBatchSomeColumn());
        methodList.add(new AlwaysUpdateSomeColumnById());
        methodList.add(new LogicDeleteByIdWithFill());
        return methodList;
    }
}
