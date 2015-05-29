package org.chinalbs.logistics.vo;

import java.util.List;

public class AddOrderIntentInfo {

	private Long orderIntentId;
	private Long goodsId;
	private List<Long> truckIds;
	private int initiator;
	private String contactName;
	private String contactMobile;

	public Long getOrderIntentId() {
		return orderIntentId;
	}

	public void setOrderIntentId(Long orderIntentId) {
		this.orderIntentId = orderIntentId;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public List<Long> getTruckIds() {
		return truckIds;
	}

	public void setTruckIds(List<Long> truckIds) {
		this.truckIds = truckIds;
	}

	public int getInitiator() {
		return initiator;
	}

	public void setInitiator(int initiator) {
		this.initiator = initiator;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	@Override
	public String toString() {
		return "AddOrderIntentInfo [getOrderIntentId()=" + getOrderIntentId()
				+ ", getGoodsId()=" + getGoodsId() + ", getTruckIds()="
				+ getTruckIds() + ", getInitiator()=" + getInitiator()
				+ ", getContactName()=" + getContactName()
				+ ", getContactMobile()=" + getContactMobile()
				+ "]";
	}
	
	

}
