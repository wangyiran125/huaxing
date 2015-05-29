package org.chinalbs.logistics.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Driver;
import org.chinalbs.logistics.domain.LogisticsUser;
import org.chinalbs.logistics.domain.Truck;
import org.chinalbs.logistics.domain.TruckOwner;
import org.chinalbs.logistics.domain.TruckPlan;
import org.chinalbs.logistics.repository.DriverRepository;
import org.chinalbs.logistics.repository.LogisticsUserRepository;
import org.chinalbs.logistics.repository.TruckOwnerRepository;
import org.chinalbs.logistics.repository.TruckPlanRepository;
import org.chinalbs.logistics.repository.TruckRepository;
import org.chinalbs.logistics.repository.criteria.TruckDAO;
import org.chinalbs.logistics.service.TruckPlanService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.utils.Consts;
import org.chinalbs.logistics.utils.HTTPUtils;
import org.chinalbs.logistics.utils.SimplePageable;
import org.chinalbs.logistics.vo.TruckPlanInfo;
import org.chinalbs.logistics.vo.TruckViewInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TruckPlanServiceImpl implements TruckPlanService {

	@Autowired
	private TruckPlanRepository truckPlanRepository;
	@Autowired
	private TruckRepository truckRepository;
	
	@Autowired
	private TruckDAO truckDAO;

    @Autowired
    private LogisticsUserRepository logisticsUserRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private TruckOwnerRepository truckOwnerRepository;
	
	@Override
	public ListSlice<TruckPlanInfo> findListSlice(int from, int max) {
		
		Page<TruckPlan> page = truckPlanRepository.findAll(new SimplePageable(from, max));
		if (page != null && page.getSize() > 0) {
			List<TruckPlanInfo> truckPlanInfos = new ArrayList<TruckPlanInfo>();
			for (TruckPlan truckPlan : page) {
				if (truckPlan != null) {
					Truck truck = truckRepository.findOne(truckPlan.getTruckId());
					truckPlanInfos.add(makeTruckPlanInfo(truckPlan, truck, new TruckPlanInfo()));
				}
			}
			return new ListSlice<TruckPlanInfo>(page.getTotalElements(), truckPlanInfos);	
		}
		return null;
	}

	@Override
	public TruckPlan create(TruckPlanInfo info) {
		TruckPlan truckPlan = makeTruckPlan(new TruckPlan(), info);
		truckPlan.setPublishTime(new Date());
		return truckPlanRepository.save(truckPlan);
	}

	@Override
	public TruckPlan findOne(Long truckPlanId) {
		return truckPlanRepository.findOne(truckPlanId);
	}

	@Override
	public TruckPlan update(Long truckPlanId, TruckPlanInfo info) {
		TruckPlan truckPlan = truckPlanRepository.findOne(truckPlanId);
		if (truckPlan != null) {
			makeTruckPlan(truckPlan, info);
			return truckPlanRepository.save(truckPlan);
		}
		return null;
	}

	@Override
	public void delete(Long truckPlanId) {
		TruckPlan truckPlan = truckPlanRepository.findOne(truckPlanId);
		if (truckPlan != null) {
			truckPlan.setIsDeleted(Consts.DELETED);
			truckPlanRepository.save(truckPlan);
		}
	}
	
	private TruckPlanInfo makeTruckPlanInfo(TruckPlan truckPlan, Truck truck, TruckPlanInfo truckPlanInfo){
		truckPlanInfo.setTruckId(truckPlan.getTruckId());
		truckPlanInfo.setTruckPlanId(truckPlan.getId());
		truckPlanInfo.setIsBackTruck(truckPlan.getIsBackTruck());
		truckPlanInfo.setIsLongTerm(truckPlan.getIsLongTerm());
		truckPlanInfo.setDepartureProvinceCode(truckPlan.getDepartureProvinceCode());
		truckPlanInfo.setDepartureCityCode(truckPlan.getDepartureCityCode());
		truckPlanInfo.setDestinationProvinceCode(truckPlan.getDestinationProvinceCode());
		truckPlanInfo.setDestinationCityCode(truckPlan.getDestinationCityCode());
		truckPlanInfo.setShippingPrice(truckPlan.getShippingPrice());
		truckPlanInfo.setDepartureTime(truckPlan.getDepartureTime());
		truckPlanInfo.setPhrase(truckPlan.getPhrase());
		truckPlanInfo.setRemark(truckPlan.getRemark());
        truckPlanInfo.setPublishTime(truckPlan.getPublishTime());
        truckPlanInfo.setIsLocalFreeTruck(truckPlan.getIsLocalFreeTruck());
		return copyFromTruck(truck, truckPlanInfo);
	}
	
	private TruckPlanInfo copyFromTruck(Truck truck, TruckPlanInfo truckPlanInfo){
		truckPlanInfo.setLicensePlateNumber(truck.getLicensePlateNumber());
		truckPlanInfo.setTruckLength(truck.getTruckLength());
		truckPlanInfo.setTruckLoad(truck.getTruckLoad());
		truckPlanInfo.setVolume(truck.getVolume());
		truckPlanInfo.setTruckStation(truck.getTruckStation());
		truckPlanInfo.setTruckAddress(truck.getTruckAddress());
		truckPlanInfo.setType(truck.getType());
		truckPlanInfo.setBrand(truck.getBrand());
		truckPlanInfo.setTruckCondition(truck.getTruckCondition());
		truckPlanInfo.setTruckStatus(truck.getTruckStatus());
		truckPlanInfo.setTruckOwnerPhone(truck.getTruckOwnerPhone());
		truckPlanInfo.setPatcher(truck.getPatcher());
		truckPlanInfo.setTruckUDID(truck.getTruckUDID());
		truckPlanInfo.setTruckPicture(truck.getTruckPicture());
		truckPlanInfo.setTruckLicensePicture(truck.getTruckLicensePicture());
		return truckPlanInfo;
	}
	
	private TruckPlan makeTruckPlan(TruckPlan truckPlan, TruckPlanInfo info){
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
		truckPlan.setTruckId(info.getTruckId());
		truckPlan.setTruckOwnerId(userId);
		truckPlan.setIsBackTruck(info.getIsBackTruck());
		truckPlan.setIsLongTerm(info.getIsLongTerm());
		truckPlan.setShippingPrice(info.getShippingPrice()*100);
		truckPlan.setDepartureProvinceCode(info.getDepartureProvinceCode());
		truckPlan.setDepartureCityCode(info.getDepartureCityCode());
		truckPlan.setDestinationProvinceCode(info.getDestinationProvinceCode());
		truckPlan.setDestinationCityCode(info.getDestinationCityCode());
		truckPlan.setDepartureTime(info.getDepartureTime());
		truckPlan.setPhrase(info.getPhrase());
		truckPlan.setRemark(info.getRemark());
		truckPlan.setIsLocalFreeTruck(info.getIsLocalFreeTruck());
		return truckPlan;
	}

	@Override
	public ListSlice<TruckPlanInfo> findListSlice(Long userId, int from, int max) {
		Page<TruckPlan> page = truckPlanRepository.findByTruckOwnerId(userId, new SimplePageable(from, max));
		if (page != null && page.getSize() > 0) {
			List<TruckPlanInfo> truckPlanInfos = new ArrayList<TruckPlanInfo>();
			for (TruckPlan truckPlan : page) {
				if (truckPlan != null) {
					Truck truck = truckRepository.findOne(truckPlan.getTruckId());
					truckPlanInfos.add(makeTruckPlanInfo(truckPlan, truck, new TruckPlanInfo()));
				}
			}
			return new ListSlice<TruckPlanInfo>(page.getTotalElements(), truckPlanInfos);	
		}
		return null;
	}

	@Override
	public TruckPlanInfo findOneDetail(Long truckPlanId) {
		TruckPlanInfo truckPlanInfo = null;
		TruckPlan truckPlan = truckPlanRepository.findOne(truckPlanId);
		if (truckPlan != null) {
			Truck truck = truckRepository.findOne(truckPlan.getTruckId());
			truckPlanInfo =  makeTruckPlanInfo(truckPlan, truck, new TruckPlanInfo());
		}
		return truckPlanInfo;
	}

	@Override
	public ListSlice<TruckViewInfo> search(TruckPlanInfo truckPlanInfo,
			int from, int max) {
		
		ListSlice<TruckViewInfo> listSliceTruckViewInfo = truckDAO.findRange4Search(truckPlanInfo, new int[]{from, max});
		listSliceTruckViewInfo = HTTPUtils.capcare4Search(listSliceTruckViewInfo);
        for (TruckViewInfo tvi : listSliceTruckViewInfo.getList()) {
//            tvi.setTruckLabel(getTruckLabel(tvi.getTruckPlan().getTruckOwnerId()));
        	makeTruckViewInfo(tvi);
        }
		return listSliceTruckViewInfo;
	}
	
    private void makeTruckViewInfo(TruckViewInfo info) {
    	long userId = info.getTruckPlan().getTruckOwnerId();
        LogisticsUser user = logisticsUserRepository.findOne(userId);
        if (user.getRole() == Consts.Role.TRUCKOWNER) {
            TruckOwner truckOwner = truckOwnerRepository.findOneByUserId(userId);
            info.setTruckLabel(truckOwner.getTruckLabel());
            info.setVipLevel(truckOwner.getVipLevel());
//            return truckOwner.getTruckLabel();
        } else if (user.getRole() == Consts.Role.DRIVER) {
            Driver driver = driverRepository.findOnebyUserId(userId);
//            return driver.getTruckLabel();
            info.setTruckLabel(driver.getTruckLabel());
        }
//        return null;
    }
}
