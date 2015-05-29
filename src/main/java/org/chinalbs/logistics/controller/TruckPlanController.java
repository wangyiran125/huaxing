package org.chinalbs.logistics.controller;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Goods;
import org.chinalbs.logistics.domain.TruckPlan;
import org.chinalbs.logistics.service.TruckPlanService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.vo.TruckPlanInfo;
import org.chinalbs.logistics.vo.TruckViewInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/truckplans")
public class TruckPlanController {

	@Autowired
	private TruckPlanService truckPlanService;
//	
//	@OperationDefinition(name = "车源列表")
//	@RequestMapping(value = "", params = {"from","max"}, method = RequestMethod.GET)
//	public Response<ListSlice<TruckPlanInfo>> listSlice(@RequestParam int from, @RequestParam int max){
//		return ResponseHelper.createSuccessResponse(truckPlanService.findListSlice(from, max));
//	}
	
	@OperationDefinition(name = "增加新车源")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Response<TruckPlan> create(@RequestBody TruckPlanInfo info){
        if (info.getTruckId() <= 0) {
            return ResponseHelper.createBusinessErrorResponse("请选择正确地车辆！！");
        }
		return ResponseHelper.createSuccessResponse(truckPlanService.create(info));
	}
	
	@OperationDefinition(name = "获取车源详细信息")
	@RequestMapping(value = "/{truckPlanId}", method = RequestMethod.GET)
	public Response<TruckPlanInfo> viewTruckPlanInfo(@PathVariable Long truckPlanId){
		return ResponseHelper.createSuccessResponse(truckPlanService.findOneDetail(truckPlanId));
	}
	
	@OperationDefinition(name = "修改车源信息")
	@RequestMapping(value = "/{truckPlanId}", method = RequestMethod.PUT)
	public Response<TruckPlan> update(@PathVariable Long truckPlanId, @RequestBody TruckPlanInfo info){
		return ResponseHelper.createSuccessResponse(truckPlanService.update(truckPlanId, info));
	}
	
	@OperationDefinition(name = "删除车源")
	@RequestMapping(value = "/{truckPlanId}", method = RequestMethod.DELETE)
	public Response<?> delete(@PathVariable Long truckPlanId){
		truckPlanService.delete(truckPlanId);
		return ResponseHelper.createSuccessResponse();
	}
	
	@OperationDefinition(name = "获取某车主的车源列表")
	@RequestMapping(value = "", method = RequestMethod.GET)
    public Response<ListSlice<TruckPlanInfo>> listSliceByUserId(@RequestParam int from, @RequestParam int max){
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		return ResponseHelper.createSuccessResponse(truckPlanService.findListSlice(userId, from, max));
	}
	
	@OperationDefinition(name = "搜索车源信息")
	@RequestMapping(value = "/search", params = {"from", "max"}, method = RequestMethod.POST)
	public Response<ListSlice<TruckViewInfo>> search(@RequestBody TruckPlanInfo truckPlanInfo, @RequestParam int from, @RequestParam int max){
		return ResponseHelper.createSuccessResponse(truckPlanService.search(truckPlanInfo, from, max));
	}
	
}
