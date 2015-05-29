package org.chinalbs.logistics.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.chinalbs.logistics.common.json.ReturnCode;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Driver;
import org.chinalbs.logistics.domain.OrderIntent;
import org.chinalbs.logistics.domain.Truck;
import org.chinalbs.logistics.error.CodeException;
import org.chinalbs.logistics.repository.DriverRepository;
import org.chinalbs.logistics.repository.LogisticsOrderRepository;
import org.chinalbs.logistics.repository.OrderIntentRepository;
import org.chinalbs.logistics.repository.TruckRepository;
import org.chinalbs.logistics.service.TruckService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.utils.Consts;
import org.chinalbs.logistics.utils.HTTPUtils;
import org.chinalbs.logistics.utils.MessageDes;
import org.chinalbs.logistics.utils.SimplePageable;
import org.chinalbs.logistics.vo.TruckInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TruckServiceImpl implements TruckService {

	@Autowired
	private TruckRepository truckRepository;
	
	@Autowired
	private LogisticsOrderRepository logisticsOrderRepository;
	
	@Autowired
	private OrderIntentRepository orderIntentRepository;
	
	@Autowired
	private DriverRepository driverRepository;
	
	@Override
	public ListSlice<TruckInfo> findListSlice(Long truckOwnerId, int from, int max) {
		Page<Truck> page = truckRepository.findByTruckOwnerId(truckOwnerId, new SimplePageable(from, max));
		List<TruckInfo> truckInfos = new ArrayList<TruckInfo>();
		TruckInfo truckInfo = null;
		if (page != null && page.getSize() > 0) {
			for (Truck truck : page) {
				truckInfo = makeTruckInfo(new TruckInfo(), truck);
				truckInfos.add(truckInfo);
			}
			return new ListSlice<TruckInfo>(page.getTotalElements(), truckInfos);
		}
		return null;
	}

	/*
	 * 添加车辆
	 * 
	 */
	@Override
	public Truck create(Long truckOwnerId, TruckInfo info) {
		
		//检查车牌号不能为空，且不可重复
		if (info.getLicensePlateNumber() == null || info.getLicensePlateNumber().equals("")) {
			throw new CodeException(ReturnCode.INVALID_PARAMETER, MessageDes.PARAM_ERROR );
		}
	    //为方便存储和比较，车牌都改为大写
		info.setLicensePlateNumber(info.getLicensePlateNumber().toUpperCase());
		Page<Truck> page = truckRepository.findListByLicensePlateNumber(info.getLicensePlateNumber(), 
				new SimplePageable(0, Integer.MAX_VALUE));
		if (page != null && page.getSize() > 0) {
			for (Truck truck : page) {
				if (truck.getLicensePlateNumber().equalsIgnoreCase(info.getLicensePlateNumber())) {
					throw new CodeException(ReturnCode.BUSINESS_ERROR, MessageDes.Truck.PLATE_NUMBER_ERROR );
				}
			}
		}
		
		//凯步关爱 新增
		modifyCapcareDevice(Consts.Capcare.DEVICE_BIND,info);
		Truck truck = createOrUpdateTruck(new Truck(), info);
		truck.setTruckOwnerId(truckOwnerId);
		return truckRepository.save(truck);
	}

	@Override
	public TruckInfo findOneByTruckId(Long truckId) {
		Truck truck = truckRepository.findOne(truckId);
		TruckInfo truckInfo = null;
		if (truck != null) {
			truckInfo = makeTruckInfo(new TruckInfo(), truck);
		}
		return truckInfo;
	}

	@Override
	public Truck update(Long truckId, TruckInfo info) {
		Truck truck = truckRepository.findOne(truckId);
		if (truck != null) {
			List<Driver> driverList = driverRepository.findListByTruckId(truckId);
			Long driverUserId = 0l;
			if (driverList.size() > 0) {
				//list当中最多有一个元素，因为一个车辆最多只能分配给一个司机
				driverUserId = driverList.get(0).getUserId();
			}
			
			//凯步关爱修改
			if ((truck.getDeviceKey() == null || truck.getDeviceKey().equals(""))
					&& (info.getDeviceKey() != null && !info.getDeviceKey().equals(""))) {
				//原来没有设备， 现在有设备， 绑定
				modifyCapcareDevice(Consts.Capcare.DEVICE_BIND,info);
				modifyCapcareDevice(driverUserId,Consts.Capcare.DEVICE_BIND,info);
			}
			else if ((truck.getDeviceKey() != null && !truck.getDeviceKey().equals(""))
					&& (info.getDeviceKey() == null || info.getDeviceKey().equals(""))) {
				//原来有设备，现在没有设备， 解绑
				modifyCapcareDevice(Consts.Capcare.DEVICE_UNBIND,truck);
				modifyCapcareDevice(driverUserId, Consts.Capcare.DEVICE_UNBIND,truck);
			}
			else if ((truck.getDeviceKey() != null && !truck.getDeviceKey().equals(""))
					&& (info.getDeviceKey() != null && !info.getDeviceKey().equals(""))
					&& !truck.getDeviceKey().equals(info.getDeviceKey())){
				//原来有设备， 现在有设备， 不相等， 解绑 再次绑定
				modifyCapcareDevice(Consts.Capcare.DEVICE_UNBIND,truck);
				modifyCapcareDevice(driverUserId, Consts.Capcare.DEVICE_UNBIND,truck);
				modifyCapcareDevice(Consts.Capcare.DEVICE_BIND,info);
				modifyCapcareDevice(driverUserId, Consts.Capcare.DEVICE_BIND,info);
			}else if ((truck.getDeviceKey() != null && !truck.getDeviceKey().equals(""))
					&& (info.getDeviceKey() != null && !info.getDeviceKey().equals(""))
					&& truck.getDeviceKey().equals(info.getDeviceKey())){
				//原来有设备， 现在有设备， 不相等， 变更
				if (!truck.getAlarmCall().equals(info.getAlarmCall())
						|| !truck.getDevicePhone().equals(info.getDevicePhone())
						|| !truck.getLicensePlateNumber().equals(info.getLicensePlateNumber())) {
					modifyCapcareDevice(Consts.Capcare.DEVICE_MODIFY,info);
					modifyCapcareDevice(driverUserId, Consts.Capcare.DEVICE_MODIFY,info);
				}
			}
			
			return truckRepository.save(createOrUpdateTruck(truck, info));
		}
		return null;
	}
	
	/*
	 * 车辆删除
	 * 抢单未拒绝前禁止删除， 运输中禁止删除
	 */

	@Override
	public void delete(Long truckId) {
		Long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		Page<OrderIntent> page = orderIntentRepository.findNoRefusedByTruckOwnerId(
				userId, new SimplePageable(0, Integer.MAX_VALUE));
		//车辆已抢单或者运输中， 不可删除
		if (page != null && page.getSize() > 0) {
			for (OrderIntent orderIntent : page) {
				if (orderIntent.getTruckIds().contains(Long.toString(truckId))){
					//抢单中包含此辆车
					throw new CodeException(ReturnCode.BUSINESS_ERROR, MessageDes.Truck.DELETE_GUARD_STATUS);
				}
			}
		}
		
		Truck truck = truckRepository.findOne(truckId);
		if (truck != null) {
			//凯步关爱删除
			List<Driver> driverList = driverRepository.findListByTruckId(truckId);
			Long driverUserId = 0l;
			if (driverList.size() > 0) {
				//list当中最多有一个元素，因为一个车辆最多只能分配给一个司机
				driverUserId = driverList.get(0).getUserId();
			}
			
			modifyCapcareDevice(Consts.Capcare.DEVICE_UNBIND,truck);
			modifyCapcareDevice(driverUserId, Consts.Capcare.DEVICE_UNBIND,truck);
			truck.setIsDeleted(Consts.DELETED);
			truckRepository.save(truck);
		}
	}

	private Truck createOrUpdateTruck(Truck truck, TruckInfo info){
		truck.setLicensePlateNumber(info.getLicensePlateNumber());
		truck.setTruckLength(info.getTruckLength());
		truck.setTruckLoad(info.getTruckLoad());
		truck.setVolume(info.getVolume());
		truck.setTruckStation(info.getTruckStation());
		truck.setTruckAddress(info.getTruckAddress());
		truck.setType(info.getType());
		truck.setBrand(info.getBrand());
		truck.setTruckCondition(info.getTruckCondition());
		truck.setTruckOwnerPhone(info.getTruckOwnerPhone());
		truck.setPatcher(info.getPatcher());
		truck.setTruckUDID(info.getTruckUDID());
		truck.setTruckPicture((info.getTruckPicture() == "" || info.getTruckPicture() == null) ? 
				truck.getTruckPicture() : info.getTruckPicture());
		truck.setTruckLicensePicture((info.getTruckLicensePicture() == "" || info.getTruckLicensePicture() == null) ? 
				truck.getTruckLicensePicture() : info.getTruckLicensePicture());
		truck.setDeviceKey(info.getDeviceKey());
		truck.setTruckStatus(truck.getTruckStatus() == 0 ? Consts.Truck.EMPTY : truck.getTruckStatus());
		truck.setDevicePhone(info.getDevicePhone());
		truck.setAlarmCall(info.getAlarmCall());
		return truck;
	}
	
	private TruckInfo makeTruckInfo(TruckInfo info, Truck truck){
		info.setId(truck.getId());
		info.setTruckOwnerId(truck.getTruckOwnerId());
		info.setLicensePlateNumber(truck.getLicensePlateNumber());
		info.setTruckLength(truck.getTruckLength());
		info.setTruckLoad(truck.getTruckLoad());
		info.setVolume(truck.getVolume());
		info.setTruckStation(truck.getTruckStation());
		info.setTruckStatus(truck.getTruckStatus());
		info.setTruckAddress(truck.getTruckAddress());
		info.setType(truck.getType());
		info.setBrand(truck.getBrand());
		info.setTruckCondition(truck.getTruckCondition());
		info.setTruckOwnerPhone(truck.getTruckOwnerPhone());
		info.setPatcher(truck.getPatcher());
		info.setTruckUDID(truck.getTruckUDID());
		info.setTruckPicture(truck.getTruckPicture());
		info.setTruckLicensePicture(truck.getTruckLicensePicture());
		info.setDeviceKey(truck.getDeviceKey());
		info.setDevicePhone(truck.getDevicePhone());
		info.setAlarmCall(truck.getAlarmCall());
		return info;
	}

	/***
	 * 修改车的状态（货主修改）参数格式{{truckId,truckStatus}，{truckId, truckStatus},...}
	 */
	@Override
	public List<Truck> updateTruckStatus(List<TruckInfo> truckInfos) {
		if (truckInfos != null && truckInfos.size() >= 0) {
			List<Truck> trucks = new ArrayList<Truck>();
			for (TruckInfo truckinfo : truckInfos) {
				Truck truck = truckRepository.findOne(truckinfo.getId());
				if (truck != null) {
					truck.setTruckStatus(truckinfo.getTruckStatus());
					trucks.add(truckRepository.save(truck));
				}
			}
			return trucks;
		}
		return null;
	}
	
	@Override
	public void modifyCapcareDevice(String model, Truck truck) {
		modifyCapcareDevice(SessionInfo.getCurrent().getUserInfo().getUserId(), model, truck);
	}
	
	
	@Override
	public void modifyCapcareDevice(Long userId, String model, Truck truck) {
		if (userId == 0l) {
			//ugly code
			return;
		}
		
		if (truck.getDeviceKey() != null && !truck.getDeviceKey().equals("")) {
			//凯步关爱检查设备
			if (model.equals(Consts.Capcare.DEVICE_BIND)) {
				HTTPUtils.checkDevice(truck.getDeviceKey());
			}
			//userId=1&deviceKey=111&actionType=1&fAlarmCall=3231231&devicePhone=2321&licensePlateNumber=321321
			Map<String, String> paramHash = new HashMap<String, String>();
			paramHash.put("userId", Long.toString(userId));
			paramHash.put("deviceKey", truck.getDeviceKey());
			paramHash.put("actionType", model);
			if (truck.getAlarmCall() != null && !truck.getAlarmCall().equals("")) {
				paramHash.put("alarmCall", truck.getAlarmCall());
			}
			if (truck.getDevicePhone() != null && !truck.getDevicePhone().equals("")) {
				paramHash.put("devicePhone", truck.getDevicePhone());
			}	
			if (truck.getLicensePlateNumber() != null && !truck.getLicensePlateNumber().equals("")) {
				paramHash.put("licensePlateNumber", truck.getLicensePlateNumber());
			}	
			HTTPUtils.mgrDevice(paramHash);
		}		
		
	}

	@Override
	public ListSlice<TruckInfo> findlistIntentPermit(Long truckOwnerId,
			int from, int max) {
		Page<Truck> page = truckRepository.findIntentsPermitByTruckOwnerId(truckOwnerId, new SimplePageable(from, max));
		List<TruckInfo> truckInfos = new ArrayList<TruckInfo>();
		TruckInfo truckInfo = null;
		if (page != null && page.getSize() > 0) {
			for (Truck truck : page) {
				truckInfo = makeTruckInfo(new TruckInfo(), truck);
				truckInfos.add(truckInfo);
			}
			return new ListSlice<TruckInfo>(page.getTotalElements(), truckInfos);
		}
		return null;
	}

    @Override
    public List<TruckInfo> findListByDeviceKey(String deviceKey) {
        List<Truck> trucks = truckRepository.findListByDeviceKey(deviceKey);
        List<TruckInfo> truckInfos = new ArrayList<TruckInfo>();
        if (trucks != null) {
            for (Truck truck : trucks) {
                TruckInfo truckInfo = makeTruckInfo(new TruckInfo(), truck);
                truckInfos.add(truckInfo);
            }
        }
        return truckInfos;
    }
}
