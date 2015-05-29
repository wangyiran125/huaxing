package org.chinalbs.logistics.controller;


import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.common.json.ReturnCode;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Driver;
import org.chinalbs.logistics.error.CodeException;
import org.chinalbs.logistics.service.DriverService;
import org.chinalbs.logistics.service.TruckService;
import org.chinalbs.logistics.service.UserService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.utils.Consts;
import org.chinalbs.logistics.utils.HTTPUtils;
import org.chinalbs.logistics.utils.MessageDes;
import org.chinalbs.logistics.vo.DriverInfo;
import org.chinalbs.logistics.vo.PasswordInfo;
import org.chinalbs.logistics.vo.RegisterInfo;
import org.chinalbs.logistics.vo.TruckInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/drivers")
public class DriverController {

	@Autowired
	private DriverService driverService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TruckService truckService;

	@OperationDefinition(name = "创建司机用户")
    @RequestMapping(value = "", method = RequestMethod.POST)
	public Response<DriverInfo> create(@RequestBody RegisterInfo registerInfo){
		checkRole();
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
        if (registerInfo.getTruckId() > 0) {
            List<Driver> drivers = driverService.findListByTruckId((long)registerInfo.getTruckId());
            if (drivers.size() > 0) {
                    return ResponseHelper.createBusinessErrorResponse(MessageDes.Truck.TRUCK_NOT_AVAILABLE);
            }
        }
		DriverInfo created = driverService.create(userId, registerInfo);
		if (!HTTPUtils.addUser2(created.getLogisticsUser().getId(),registerInfo)) {
			//凯步关爱 添加用户
			driverService.deletePhysical(userId, created.getLogisticsUser().getId());
			return ResponseHelper.createResponse(ReturnCode.CAPCARE_EXCEPTION, MessageDes.User.CAPCARE_USER_CREATE_ERROR);
		}
		
		//如果车辆本身有设备，需要绑定司机
		TruckInfo truck = truckService.findOneByTruckId(created.getDriver().getTruckId());
		truckService.modifyCapcareDevice(created.getLogisticsUser().getId(),Consts.Capcare.DEVICE_BIND,truck);
		
		return ResponseHelper.createSuccessResponse(created);
	}
	
	@OperationDefinition(name = "修改司机用户信息")
    @RequestMapping(value = "/{driverUserId}", method = RequestMethod.PUT)
	public Response<?> update(@RequestBody Driver driver, @PathVariable Long driverUserId) {
		checkTruckOwner(driverUserId);
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
        if (driver.getTruckId() > 0) {
            List<Driver> drivers = driverService.findListByTruckId((long)driver.getTruckId());
            for (Driver d : drivers) {
                if (d.getUserId() != driverUserId) {
                    return ResponseHelper.createBusinessErrorResponse(MessageDes.Truck.TRUCK_NOT_AVAILABLE);
                }
            }

        }
		driverService.update(userId, driverUserId, driver);
		return ResponseHelper.createSuccessResponse();
	}
	
    @OperationDefinition(name = "列出所属司机用户")
    @RequestMapping(value = "", params = {"from","max"}, method = RequestMethod.GET)
    public Response<ListSlice<DriverInfo>> list(@RequestParam int from, @RequestParam int max) {
    	long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
    	return ResponseHelper.createSuccessResponse(driverService.findByParentId(userId, from, max));
    }
    @OperationDefinition(name = "列出所属司机用户")
    @RequestMapping(value = "", params = {"from","max", "userId"}, method = RequestMethod.GET)
    public Response<ListSlice<DriverInfo>> list(@RequestParam int from, @RequestParam int max, @RequestParam long userId) {
    	return ResponseHelper.createSuccessResponse(driverService.findByParentId(userId, from, max));
    }
	
    @OperationDefinition(name = "删除司机用户")
    @RequestMapping(value = "/{driverUserId}", method = RequestMethod.DELETE)
    public Response<?> delete(@PathVariable Long driverUserId) {
		//checkTruckOwner(driverUserId);
    	long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
    	driverService.delete(userId, driverUserId);
    	return ResponseHelper.createSuccessResponse();

    }
    
	@OperationDefinition(name = "修改自己密码")
	@RequestMapping(value = "/{driverUserId}/password", method = RequestMethod.PUT)
    public Response<?> changePassword(@RequestBody PasswordInfo passwordInfo,@PathVariable Long driverUserId) {
    	boolean changed = driverService.changePassword(SessionInfo.getCurrent().getUserInfo().getUserId(), 
    			driverUserId, passwordInfo.getOldPassword(), passwordInfo.getNewPassword());
    	if (changed) {
    		return ResponseHelper.createSuccessResponse();
    	} else {
    		return ResponseHelper.createBusinessErrorResponse(MessageDes.User.ORIGINAL_PASSWORD_ERROR);
    	}
    }

	private void checkTruckOwner(long driverUserId) {
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		Driver driver = driverService.findByUserId(driverUserId);
		if (userId != driver.getParentUserId()) {
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
