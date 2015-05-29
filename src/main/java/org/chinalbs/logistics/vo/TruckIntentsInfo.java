package org.chinalbs.logistics.vo;

import java.util.List;

public class TruckIntentsInfo {

	private Long goodsId;
	private List<TruckInfo> truckInfoList;
	
	public TruckIntentsInfo(){
		
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public List<TruckInfo> getTruckInfoList() {
		return truckInfoList;
	}
	public void setTruckInfoList(List<TruckInfo> truckInfoList) {
		this.truckInfoList = truckInfoList;
	}
	@Override
	public String toString() {
		return "TruckIntentsInfo [getGoodsId()=" + getGoodsId()
				+ ", getTruckInfoList()=" + getTruckInfoList()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	
}
