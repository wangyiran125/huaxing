package org.chinalbs.logistics.service;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Driver;
import org.chinalbs.logistics.vo.DriverInfo;
import org.chinalbs.logistics.vo.RegisterInfo;

import java.util.List;


public interface DriverService {
	public DriverInfo create(Long parentId, RegisterInfo registerInfo);
	public DriverInfo update(Long parentId, Long driverId, Driver driverParam);
	public ListSlice<DriverInfo> findByParentId(Long parentId, int from, int max);
	public boolean delete(Long parentId, Long userId); 
	public boolean deletePhysical(Long parentId, Long userId); 
	public boolean changePassword(Long parentId, Long userId, String oldPassword, String newPassword);
    public Driver findByUserId(Long userId);
    public List<Driver> findListByTruckId(Long truckId);
}
