package org.chinalbs.logistics.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.common.json.ReturnCode;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Driver;
import org.chinalbs.logistics.domain.Truck;
import org.chinalbs.logistics.error.CodeException;
import org.chinalbs.logistics.service.DriverService;
import org.chinalbs.logistics.service.TruckService;
import org.chinalbs.logistics.service.UserService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.session.UserInfo;
import org.chinalbs.logistics.utils.Consts;
import org.chinalbs.logistics.utils.MessageDes;
import org.chinalbs.logistics.vo.TruckInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trucks")
public class TruckController {

	@Autowired
	private TruckService truckService;
    @Autowired
    private UserService userService;
    @Autowired
    private DriverService driverService;

	
	@OperationDefinition(name = "获取车辆列表")
	@RequestMapping(value = "", params = {"from","max"}, method = RequestMethod.GET)
	public Response<ListSlice<TruckInfo>> listSlice(@RequestParam int from, @RequestParam int max){
        UserInfo userInfo = SessionInfo.getCurrent().getUserInfo();
        if (userInfo != null && userInfo.getRole() == Consts.Role.DRIVER) {
            Driver driver = driverService.findByUserId(userInfo.getUserId());
            if (driver != null) {
                TruckInfo truck = truckService.findOneByTruckId(driver.getTruckId());
                return ResponseHelper.createSuccessResponse(new ListSlice<TruckInfo>(1, Collections.singletonList(truck)));
            }
        } else {
            long truckOwnerId = SessionInfo.getCurrent().getUserInfo().getUserId();
            return ResponseHelper.createSuccessResponse(truckService.findListSlice(truckOwnerId, from, max));
        }
        return ResponseHelper.createSuccessResponse(new ListSlice<TruckInfo>(0, Arrays.asList(new TruckInfo[0])));
	}
	
	@OperationDefinition(name = "获取可抢单车辆列表")
	@RequestMapping(value = "/intentabletrucks", params = {"from","max"}, method = RequestMethod.GET)
	public Response<ListSlice<TruckInfo>> listIntentPermit(@RequestParam int from, @RequestParam int max){
        UserInfo userInfo = SessionInfo.getCurrent().getUserInfo();
        if (userInfo != null && userInfo.getRole() == Consts.Role.DRIVER) {
            Driver driver = driverService.findByUserId(userInfo.getUserId());
            if (driver != null) {
                TruckInfo truck = truckService.findOneByTruckId(driver.getTruckId());
                if (truck.getIntentPermission() == 1 && truck.getTruckStatus() != Consts.Truck.FULL) {
                    return ResponseHelper.createSuccessResponse(new ListSlice<TruckInfo>(1, Collections.singletonList(truck)));
                }
            }
        } else {
            long truckOwnerId = SessionInfo.getCurrent().getUserInfo().getUserId();
            return ResponseHelper.createSuccessResponse(truckService.findlistIntentPermit(truckOwnerId, from, max));
        }
        return ResponseHelper.createSuccessResponse(new ListSlice<TruckInfo>(0, Arrays.asList(new TruckInfo[0])));
    }
	
	@OperationDefinition(name = "增加新车辆")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Response<Truck> create(@RequestBody TruckInfo info){
        checkRole();
		long truckOwnerId = SessionInfo.getCurrent().getUserInfo().getUserId();
        //检查设备是否已被使用
        if (info.getDeviceKey() != null && !info.getDeviceKey().isEmpty()) {
            List<TruckInfo> infos = truckService.findListByDeviceKey(info.getDeviceKey());
            if (infos.size() > 0) {
                throw new CodeException(ReturnCode.INVALID_PARAMETER, "您输入的设备编码已经被别的车辆使用了,请重新输入！");
            }
        }
		return ResponseHelper.createSuccessResponse(truckService.create(truckOwnerId, info));
	}
	
	@OperationDefinition(name = "获取车辆详细信息")
	@RequestMapping(value = "/{truckId}", method = RequestMethod.GET)
	public Response<TruckInfo> viewTruckInfo(@PathVariable Long truckId){
		return ResponseHelper.createSuccessResponse(truckService.findOneByTruckId(truckId));
	}
	
	@OperationDefinition(name = "修改车辆信息")
	@RequestMapping(value = "/{truckId}", method = RequestMethod.PUT)
	public Response<Truck> update(@PathVariable Long truckId, @RequestBody TruckInfo info){
        checkTruckOwner(truckId);
        //检查设备是否已被使用
        if (info.getDeviceKey() != null && !info.getDeviceKey().isEmpty()) {
            List<TruckInfo> infos = truckService.findListByDeviceKey(info.getDeviceKey());
            for (TruckInfo ti : infos) {
                if (ti.getId().longValue() != truckId) {
                    throw new CodeException(ReturnCode.INVALID_PARAMETER, "您输入的设备编码已经被别的车辆使用了,请重新输入！");
                }
            }
        }
		return ResponseHelper.createSuccessResponse(truckService.update(truckId, info));
	}
	
	@OperationDefinition(name = "删除车辆")
	@RequestMapping(value = "/{truckId}", method = RequestMethod.DELETE)
	public Response<?> delete(@PathVariable Long truckId){
        checkTruckOwner(truckId);
		truckService.delete(truckId);
		return ResponseHelper.createSuccessResponse();
	}
	
	@OperationDefinition(name = "修改车辆状体（一辆或多辆）")
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public Response<List<Truck>> updateTruckStatus(@RequestBody List<TruckInfo> truckInfos){
		return ResponseHelper.createSuccessResponse(truckService.updateTruckStatus(truckInfos));
	}


    private void checkTruckOwner(long truckId) {
        long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
        Truck truck = truckService.findOneByTruckId(truckId);
        if (userId != truck.getTruckOwnerId()) {
            throw new CodeException(ReturnCode.NO_PERMISSION, MessageDes.NO_PERMISSION);
        }
    }

    private void checkRole() {
        int role = SessionInfo.getCurrent().getUserInfo().getRole();
        if (role != Consts.Role.TRUCKOWNER) {
            throw new CodeException(ReturnCode.NO_PERMISSION, MessageDes.Truck.WRONG_ROLE);
        }
    }
}
