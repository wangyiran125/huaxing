package org.chinalbs.logistics.repository.criteria;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.vo.TruckPlanInfo;
import org.chinalbs.logistics.vo.TruckViewInfo;

public interface TruckDAO extends EntityDao<TruckPlanInfo> {

    public ListSlice<TruckViewInfo> findRange4Search(TruckPlanInfo obj, int[] range);
}
