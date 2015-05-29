package org.chinalbs.logistics.service;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.LogisticsOrder;
import org.chinalbs.logistics.vo.HistoryOderInfo;
import org.chinalbs.logistics.vo.OrderDetailInfo;

public interface LogisticsOrderService {
	public LogisticsOrder findOneByGoodsId(Long goodsId);
	
	public ListSlice<HistoryOderInfo> findHistoryOrder(Long userId, int from, int max);
	
	public LogisticsOrder findOneById(Long orderId);
	
	public OrderDetailInfo orderDetail(Long orderId);
	
	public LogisticsOrder orderEvaluate(LogisticsOrder logisticsOrder, Long userId);
}
