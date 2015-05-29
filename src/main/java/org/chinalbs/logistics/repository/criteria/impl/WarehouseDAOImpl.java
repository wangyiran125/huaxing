package org.chinalbs.logistics.repository.criteria.impl;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Warehouse;
import org.chinalbs.logistics.repository.criteria.AbstractJpaDao;
import org.chinalbs.logistics.repository.criteria.WarehouseDAO;
import org.springframework.stereotype.Repository;


@Repository
public class WarehouseDAOImpl extends AbstractJpaDao<Warehouse> implements WarehouseDAO{

	public WarehouseDAOImpl() {
		super(Warehouse.class);
	}
	
    public ListSlice<Warehouse> findRange4Search(Warehouse obj, int[] range) {
    	return findRange(obj, range);
    }
	
}
