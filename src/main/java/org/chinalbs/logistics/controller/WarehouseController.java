package org.chinalbs.logistics.controller;

import java.util.List;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Warehouse;
import org.chinalbs.logistics.service.WarehouseService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.vo.WarehouseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

	@Autowired
	private WarehouseService warehouseService;

	@OperationDefinition(name = "获取仓库列表")
	@RequestMapping(value = "", params = {"from","max"}, method = RequestMethod.GET)
	public Response<ListSlice<Warehouse>> listSlice(@RequestParam int from, @RequestParam int max){
		return ResponseHelper.createSuccessResponse(warehouseService.findListSlice(from, max));
	}
	
	@OperationDefinition(name = "添加新仓库")
	@RequestMapping(value = "",method = RequestMethod.POST)
	public Response<Warehouse> create(@RequestBody WarehouseInfo info){
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		return ResponseHelper.createSuccessResponse(warehouseService.create(userId,info));
	}
	
	@OperationDefinition(name = "查看仓库详细信息")
	@RequestMapping(value = "/{warehouseId}", method = RequestMethod.GET)
	public Response<Warehouse> viewWarehouseInfo(@PathVariable Long warehouseId){
		return ResponseHelper.createSuccessResponse(warehouseService.findOneByWarehouseId(warehouseId));
	}
	
	@OperationDefinition(name = "修改仓库信息")
	@RequestMapping(value = "/{warehouseId}", method = RequestMethod.PUT)
	public Response<Warehouse> update(@PathVariable Long warehouseId, @RequestBody WarehouseInfo info){
		return ResponseHelper.createSuccessResponse(warehouseService.update(warehouseId, info));
	}
	
	@OperationDefinition(name = "删除仓库信息")
	@RequestMapping(value = "/{warehouseId}", method = RequestMethod.DELETE)
	public Response<?> delete(@PathVariable Long warehouseId){
		warehouseService.delete(warehouseId);
		return ResponseHelper.createSuccessResponse();
	}
	
	@OperationDefinition(name = "查看当前用户已发布的仓库")
	@RequestMapping(value = "/currentUser", method = RequestMethod.GET)
	public Response<List<Warehouse>> listSlice(){
		long goodsOwnerId = SessionInfo.getCurrent().getUserInfo().getUserId();
		return ResponseHelper.createSuccessResponse(warehouseService.findByGoodsOwnerId(goodsOwnerId));
	}
	
	@OperationDefinition(name = "搜索仓库信息")
	@RequestMapping(value = "/search", params = {"from", "max"}, method = RequestMethod.POST)
	public Response<ListSlice<Warehouse>> search(@RequestBody Warehouse warehouse, @RequestParam int from, @RequestParam int max){
		return ResponseHelper.createSuccessResponse(warehouseService.search(warehouse, from, max));
	}
}
