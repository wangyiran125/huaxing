package org.chinalbs.logistics.vo;

import java.util.List;

import org.chinalbs.logistics.domain.Goods;
import org.chinalbs.logistics.domain.LogisticsOrder;
import org.chinalbs.logistics.domain.Truck;

public class OrderDetailInfo {
	private LogisticsOrder logisticsOrder;
	private Goods goods;
	private UserInfo truckOwner;
	private List<Truck> truckList;
	
	public OrderDetailInfo() {
		
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

	public UserInfo getTruckOwner() {
		return truckOwner;
	}

	public void setTruckOwner(UserInfo truckOwner) {
		this.truckOwner = truckOwner;
	}

	public List<Truck> getTruckList() {
		return truckList;
	}

	public void setTruckList(List<Truck> truckList) {
		this.truckList = truckList;
	}

	@Override
	public String toString() {
		return "OrderDetailInfo [getLogisticsOrder()=" + getLogisticsOrder()
				+ ", getGoods()=" + getGoods() + ", getTruckOwner()="
				+ getTruckOwner() + ", getTruckList()=" + getTruckList()
				+ "]";
	}
	
	
	
}
