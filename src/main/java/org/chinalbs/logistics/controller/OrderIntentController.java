package org.chinalbs.logistics.controller;

import java.util.List;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.OrderIntent;
import org.chinalbs.logistics.domain.Truck;
import org.chinalbs.logistics.domain.TruckPlan;
import org.chinalbs.logistics.service.OrderIntentService;
import org.chinalbs.logistics.service.TruckPlanService;
import org.chinalbs.logistics.service.UserService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.vo.AddOrderIntentInfo;
import org.chinalbs.logistics.vo.IntentFromGoodsOwner;
import org.chinalbs.logistics.vo.OrderIntentInfo;
import org.chinalbs.logistics.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/intents")
public class OrderIntentController {

	@Autowired
	private OrderIntentService orderIntentService;

    @Autowired
    private TruckPlanService truckPlanService;

    @Autowired
    private UserService userService;
	
	@OperationDefinition(name = "获取抢单列表（车主）")
	@RequestMapping(value = "", params = {"from","max"}, method = RequestMethod.GET)
	public Response<ListSlice<OrderIntentInfo>> listSlice(@RequestParam int from, @RequestParam int max){
		long truckOwnerId = SessionInfo.getCurrent().getUserInfo().getUserId();
		return ResponseHelper.createSuccessResponse(orderIntentService.findListSliceOfTruckOwner(truckOwnerId, from, max));
	}
	
	@OperationDefinition(name = "获取抢单列表（车主客户端）")
	@RequestMapping(value = "/client", params = {"from","max","status","initiator"}, method = RequestMethod.GET)
	public Response<ListSlice<OrderIntentInfo>> listSlice(@RequestParam int from, @RequestParam int max, @RequestParam int status, @RequestParam int initiator){
		long truckOwnerId = SessionInfo.getCurrent().getUserInfo().getUserId();
		return ResponseHelper.createSuccessResponse(orderIntentService.findListSliceOfTruckOwner(truckOwnerId, from, max, status, initiator));
	}
	
	@OperationDefinition(name = "获取抢单列表（货主）")
	@RequestMapping(value = "/{goodsId}", params = {"from","max"}, method = RequestMethod.GET)
	public Response<ListSlice<OrderIntentInfo>> listSlice(@PathVariable Long goodsId, @RequestParam int from, @RequestParam int max){
		return ResponseHelper.createSuccessResponse(orderIntentService.findListSliceOfGoods(goodsId, from, max));
	}
	
	@OperationDefinition(name = "增加抢单")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Response<AddOrderIntentInfo> create(@RequestBody AddOrderIntentInfo info){
		return ResponseHelper.createSuccessResponse(orderIntentService.create(info));
	}
	
	
	@OperationDefinition(name = "增加抢单--货主")
	@RequestMapping(value = "/goodsownerintents", method = RequestMethod.POST)
	public Response<IntentFromGoodsOwner> create(@RequestBody IntentFromGoodsOwner info){
        TruckPlan plan = truckPlanService.findOne(info.getTruckPlanId());
        if(plan == null){
        	return ResponseHelper.createBusinessErrorResponse("该车源已被删除，请选择其他车辆");
        }
        //此处可能是司机的ID
        Long truckOwnerId = plan.getTruckOwnerId();
        OrderIntent intent = orderIntentService.findByTruckOwnerIdAndGoodsId(truckOwnerId, info.getGoodsId());
        if (intent != null) {
        	if(intent.getStatus() == 2){
        		return ResponseHelper.createBusinessErrorResponse("您已拒绝该车，请选择其他车辆或运送其他货物");
			}else if(intent.getStatus() == -1){
				return ResponseHelper.createBusinessErrorResponse("该车主已经取消本次抢单，请选择其他车辆");
			}
        	if(intent.getInitiator() == 1){
        		return ResponseHelper.createBusinessErrorResponse("该车主已经抢单此货物，请选择其他车辆！");
        	}else{
        		return ResponseHelper.createBusinessErrorResponse("您已经选择了该车辆，请联系该车主后，到我的货物中选择该车辆进行发货");
        	}
        }
        UserInfo userInfo = userService.findById(truckOwnerId);
        return ResponseHelper.createSuccessResponse(orderIntentService.create4GoodsOwner(info, userInfo));
	}
	
	@OperationDefinition(name = "获取抢单详细信息")
	@RequestMapping(value = "/{orderIntentId}", method = RequestMethod.GET)
	public Response<OrderIntentInfo> viewOrderIntentInfo(@PathVariable Long orderIntentId){
		return ResponseHelper.createSuccessResponse(orderIntentService.findOneByOrderIntentId(orderIntentId));
	}
	
	@OperationDefinition(name = "修改抢单状态")
	@RequestMapping(value = "/{orderIntentId}", method = RequestMethod.PUT)
	public Response<?> update(@PathVariable Long orderIntentId){
		orderIntentService.update(orderIntentId);
		return ResponseHelper.createSuccessResponse();
	}
	
	@OperationDefinition(name = "删除抢单")
	@RequestMapping(value = "/{orderIntentId}", method = RequestMethod.DELETE)
	public Response<?> delete(@PathVariable Long orderIntentId){
		orderIntentService.delete(orderIntentId);
		return ResponseHelper.createSuccessResponse();
	}
	
	
	@OperationDefinition(name = "获取抢单的车辆列表")
	@RequestMapping(value = "/{orderIntentId}/trucks", method = RequestMethod.GET)
	public Response<List<Truck>> viewOrderIntentTrucks(@PathVariable Long orderIntentId){
		return ResponseHelper.createSuccessResponse(orderIntentService.viewOrderIntentTrucks(orderIntentId));
	}
	
	@OperationDefinition(name = "标示车辆的满载状况（确认用车）-- 货主")
	@RequestMapping(value = "/{orderIntentId}/truckstatus", method = RequestMethod.PUT)
	public Response<?> updateStatus(@PathVariable Long orderIntentId, @RequestBody List<Truck> truck){
		orderIntentService.updateStatus(orderIntentId, truck);
		return ResponseHelper.createSuccessResponse();
	}
	
	@OperationDefinition(name = "修改车辆的剩余容量(确认提货) -- 车主")
	@RequestMapping(value = "/{orderIntentId}/truckcapacity", method = RequestMethod.PUT)
	public Response<?> updateResidualCapacity(@PathVariable Long orderIntentId, @RequestBody List<Truck> truck){
		orderIntentService.updateResidualCapacity(orderIntentId, truck);
		return ResponseHelper.createSuccessResponse();
	}
	
	@OperationDefinition(name = "确认到货--货主")
	@RequestMapping(value = "/{orderIntentId}/confirm", method = RequestMethod.PUT)
	public Response<?> confirmOrders(@PathVariable Long orderIntentId){
		orderIntentService.confirmOrders(orderIntentId);
		return ResponseHelper.createSuccessResponse();
	}
}
