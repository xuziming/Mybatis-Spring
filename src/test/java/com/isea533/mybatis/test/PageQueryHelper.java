package com.isea533.mybatis.test;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 分页查询助手
 */
public class PageQueryHelper {

	/**
	 * 无分页查询器
	 * @param <T>
	 */
	public interface NoPageQuerier<T> {
		List<T> query();
	}

	/**
	 * 实施分页查询
	 * @param <T>
	 * @param pageNum
	 * @param pageSize
	 * @param querier
	 * @return
	 */
	public static final <T> Pagination<T> doPageQuery(int pageNum, int pageSize, NoPageQuerier<T> querier) {
		// 设置分页信息
		PageHelper.startPage(pageNum, pageSize);

		// 执行实际查询(返回com.github.pagehelper.Page对象)
		List<T> result = querier.query();

		// 获取分页数据
		PageInfo<T> pageInfo = new PageInfo<T>(result);

		// 转换为目标分页对象
		Pagination<T> pagination = transferPageInfoToPagination(pageInfo);

		// 清除分页信息(其实无需clear, PageInterceptor会在finally调用clearPage())
		// PageHelper.clearPage();// TODO === 注意分页信息的生命周期或者说作用域

		return pagination;
	}

	/**
	 * 转换为目标分页对象
	 * @param <T>
	 * @param pageInfo
	 * @return
	 */
	private static final <T> Pagination<T> transferPageInfoToPagination(PageInfo<T> pageInfo) {
		Pagination<T> pagination = new Pagination<T>();

		pagination.setTotal(pageInfo.getTotal());
		pagination.setList(pageInfo.getList());

		pagination.setPageNum(pageInfo.getPageNum());
		pagination.setPageSize(pageInfo.getPageSize());

		pagination.setPages(pageInfo.getPages());
		pagination.setSize(pageInfo.getSize());

		// 由于结果是>startRow的，所以实际的需要+1
		if (pagination.getSize() == 0) {
			pagination.setStartRow(0);
			pagination.setEndRow(0);
		} else {
			pagination.setStartRow(pageInfo.getStartRow() + 1);
			// 计算实际的endRow(最后一页的时候特殊)
			pagination.setEndRow(pagination.getStartRow() - 1 + pagination.getSize());
		}
		return pagination;
	}

}
