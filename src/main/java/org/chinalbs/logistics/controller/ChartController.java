package org.chinalbs.logistics.controller;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.service.ChartService;
import org.chinalbs.logistics.utils.Consts;
import org.chinalbs.logistics.vo.ChartInfo;
import org.chinalbs.logistics.vo.OrderChartInfo;
import org.chinalbs.logistics.vo.UserChartInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chart")
public class ChartController {
						
	@Autowired
	private ChartService chartService;
	
	@OperationDefinition(name = "获取订单总数报表数据")
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public Response<ChartInfo> getOrderCountChart(){
		return ResponseHelper.createSuccessResponse(chartService.getOrderChart());
	}
	
	@OperationDefinition(name = "获取用户总数报表数据")
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public Response<ChartInfo> getUserCountChart(){
		return ResponseHelper.createSuccessResponse(chartService.getUserChart());
	}
	
	@OperationDefinition(name = "获取订单增量报表数据")
	@RequestMapping(value = "/order/increase", params={"type"}, method = RequestMethod.GET)
	public Response<ChartInfo> getOrderIncreaseChartByType(@RequestParam String type){
		return ResponseHelper.createSuccessResponse(chartService.getOrderIncreaseChart(type));
	}
	
	@OperationDefinition(name = "获取用户增量报表数据")
	@RequestMapping(value = "/user/increase", params={"type"}, method = RequestMethod.GET)
	public Response<ChartInfo> getUserIncreaseChartByType(@RequestParam String type){
		return ResponseHelper.createSuccessResponse(chartService.getUnserIncreaseChart(type));
	}
	
	@OperationDefinition(name = "获取货主发货报表数据")
	@RequestMapping(value = "/user/goods", params={"from", "max"}, method = RequestMethod.GET)
	public Response<ListSlice<UserChartInfo>> getGoodsChartInfoByRole(@RequestParam int from, @RequestParam int max, String name, String phone, String startTime, String endTime){
		return ResponseHelper.createSuccessResponse(chartService.getUserChartInfo(Consts.Role.GOODSOWNER, name, phone, startTime, endTime, from, max));
	}
	
	@OperationDefinition(name = "获取车主运货报表数据")
	@RequestMapping(value = "/user/orderintent", params={"from", "max"}, method = RequestMethod.GET)
	public Response<ListSlice<UserChartInfo>> getGoodsIntentChartInfoByRole(@RequestParam int from, @RequestParam int max, String name, String phone, String startTime, String endTime){
		return ResponseHelper.createSuccessResponse(chartService.getUserChartInfo(Consts.Role.TRUCKOWNER, name, phone, startTime, endTime, from, max));
	}
	
	@OperationDefinition(name = "获取运单、抢单、已发布货物表数据")
	@RequestMapping(value = "", params = {"from","max","status","type"}, method = RequestMethod.GET)
	public Response<ListSlice<OrderChartInfo>> viewListSlice(
			@RequestParam int from,
			@RequestParam int max,
			@RequestParam int status,
			@RequestParam String type){
		
		return ResponseHelper.createSuccessResponse(chartService.getOrderByStatusAndType(status, type, from, max));
	}
	
	@OperationDefinition(name = "获取某用户运单、抢单、已发布表数据")
	@RequestMapping(value = "/{userid}",params = {"from","max","status"}, method = RequestMethod.GET)
	public Response<ListSlice<OrderChartInfo>> viewListSclice(
			@PathVariable Long userid,
			@RequestParam int from,
			@RequestParam int max,
			@RequestParam int status,
			String startTime,
			String endTime){
		
		return ResponseHelper.createSuccessResponse(chartService.getOrderByUserId(userid, status, startTime, endTime, from, max));
	}
}
