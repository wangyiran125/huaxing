package org.chinalbs.logistics.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class TruckOwner extends UserCommon {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2308085860671965290L;

    @Column(nullable = false)
	private String truckLabel;// 车标
	private int vipLevel;// 会员等级：1、金卡；2、银卡；3、铜卡；4、普通

	public String getTruckLabel() {
		return truckLabel;
	}

	public void setTruckLabel(String truckLabel) {
		this.truckLabel = truckLabel;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	@Override
	public String toString() {
		return "TruckOwner [truckLabel=" + truckLabel + ", vipLevel="
				+ vipLevel + ", id=" + id + "]";
	}
	
	
}
