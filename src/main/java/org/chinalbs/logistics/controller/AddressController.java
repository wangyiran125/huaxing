package org.chinalbs.logistics.controller;

import java.util.List;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Address;
import org.chinalbs.logistics.service.AddressService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.vo.AddressInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	@OperationDefinition(name = "获取地址列表")
	@RequestMapping(value = "", params = {"from","max"}, method = RequestMethod.GET)
	public Response<ListSlice<Address>> listSlice(@RequestParam int from, @RequestParam int max){
		return ResponseHelper.createSuccessResponse(addressService.findListSlice(from, max));
	}
	
	@OperationDefinition(name = "增加地址")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Response<Address> create(@RequestBody AddressInfo info){
		return ResponseHelper.createSuccessResponse(addressService.create(info));
	}
	
	@OperationDefinition(name = "修改地址")
	@RequestMapping(value = "/{addressId}", method = RequestMethod.PUT)
	public Response<Address> update(@PathVariable Long addressId, @RequestBody AddressInfo info){
		return ResponseHelper.createSuccessResponse(addressService.update(addressId, info));
	}
	
	@OperationDefinition(name = "删除地址信息")
	@RequestMapping(value = "/{addressId}", method = RequestMethod.DELETE)
	public Response<?> delete(@PathVariable Long addressId){
		addressService.delete(addressId);
		return ResponseHelper.createSuccessResponse();
	}
	
	@OperationDefinition(name = "获取某用户地址列表")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Response<List<Address>> viewAddress(){
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		return ResponseHelper.createSuccessResponse(addressService.findByUserId(userId));
	}
	
	@OperationDefinition(name = "修改地址表的默认出发地或到达地标志")
	@RequestMapping(value = "/{addressId}/{flag}", method = RequestMethod.PUT)
	public Response<Address> updateFlag(@PathVariable Long addressId, @PathVariable int flag){
		return ResponseHelper.createSuccessResponse(addressService.updateAddressFlag(addressId, flag));
	}
	
	@OperationDefinition(name = "查找该用户的默认地址")
	@RequestMapping(value = "/{flag}", method = RequestMethod.GET)
	public Response<Address> viewDefaultAddress(@PathVariable int flag){
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		return ResponseHelper.createSuccessResponse(addressService.findByUserIdAndFlag(userId, flag));
	}
}
