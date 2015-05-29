package org.chinalbs.logistics.controller;

import java.util.List;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.common.json.ReturnCode;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Goods;
import org.chinalbs.logistics.error.CodeException;
import org.chinalbs.logistics.domain.GoodsOwner;
import org.chinalbs.logistics.service.GoodsService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.utils.Consts;
import org.chinalbs.logistics.utils.MessageDes;
import org.chinalbs.logistics.vo.GoodsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	private GoodsService goodsService;
	
	@OperationDefinition(name = "获取货物列表")
	@RequestMapping(value = "", params = {"from","max"}, method = RequestMethod.GET)
	public Response<ListSlice<Goods>> listSlice(@RequestParam int from, @RequestParam int max){
		return ResponseHelper.createSuccessResponse(goodsService.findListSlice(from, max));
	}
	
	
	@OperationDefinition(name = "获取待发货物列表")
	@RequestMapping(value = "/waitgoods", params = {"from","max"}, method = RequestMethod.GET)
	public Response<ListSlice<Goods>> listWaitGoodsSlice(@RequestParam int from, @RequestParam int max){
		return ResponseHelper.createSuccessResponse(goodsService.findWaitGoodsListSlice(from, max));
	}
	
	@OperationDefinition(name = "发布货物")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Response<Goods> create(@RequestBody GoodsInfo info){
		checkRole();
		return ResponseHelper.createSuccessResponse(goodsService.create(info));
	}

	@OperationDefinition(name = "获得货物详细信息")
	@RequestMapping(value = "/{goodsId}", method = RequestMethod.GET)
	public Response<Goods> viewGoodsInfo(@PathVariable Long goodsId){
		return ResponseHelper.createSuccessResponse(goodsService.findOneByGoodsId(goodsId));
	}
	
	@OperationDefinition(name = "修改货物信息")
	@RequestMapping(value = "/{goodsId}", method = RequestMethod.PUT)
	public Response<Goods> update(@PathVariable Long goodsId, @RequestBody GoodsInfo info){
		checkGoodsOwner(goodsId);
		return ResponseHelper.createSuccessResponse(goodsService.update(goodsId, info));
	}
	
	@OperationDefinition(name = "删除货物信息")
	@RequestMapping(value = "/{goodsId}", method = RequestMethod.DELETE)
	public Response<?> delete(@PathVariable Long goodsId){
		checkGoodsOwner(goodsId);
		goodsService.delete(goodsId);
		return ResponseHelper.createSuccessResponse();
	}
	
	@OperationDefinition(name = "获取当前用户已发布的货物列表")
	@RequestMapping(value = "/currentUser", params = {"from","max"}, method = RequestMethod.GET)
	public Response<ListSlice<GoodsInfo>> listSliceByUserId(@RequestParam int from, @RequestParam int max){
		long goodsOwnerId = SessionInfo.getCurrent().getUserInfo().getUserId();
		return ResponseHelper.createSuccessResponse(goodsService.findListSlice(goodsOwnerId, from, max));
	}
	
	@OperationDefinition(name = "搜索货物信息")
	@RequestMapping(value = "/search", params = {"from", "max"}, method = RequestMethod.POST)
	public Response<ListSlice<Goods>> search(@RequestBody Goods goods, @RequestParam int from, @RequestParam int max){
		return ResponseHelper.createSuccessResponse(goodsService.search(goods, from, max));
	}
	
	@OperationDefinition(name = "获取货物的默认联系方式联系人")
	@RequestMapping(value = "/defaultinfo", method = RequestMethod.GET)
	public Response<GoodsOwner> viewGoodsInfo(){
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		return ResponseHelper.createSuccessResponse(goodsService.findOneByUserId(userId));
	}
	
	@OperationDefinition(name = "/获取当前用户待发货物列表")
	@RequestMapping(value = "/currentuser/waitgoods", method = RequestMethod.GET)
	public Response<List<Goods>> listWaitGoodsSlice(){
		long goodsOwnerId = SessionInfo.getCurrent().getUserInfo().getUserId();
		return ResponseHelper.createSuccessResponse(goodsService.findWaitGoodsByGoodsOwnerId(goodsOwnerId));
	}

	private void checkGoodsOwner(long goodsId) {
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		Goods goods = goodsService.findOneByGoodsId(goodsId);
		if (userId != goods.getGoodsOwnerId()) {
			throw new CodeException(ReturnCode.NO_PERMISSION, MessageDes.NO_PERMISSION);
		}
	}

	private void checkRole() {
		int role = SessionInfo.getCurrent().getUserInfo().getRole();
		if (role != Consts.Role.GOODSOWNER && role != Consts.Role.WAREHOUSEOWNER) {
			throw new CodeException(ReturnCode.NO_PERMISSION, MessageDes.Goods.WRONG_ROLE);
		}
	}
}
