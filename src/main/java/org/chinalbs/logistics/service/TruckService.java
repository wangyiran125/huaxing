package org.chinalbs.logistics.service;

import java.util.List;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Truck;
import org.chinalbs.logistics.vo.TruckInfo;

public interface TruckService {

	public ListSlice<TruckInfo> findListSlice(Long truckOwnerId, int from, int max);
	public ListSlice<TruckInfo> findlistIntentPermit(Long truckOwnerId, int from, int max);
    public List<TruckInfo> findListByDeviceKey(String deviceKey);
	public Truck create(Long truckOwnerId, TruckInfo info);
	public TruckInfo findOneByTruckId(Long truckId);
	public Truck update(Long truckId, TruckInfo info);
	public List<Truck> updateTruckStatus(List<TruckInfo> truckInfos);
	public void modifyCapcareDevice(String model, Truck truck);
	public void modifyCapcareDevice(Long userId, String model, Truck truck);
	public void delete(Long truckId);
}