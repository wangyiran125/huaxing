package org.chinalbs.logistics.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.chinalbs.logistics.common.domain.BaseEntity;

@Entity
public class Warehouse extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3705433884448775251L;

	private long goodsOwnerId;
	private int type;
	private int volume; // 仓库体积
	private int area; // 仓库面积
	private String district; // 仓库所在区域（北京/其他城市）
	private String address; // 仓库地址
	private int rent; // 租金
	private double residualCapacity; // 仓库剩余容量
	private double latitude; //货物所在纬度
	private double longitude;//货物所在经度
	
	@Lob
	private String picture; // 仓库照片url
	
	private String companyName; // 公司名称
	private int isCoolStore; 
	private String contactName;
	private String contactMobile;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime; //创建仓库时间
	
	private int isDeleted = 0;

	public long getGoodsOwnerId() {
		return goodsOwnerId;
	}

	public void setGoodsOwnerId(long goodsOwnerId) {
		this.goodsOwnerId = goodsOwnerId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public double getResidualCapacity() {
		return residualCapacity;
	}

	public void setResidualCapacity(double residualCapacity) {
		this.residualCapacity = residualCapacity;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public int getIsCoolStore() {
		return isCoolStore;
	}

	public void setIsCoolStore(int isCoolStore) {
		this.isCoolStore = isCoolStore;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "Warehouse [goodsOwnerId=" + goodsOwnerId + ", type=" + type
				+ ", volume=" + volume + ", area=" + area + ", district="
				+ district + ", address=" + address + ", rent=" + rent
				+ ", residualCapacity=" + residualCapacity
				+ ", latitude=" + latitude +",longitude="+longitude+", picture="
				+ picture + ", companyName=" + companyName + ", isCoolStore="
				+ isCoolStore + ", contactName=" + contactName
				+ ", contactMobile=" + contactMobile + ", createTime="
				+ createTime + ", isDeleted=" + isDeleted + ", id=" + id + "]";
	}
	
	

}
