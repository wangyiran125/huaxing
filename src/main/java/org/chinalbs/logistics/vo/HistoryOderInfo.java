package org.chinalbs.logistics.vo;

import org.chinalbs.logistics.domain.Goods;
import org.chinalbs.logistics.domain.LogisticsOrder;

public class HistoryOderInfo {
	private LogisticsOrder logisticsOrder;
	private Goods goods;
	
	public HistoryOderInfo() {
		
	}

	public LogisticsOrder getLogisticsOrder() {
		return logisticsOrder;
	}

	public void setLogisticsOrder(LogisticsOrder logisticsOrder) {
		this.logisticsOrder = logisticsOrder;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	@Override
	public String toString() {
		return "HistoryOderInfo [getLogisticsOrder()=" + getLogisticsOrder()
				+ ", getGoods()=" + getGoods() + "]";
	}
	
	

}
