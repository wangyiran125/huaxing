package org.chinalbs.logistics.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.chinalbs.logistics.common.json.ReturnCode;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Driver;
import org.chinalbs.logistics.domain.LogisticsUser;
import org.chinalbs.logistics.domain.Truck;
import org.chinalbs.logistics.error.CodeException;
import org.chinalbs.logistics.repository.DriverRepository;
import org.chinalbs.logistics.repository.LogisticsUserRepository;
import org.chinalbs.logistics.repository.TruckRepository;
import org.chinalbs.logistics.service.DriverService;
import org.chinalbs.logistics.service.TruckService;
import org.chinalbs.logistics.service.UserService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.utils.Consts;
import org.chinalbs.logistics.utils.MessageDes;
import org.chinalbs.logistics.utils.MD5Utils;
import org.chinalbs.logistics.utils.SimplePageable;
import org.chinalbs.logistics.vo.DriverInfo;
import org.chinalbs.logistics.vo.RegisterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DriverServiceImpl implements DriverService{

	
	@Autowired
	private LogisticsUserRepository logisticsUserRepository;
	
	
	@Autowired
	private DriverRepository driverRepository;
	
	@Autowired
	private TruckRepository truckRepository;
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TruckService truckService;
	/*
	 * 创建司机
	 * 创建者角色判断， 用户名重复判断
	 */
	@Override
	public DriverInfo create(Long parentId, RegisterInfo registerInfo) {
         
		if (SessionInfo.getCurrent().getUserInfo().getRole() != Consts.Role.TRUCKOWNER) {
			throw new CodeException(ReturnCode.NO_PERMISSION, MessageDes.User.NO_DRIVER_PERMINSION);
		}		
        if (logisticsUserRepository.findOneByUsername(registerInfo.getUsername()) != null) {
        	//用户名重复
            throw new CodeException(ReturnCode.USERNAME_EXIST, MessageDes.User.USER_DUPLICATE );
        }
		LogisticsUser driverUser = userService.create(registerInfo);
		Driver driver = driverRepository.findOnebyUserId(driverUser.getId());
		return new DriverInfo(driverUser, driver);
	}

	/*
	 * 更新司机
	 * 修改角色判断，所属关系判断
	 */
	@Override
	public DriverInfo update(Long parentId, Long userId, Driver driverParam) {
		
		LogisticsUser user = logisticsUserRepository.findOne(userId);
		Driver driver = driverRepository.findOnebyUserId(userId);
		if (driver == null || user == null) {
			throw new CodeException(ReturnCode.USERNAME_NOT_EXIST, MessageDes.User.USER_NOT_EXISTS);
		}
		
		int role = SessionInfo.getCurrent().getUserInfo().getRole();
		if (role== Consts.Role.TRUCKOWNER && driver.getParentUserId() == parentId) {
			//车主修改司机信息
		}
		else if (role == Consts.Role.DRIVER && parentId == userId) {
			//司机修改自己信息
		}
		else {
			throw new CodeException(ReturnCode.NO_PERMISSION, MessageDes.User.NO_DRIVER_PERMINSION);
		}
		
		//如果车子有变化，需要解绑后，再次绑定
		if (driverParam.getTruckId() != driver.getTruckId()) {
			Truck truck = truckService.findOneByTruckId(driver.getTruckId());
			if (truck != null) {
				truckService.modifyCapcareDevice(driver.getUserId(),Consts.Capcare.DEVICE_UNBIND,truck);
			}
			
			truck = truckService.findOneByTruckId(driverParam.getTruckId());
			if (truck != null) {
				truckService.modifyCapcareDevice(driver.getUserId(),Consts.Capcare.DEVICE_BIND,truck);
			}	
		}
		
		
		driver = makeDriver( driver, driverParam);
		driverRepository.save(driver);

		return new DriverInfo(user, driver);
	}
	
	/*
	 * 列出所属司机用户
	 * 
	 */
	@Override
	public ListSlice<DriverInfo> findByParentId(Long parentId, int from, int max) {
		int role = SessionInfo.getCurrent().getUserInfo().getRole();
		if (role != Consts.Role.TRUCKOWNER && role != Consts.Role.ADMIN) {
			throw new CodeException(ReturnCode.NO_PERMISSION, MessageDes.User.NO_DRIVER_PERMINSION);
        }

        Page<Driver> page = driverRepository.findPagebyparentUserId(new SimplePageable(from, max), parentId);
		List<DriverInfo> driverInfos = new ArrayList<DriverInfo>();
		LogisticsUser user = null;
		if (page != null && page.getSize() > 0) {
			for (Driver driver : page) {
				user = logisticsUserRepository.findOne(driver.getUserId());
				Truck truck = truckRepository.findOne(driver.getTruckId());
				if (truck == null || truck.getLicensePlateNumber() == null) {
					driverInfos.add(new DriverInfo(user, driver,""));	
				}
				else {
					driverInfos.add(new DriverInfo(user, driver,truck.getLicensePlateNumber()));	
				}
				
			}
			return new ListSlice<DriverInfo>(page.getTotalElements(), driverInfos);
		}
		return null;
	}
	
	/*
	 * 删除司机
	 */
	@Override
	public boolean delete(Long parentId, Long userId) {
		
		Driver driver = driverRepository.findOnebyUserId(userId);
		if (driver == null) {
			throw new CodeException(ReturnCode.USERNAME_NOT_EXIST, MessageDes.User.USER_NOT_EXISTS);
		}
		
		int role = SessionInfo.getCurrent().getUserInfo().getRole();
		if (role != Consts.Role.TRUCKOWNER || driver.getParentUserId() != parentId) {
			if (role != Consts.Role.ADMIN) {
				throw new CodeException(ReturnCode.NO_PERMISSION, MessageDes.User.NO_DRIVER_PERMINSION);
			}
		}	
		
		//如果车辆本身有设备，需要解绑司机
		Truck truck = truckService.findOneByTruckId(driver.getTruckId());
		if (truck != null) {
			truckService.modifyCapcareDevice(driver.getUserId(),Consts.Capcare.DEVICE_UNBIND,truck);
		}
		
		driver.setIsDeleted(Consts.DELETED);
		driverRepository.save(driver);
		LogisticsUser user = logisticsUserRepository.findOne(userId);
		user.setIsDeleted(Consts.DELETED);
        //user.setUsername("__DELETE(" + user.getUsername() + ")DELETE__");
		user.setUsername(user.getUsername());
		logisticsUserRepository.save(user);
		return true;
	}
	
	/*
	 * 删除司机（物理）
	 */
	@Override
	public boolean deletePhysical(Long parentId, Long userId) {
		
		Driver driver = driverRepository.findOnebyUserId(userId);
		if (driver == null) {
			throw new CodeException(ReturnCode.USERNAME_NOT_EXIST, MessageDes.User.USER_NOT_EXISTS);
		}
		
		int role = SessionInfo.getCurrent().getUserInfo().getRole();
		if (role != Consts.Role.TRUCKOWNER || driver.getParentUserId() != parentId) {
			throw new CodeException(ReturnCode.NO_PERMISSION, MessageDes.User.NO_DRIVER_PERMINSION);
        }

            driverRepository.delete(driver.getId());
		logisticsUserRepository.delete(userId);
		return true;
	}

	/*
	 * 修改密码
	 */
	@Override
	public boolean changePassword(Long parentId, Long userId,
			String oldPassword, String newPassword) {
		
		LogisticsUser user = logisticsUserRepository.findOne(userId);
		Driver driver = driverRepository.findOnebyUserId(userId);
		if (driver == null || user == null) {
			throw new CodeException(ReturnCode.USERNAME_NOT_EXIST, MessageDes.User.USER_NOT_EXISTS);
		}
		
		int role = SessionInfo.getCurrent().getUserInfo().getRole();
		if (role == Consts.Role.TRUCKOWNER && driver.getParentUserId() == parentId) {
			//车主修改司机信息
		}
		else if (role == Consts.Role.DRIVER && parentId == userId) {
			//司机修改自己信息
		}
		else {
			throw new CodeException(ReturnCode.NO_PERMISSION, MessageDes.User.NO_DRIVER_PERMINSION);
		}
		if (!user.getPassword().equals(oldPassword)) {
			throw new CodeException(ReturnCode.INVALID_LOGIN_INFO, MessageDes.User.ORIGINAL_PASSWORD_ERROR);
		}
		
		
		user.setPassword(MD5Utils.getMD5(newPassword));
		logisticsUserRepository.save(user);
		return true;
	}

    @Override
    public Driver findByUserId(Long userId) {
        return driverRepository.findOnebyUserId(userId);
    }

    @Override
    public List<Driver> findListByTruckId(Long truckId) {
        return driverRepository.findListByTruckId(truckId);
    }


    private Driver makeDriver(Driver driver, Driver driverParam){
		driver.setName(driverParam.getName());
		driver.setIdCard(driverParam.getIdCard());
		driver.setMobile(driverParam.getMobile());
		driver.setPhone(driverParam.getPhone());
		driver.setAvatar(driverParam.getAvatar());
		driver.setTruckId(driverParam.getTruckId());
		driver.setTruckLabel(driverParam.getTruckLabel());
		return driver;
	}

}
