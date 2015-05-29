package org.chinalbs.logistics.service.impl;

import java.util.Date;
import java.util.List;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Warehouse;
import org.chinalbs.logistics.repository.WarehouseRepository;
import org.chinalbs.logistics.repository.criteria.WarehouseDAO;
import org.chinalbs.logistics.service.WarehouseService;
import org.chinalbs.logistics.utils.Consts;
import org.chinalbs.logistics.utils.SimplePageable;
import org.chinalbs.logistics.vo.WarehouseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

	@Autowired
	private WarehouseRepository warehouseRepository;
	
	@Autowired
	private WarehouseDAO warehouseDAO;
	
	@Override
	public ListSlice<Warehouse> findListSlice(int from, int max) {
		Page<Warehouse> page = warehouseRepository.findAll(new SimplePageable(from, max));
		return new ListSlice<Warehouse>(page.getTotalElements(), page.getContent());
	}

	@Override
	public Warehouse create(Long userId,WarehouseInfo info) {
		Warehouse warehouse = makeWarehouse(new Warehouse(), info);
		warehouse.setGoodsOwnerId(userId);
		warehouse.setCreateTime(new Date());
		return warehouseRepository.save(warehouse);
	}

	@Override
	public Warehouse findOneByWarehouseId(Long warehouseId) {
		Warehouse warehouse = warehouseRepository.findOne(warehouseId);
		return warehouse;
	}

	@Override
	public Warehouse update(Long warehouseId, WarehouseInfo info) {
		Warehouse warehouse = warehouseRepository.findOne(warehouseId);
		if (warehouse != null) {
			return warehouseRepository.save(makeWarehouse(warehouse, info));
		}
		return null;
	}

	@Override
	public void delete(Long warehouseId) {
		Warehouse warehouse = warehouseRepository.findOne(warehouseId);
		if (warehouse != null) {
			warehouse.setIsDeleted(Consts.DELETED);
			warehouseRepository.save(warehouse);
		}
	}
	
	@Override
	public List<Warehouse> findByGoodsOwnerId(Long goodsOwnerId) {
		return warehouseRepository.findByGoodsOwnerId(goodsOwnerId);
	}
	
	private Warehouse makeWarehouse(Warehouse warehouse, WarehouseInfo info){
		warehouse.setType(info.getType());
		warehouse.setVolume(info.getVolume());
		warehouse.setArea(info.getArea());
		warehouse.setDistrict(info.getDistrict());
		warehouse.setAddress(info.getAddress());
		warehouse.setRent(info.getRent()*100);
		warehouse.setResidualCapacity(info.getResidualCapacity());
		warehouse.setLatitude(info.getLatitude());
		warehouse.setLongitude(info.getLongitude());
		warehouse.setPicture((info.getPicture() == "" ||info.getPicture() == null) ? 
				warehouse.getPicture() : info.getPicture());
		warehouse.setCompanyName(info.getCompanyName());
		warehouse.setIsCoolStore(info.getIsCoolStore());
		warehouse.setContactName(info.getContactName());
		warehouse.setContactMobile(info.getContactMobile());
		return warehouse;
	}

	@Override
	public ListSlice<Warehouse> search(Warehouse warehouse, int from, int max) {
		return warehouseDAO.findRange4Search(warehouse, new int[]{from, max});
	}

}
