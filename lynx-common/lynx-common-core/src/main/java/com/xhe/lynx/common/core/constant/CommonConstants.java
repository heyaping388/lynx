package com.xhe.lynx.common.core.constant;

/**
 * @Auther: xhe
 * @Date: 2019/11/5 13:25
 * @Description: 通用常量
 */
public interface CommonConstants {

	/**
	 * 删除
	 */
	String STATUS_DEL = "1";
	/**
	 * 正常
	 */
	String STATUS_NORMAL = "0";
	/**
	 * 锁定
	 */
	String STATUS_LOCK = "9";
	/**
	 * 菜单
	 */
	String MENU = "0";

	/**
	 * 菜单树根节点
	 */
	Integer MENU_TREE_ROOT_ID = -1;
	/**
	 * 编码
	 */
	String UTF8 = "UTF-8";
	/**
	 * 成功标记
	 */
	Integer SUCCESS = 0;
	/**
	 * 失败标记
	 */
	Integer FAIL = 1;

	/**
	 * 当前页码
	 */
	String PAGE = "page";
	/**
	 * 每页显示记录数
	 */
	String LIMIT = "limit";
	/**
	 * 排序字段
	 */
	String ORDER_FIELD = "sidx";
	/**
	 * 排序方式
	 */
	String ORDER = "order";
	/**
	 *  升序
	 */
	String ASC = "asc";
}
