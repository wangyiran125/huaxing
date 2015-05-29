package org.chinalbs.logistics.vo;


public class IntentFromGoodsOwner {

	private long orderIntentId;
    private long truckPlanId;

    public long getTruckPlanId() {
        return truckPlanId;
    }

    public void setTruckPlanId(long truckPlanId) {
        this.truckPlanId = truckPlanId;
    }

    private Long truckId;
	private long goodsId;
	private int initiator;
	private String contactName;
	private String contactMobile;


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


	public long getOrderIntentId() {
		return orderIntentId;
	}

	public void setOrderIntentId(long orderIntentId) {
		this.orderIntentId = orderIntentId;
	}

	public Long getTruckId() {
		return truckId;
	}

	public void setTruckId(Long truckId) {
		this.truckId = truckId;
	}

	public long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	@Override
	public String toString() {
		return "IntentFromGoodsOwner [getInitiator()=" + getInitiator()
				+ ", getContactName()=" + getContactName()
				+ ", getContactMobile()=" + getContactMobile()
				+ ", getOrderIntentId()=" + getOrderIntentId()
				+ ", getTruckId()=" + getTruckId() + ", getGoodsId()="
				+ getGoodsId() +  "]";
	}
	
	

}
