package org.chinalbs.logistics.service;

import java.util.List;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Goods;
import org.chinalbs.logistics.domain.GoodsOwner;
import org.chinalbs.logistics.vo.GoodsInfo;

public interface GoodsService {

	public ListSlice<Goods> findListSlice(int from, int max);
	public ListSlice<Goods> findWaitGoodsListSlice(int from, int max);
	public List<Goods> findWaitGoodsByGoodsOwnerId(Long goodsOwnerId);
	public Goods create(GoodsInfo info);
	public Goods findOneByGoodsId(Long goodsId);
	public Goods update(Long goodsId, GoodsInfo info);
	public void delete(Long goodsId);
	public ListSlice<GoodsInfo> findListSlice(Long goodsOwnerId, int from, int max);
	public ListSlice<Goods> search(Goods goods, int from, int max);	
	public ListSlice<Goods> findFinishedByGoodsOwnerId(Long goodsOwnerId, int from, int max);
	public ListSlice<Goods> findFinishedByTruckOwnerId(Long truckOwnerId, int from, int max);
	public GoodsOwner findOneByUserId(Long userId);
}
