package org.chinalbs.logistics.service;

import java.util.List;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.OrderIntent;
import org.chinalbs.logistics.domain.Truck;
import org.chinalbs.logistics.vo.AddOrderIntentInfo;
import org.chinalbs.logistics.vo.IntentFromGoodsOwner;
import org.chinalbs.logistics.vo.OrderIntentInfo;
import org.chinalbs.logistics.vo.UserInfo;

public interface OrderIntentService {

	public ListSlice<OrderIntentInfo> findListSliceOfTruckOwner(Long truckOwnerId, int from, int max);
	public ListSlice<OrderIntentInfo> findListSliceOfTruckOwner(Long truckOwnerId, int from, int max, int status, int initiator);
	public ListSlice<OrderIntentInfo> findListSliceOfGoods(Long goodsId, int from, int max);
	public AddOrderIntentInfo create(AddOrderIntentInfo info);
	public IntentFromGoodsOwner create4GoodsOwner(IntentFromGoodsOwner intentFromGoodsOwner, UserInfo userInfo);
	public OrderIntentInfo findOneByOrderIntentId(Long orderIntentId);
	public void update(Long orderIntentId);
	public void delete(Long orderIntentId);
	public OrderIntent findOnebyTruckOwnerIdAndGoodsId(Long truckOwnerId, Long goodsId);
	public List<Truck> viewOrderIntentTrucks(Long orderIntentId);
	public void updateResidualCapacity(Long orderIntentId, List<Truck> truckList);
	public void updateStatus(Long orderIntentId, List<Truck> truckList);
	public void confirmOrders(Long orderIntentId);
	void refuseOrderIntent(OrderIntent orderIntent);

    OrderIntent findByTruckOwnerIdAndGoodsId(Long truckOwnerId, Long goodsId);
}
