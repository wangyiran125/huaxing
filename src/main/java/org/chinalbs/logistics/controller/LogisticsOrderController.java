package org.chinalbs.logistics.controller;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.LogisticsOrder;
import org.chinalbs.logistics.service.LogisticsOrderService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.vo.HistoryOderInfo;
import org.chinalbs.logistics.vo.OrderDetailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class LogisticsOrderController {
	
	@Autowired
	private LogisticsOrderService logisticsOrderService;

	@OperationDefinition(name = "获得历史运单")
	@RequestMapping(value = "", params = {"from","max"}, method = RequestMethod.GET)
	public Response<ListSlice<HistoryOderInfo>> listSlice(@RequestParam int from, @RequestParam int max){
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		ListSlice<HistoryOderInfo> listSliceHistoryOder = logisticsOrderService.findHistoryOrder(userId, from, max);
		return ResponseHelper.createSuccessResponse(listSliceHistoryOder);

	}
	
	@OperationDefinition(name = "获得运单详细")
	@RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
	public Response<OrderDetailInfo> orderDetail(@PathVariable Long orderId){
		OrderDetailInfo orderDetailInfo = logisticsOrderService.orderDetail(orderId);
       return ResponseHelper.createSuccessResponse(orderDetailInfo);

	}
	
	@OperationDefinition(name = "修改运单--评价")
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public Response<LogisticsOrder> orderEvaluate(@RequestBody LogisticsOrder logisticsOrder){
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		LogisticsOrder newLogisticsOrder = logisticsOrderService.orderEvaluate(logisticsOrder,userId);
       return ResponseHelper.createSuccessResponse(newLogisticsOrder);

	}
	
	
}
