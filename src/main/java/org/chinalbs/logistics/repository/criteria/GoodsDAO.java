package org.chinalbs.logistics.repository.criteria;


import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Goods;

public interface GoodsDAO extends EntityDao<Goods> {

    public ListSlice<Goods> findRange4Search(Goods obj, int[] range);
}
