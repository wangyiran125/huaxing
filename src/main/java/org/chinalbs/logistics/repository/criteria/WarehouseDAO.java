package org.chinalbs.logistics.repository.criteria;


import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Warehouse;

public interface WarehouseDAO extends EntityDao<Warehouse> {

    public ListSlice<Warehouse> findRange4Search(Warehouse obj, int[] range);
}
