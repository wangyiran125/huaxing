package org.chinalbs.logistics.service;

import java.util.List;
import java.util.Map;

import org.chinalbs.logistics.domain.VipLevel;
import org.chinalbs.logistics.vo.VipLevelInfo;

public interface VipLevelService {

	public List<VipLevel> findAll();
	public VipLevel create(VipLevelInfo info);
	public VipLevel update(Long vipLevelId, VipLevelInfo info);
	public void delete(Long vipLevelId);
	public Map<String, Integer> getAllVipLevelItem();
	public void updateVipLevelItem(Map<String, Integer> items);
}
