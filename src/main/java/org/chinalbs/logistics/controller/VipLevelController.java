package org.chinalbs.logistics.controller;

import java.util.List;
import java.util.Map;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.domain.VipLevel;
import org.chinalbs.logistics.service.VipLevelService;
import org.chinalbs.logistics.vo.VipLevelInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vipLevels")
public class VipLevelController {

	@Autowired
	private VipLevelService vipLevelService;
	
	@OperationDefinition(name = "级别列表（车主）")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Response<List<VipLevel>> listVipLevel(){
		return ResponseHelper.createSuccessResponse(vipLevelService.findAll());
	}
	
	@OperationDefinition(name = "添加级别")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Response<VipLevel> create(@RequestBody VipLevelInfo info){
		return ResponseHelper.createSuccessResponse(vipLevelService.create(info));
	}
	
	@OperationDefinition(name = "修改")
	@RequestMapping(value = "/{vipLevelId}", method = RequestMethod.PUT)
	public Response<VipLevel> update(@PathVariable Long vipLevelId, @RequestBody VipLevelInfo info){
		return ResponseHelper.createSuccessResponse(vipLevelService.update(vipLevelId, info));
	}
	
	@OperationDefinition(name = "删除")
	@RequestMapping(value = "/{vipLevelId}", method = RequestMethod.DELETE)
	public Response<?> delete(@PathVariable Long vipLevelId){
		vipLevelService.delete(vipLevelId);
		return ResponseHelper.createSuccessResponse();
	}
	
	@OperationDefinition(name = "获取所有VIP级别延迟时间数据 ")
	@RequestMapping(value = "/items/all", method = RequestMethod.GET)
	public Response<Map<String, Integer>> getAllVipLevelItem() {
		return ResponseHelper.createSuccessResponse(vipLevelService.getAllVipLevelItem());
	}
	
	@OperationDefinition(name = "更新所有VIP级别延迟时间数据 ")
	@RequestMapping(value = "/items", method = RequestMethod.PUT)
    public Response<?> updateScoreItem(@RequestBody Map<String, Integer> items) {
		vipLevelService.updateVipLevelItem(items);
		return ResponseHelper.createSuccessResponse();
	}
}
