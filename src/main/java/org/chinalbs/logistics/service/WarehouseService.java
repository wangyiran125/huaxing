package org.chinalbs.logistics.service;

import java.util.List;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Warehouse;
import org.chinalbs.logistics.vo.WarehouseInfo;

public interface WarehouseService {

	public ListSlice<Warehouse> findListSlice(int from, int max);
	public Warehouse create(Long userId,WarehouseInfo info);
	public Warehouse findOneByWarehouseId(Long warehouseId);
	public Warehouse update(Long warehouseId, WarehouseInfo info);
	public void delete(Long warehouseId);
	public List<Warehouse> findByGoodsOwnerId(Long goodsOwnerId);
    public ListSlice<Warehouse> search(Warehouse warehouse, int from, int max);
	
}
