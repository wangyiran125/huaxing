package org.chinalbs.logistics.service;

import java.util.List;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.vo.ChartInfo;
import org.chinalbs.logistics.vo.OrderChartInfo;
import org.chinalbs.logistics.vo.UserChartInfo;

public interface ChartService {

	/**
	 * 获取系统所有运单图形报表数据
	 * @return
	 */
	public ChartInfo getOrderChart();

	/**
	 * 获取系统运单增量图形报表数据
	 * @param type 统计类型，date:昨日新增,day:本周新增，month:当月新增，year:当年新增
	 * @return
	 */
	public ChartInfo getOrderIncreaseChart(String type);

	/**
	 * 获取系统所有用户图形报表数据
	 * @return
	 */
	public ChartInfo getUserChart();

	/**
	 * 获取系统用户增量图形报表数据
	 * @param type 统计类型，date:昨日新增,day:本周新增，month:当月新增，year:当年新增
	 * @return
	 */
	public ChartInfo getUnserIncreaseChart(String type);

	/**
	 * 获取货主发货和车主运货相关数据
	 * @param goodsowner
	 * @param name
	 * @param phone
	 * @param startTime
	 * @param endTime
	 * @param max 
	 * @param from 
	 * @return
	 */
	public ListSlice<UserChartInfo> getUserChartInfo(int role, String name,
			String phone, String startTime, String endTime, int from, int max);
	
	/**
	 *运单相关报表数据
	 * @param status 运单状态
	 * @param type 统计类型，date:昨日新增,day:本周新增，month:当月新增，year:当年新增
	 * @param from
	 * @param max
	 * @return
	 */
	public ListSlice<OrderChartInfo> getOrderByStatusAndType(int status, String type, int from, int max);
	
	/**
	 *数据导出
	 * @param status 运单状态
	 * @param type 统计类型，date:昨日新增,day:本周新增，month:当月新增，year:当年新增
	 * @param from
	 * @param max
	 * @return
	 */
	public List<OrderChartInfo> getOrderByStatusAndType(int status, String type);
	
	/**
	 * 某用户运单相关报表数据
	 * @param userId
	 * @param status 运单状态
	 * @param from
	 * @param max
	 * @return
	 */
	public ListSlice<OrderChartInfo> getOrderByUserId(Long userId, int status, String startTime, String endTime, int from, int max);
	
	/**
	 * 数据导出（某用户）
	 * @param userId
	 * @param status
	 * @return
	 */
	public List<OrderChartInfo> getOrderByUserId(Long userId, String startTime, String endTime, int status);
	
}
