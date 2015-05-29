package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseEntity;

@Entity
public class Address extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -128799117011400587L;

	private long userId;
	private int provinceCode;
	private int cityCode;
	private String address;
	private int flag; // 默认标记：1、默认收货地址；2、默认发货地址


	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(int provinceCode) {
		this.provinceCode = provinceCode;
	}

	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "Address [getUserId()=" + getUserId() + ", getProvinceCode()="
				+ getProvinceCode() + ", getCityCode()=" + getCityCode()
				+ ", getAddress()=" + getAddress() + ", getFlag()=" + getFlag()
				+ ", getId()=" + getId() + "]";
	}
	
	

}
