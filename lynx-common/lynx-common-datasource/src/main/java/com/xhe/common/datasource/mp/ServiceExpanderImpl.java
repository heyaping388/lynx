package com.xhe.common.datasource.mp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zfq
 * @version 1.0
 * @Description
 * @date 2020年04月16日 14:53
 */
public class ServiceExpanderImpl<M extends BaseMapperExpander<T>, T> extends ServiceImpl<M, T> implements IServiceExpander<T> {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertBatchSomeColumn(List<T> entityList) {
		return this.insertBatchSomeColumn(entityList, DEFAULT_BATCH_SIZE);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertBatchSomeColumn(List<T> entityList, int batchSize) {
		if (batchSize < 1 || batchSize > DEFAULT_BATCH_SIZE) {
			batchSize = DEFAULT_BATCH_SIZE;
		}
		int recordNum = 0;
		ArrayList<T> subList = new ArrayList<>(batchSize);
		for (T t : entityList) {
			if (subList.size() >= batchSize) {
				// 分批插入
				recordNum += this.baseMapper.insertBatchSomeColumn(subList);
				subList = new ArrayList<>(batchSize);
			}
			subList.add(t);
		}
		// 插入剩余的记录
		recordNum += this.baseMapper.insertBatchSomeColumn(subList);
		return recordNum;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int alwaysUpdateSomeColumnById(T entity) {
		return this.baseMapper.alwaysUpdateSomeColumnById(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int logicDeleteByIdWithFill(T entity) {
		return this.baseMapper.logicDeleteByIdWithFill(entity);
	}
}
