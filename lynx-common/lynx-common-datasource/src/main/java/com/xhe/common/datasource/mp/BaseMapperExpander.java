package com.xhe.common.datasource.mp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author xhe
 * @classname MyBaseMapper
 * @description 自定义BaseMapper增强
 * @date 2020/6/23 10:18
 */
public interface BaseMapperExpander<T> extends BaseMapper<T> {
    /**
     * 以下定义的 4个 default method, copy from {@link com.baomidou.mybatisplus.extension.toolkit.ChainWrappers}
     * @return QueryChainWrapper
     */
    default QueryChainWrapper<T> queryChain() {
        return new QueryChainWrapper<>(this);
    }

    /**
     * LambdaQueryChainWrapper
     * @return LambdaQueryChainWrapper
     */
    default LambdaQueryChainWrapper<T> lambdaQueryChain() {
        return new LambdaQueryChainWrapper<>(this);
    }

    /**
     * UpdateChainWrapper
     * @return UpdateChainWrapper
     */
    default UpdateChainWrapper<T> updateChain() {
        return new UpdateChainWrapper<>(this);
    }

    /**
     * LambdaUpdateChainWrapper
     * @return LambdaUpdateChainWrapper
     */
    default LambdaUpdateChainWrapper<T> lambdaUpdateChain() {
        return new LambdaUpdateChainWrapper<>(this);
    }

    //以下3个是内置的选装件
    /**
     * 单SQL批量插入
     * @param entityList
     * @return
     */
    int insertBatchSomeColumn(List<T> entityList);

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
