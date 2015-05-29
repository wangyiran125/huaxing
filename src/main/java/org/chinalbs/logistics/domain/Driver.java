package org.chinalbs.logistics.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Driver extends UserCommon {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4274682989699733763L;

	private long parentUserId; // 车队管理员Id
	private long truckId;
    @Column(nullable = false)
	private String truckLabel;// 车标

	public long getParentUserId() {
		return parentUserId;
	}

	public void setParentUserId(long parentUserId) {
		this.parentUserId = parentUserId;
	}

	public long getTruckId() {
		return truckId;
	}

	public void setTruckId(long truckId) {
		this.truckId = truckId;
	}

	public String getTruckLabel() {
		return truckLabel;
	}

	public void setTruckLabel(String truckLabel) {
		this.truckLabel = truckLabel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Driver [getParentUserId()=" + getParentUserId()
				+ ", getTruckId()=" + getTruckId() + ", getTruckLabel()="
				+ getTruckLabel() + ", getUserId()=" + getUserId()
				+ ", getName()=" + getName() + ", getIdCard()=" + getIdCard()
				+ ", getMobile()=" + getMobile() + ", getPhone()=" + getPhone()
				+ ", getQq()=" + getQq() + ", getEmail()=" + getEmail()
				+ ", getAvatar()=" + getAvatar() + ", getIsDeleted()="
				+ getIsDeleted() + "]";
	}
	
	
}
