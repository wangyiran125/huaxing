package org.chinalbs.logistics.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.chinalbs.logistics.domain.VipLevel;
import org.chinalbs.logistics.repository.VipLevelRepository;
import org.chinalbs.logistics.service.VipLevelService;
import org.chinalbs.logistics.utils.Consts;
import org.chinalbs.logistics.vo.VipLevelInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VipLevelServiceImpl implements VipLevelService{

	@Autowired
	private VipLevelRepository vipLevelRepository;
	
	private static final Map<Integer, String> VIPLEVEL = new HashMap<Integer, String>();
	static{
		VIPLEVEL.put(Consts.VipLevel.GOLD, "gold");
		VIPLEVEL.put(Consts.VipLevel.SILVER, "silver");
//		VIPLEVEL.put(Consts.VipLevel.COPPER, "copper");
		VIPLEVEL.put(Consts.VipLevel.COMMON, "common");
	}
	
	@Override
	public List<VipLevel> findAll() {
		
		return vipLevelRepository.findAll();
	}

	@Override
	public VipLevel create(VipLevelInfo info) {
		
		return vipLevelRepository.save(makeVipLevel(new VipLevel(), info));
	}

	@Override
	public VipLevel update(Long vipLevelId, VipLevelInfo info) {
		VipLevel vipLevel = vipLevelRepository.findOne(vipLevelId);
		return vipLevelRepository.save(makeVipLevel(vipLevel, info));
	}

	@Override
	public void delete(Long vipLevelId) {
		vipLevelRepository.delete(vipLevelId);
	}
	
	private VipLevel makeVipLevel(VipLevel vipLevel, VipLevelInfo info){
		vipLevel.setLevel(info.getLevel());
		vipLevel.setName(info.getName());
		return vipLevel;
	}

	@Override
	public Map<String, Integer> getAllVipLevelItem() {
		List<VipLevel> items = vipLevelRepository.findAll();
		Map<String, Integer> itemsMap = new HashMap<String, Integer>();
		for(VipLevel item : items){
			itemsMap.put(VIPLEVEL.get(item.getLevel()), item.getDelayTime());
		}
		return itemsMap;
	}

	@Override
	public void updateVipLevelItem(Map<String, Integer> vipLevels) {
		List<VipLevel> items = vipLevelRepository.findAll();
		for(VipLevel item : items){
			String key = VIPLEVEL.get(item.getLevel());
			Integer delayTime = vipLevels.get(key) == null ? item.getDelayTime() : vipLevels.get(key);
			item.setDelayTime(delayTime);
		}
		vipLevelRepository.save(items);
	}

}
