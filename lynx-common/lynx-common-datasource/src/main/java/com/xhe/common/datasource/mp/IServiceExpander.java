package com.xhe.common.datasource.mp;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author xhe
 * @classname MyService
 * @description
 * @date 2020/6/23 11:25
 */
public interface IServiceExpander<T> extends IService<T> {

    /**
     * 单SQL批量插入
     * @param entityList
     * @return
     */
    int insertBatchSomeColumn(List<T> entityList);
    /**
     * 单SQL批量插入
     * @param entityList 对象集合
     * @param batchSize 每次插入条数 默认1000条 大于1000条按1000条分批插入
     * @return
     */
    int insertBatchSomeColumn(List<T> entityList, int batchSize);

    /**
     *根据 ID 更新固定的那几个字段(但是不包含逻辑删除)
     * @param entity 实体对象
     * @return 影响记录数
     */
    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);

    /**
     * 根据 id 逻辑删除数据,并带字段填充功能
     * @param entity 注意入参是 entity !!! ,如果字段没有自动填充,就只是单纯的逻辑删除
     * @return 影响记录数
     */
    int logicDeleteByIdWithFill(T entity);
}
