package org.chinalbs.logistics.service;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.TruckPlan;
import org.chinalbs.logistics.vo.TruckPlanInfo;
import org.chinalbs.logistics.vo.TruckViewInfo;

public interface TruckPlanService {

	public ListSlice<TruckPlanInfo> findListSlice(int from, int max);
	public TruckPlan create(TruckPlanInfo info);
	public TruckPlan findOne(Long truckPlanId);
	public TruckPlan update(Long truckPlanId, TruckPlanInfo info);
	public void delete(Long truckPlanId);
	public ListSlice<TruckPlanInfo> findListSlice(Long userId, int from, int max);
	public ListSlice<TruckViewInfo> search(TruckPlanInfo truckPlanInfo, int from, int max);
	public TruckPlanInfo findOneDetail(Long truckPlanId);
	
}
