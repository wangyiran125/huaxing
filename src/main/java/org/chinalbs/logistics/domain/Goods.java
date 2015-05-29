package org.chinalbs.logistics.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.chinalbs.logistics.common.domain.BaseEntity;

@Entity
public class Goods extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8535002263645872755L;

	private long goodsOwnerId;
	private int goodsName;
	private int goodsType;
	private int shippingType;
	private int quantity;
	private double volume;
	private double weight;
	private int shippingPrice;
	private int departureProvinceCode;
	private int departureCityCode;
	private String departure;// 起始地详细地址
	private int destinationProvinceCode;
	private int destinationCityCode;
	private String destination;// 目的地详细地址
	private double latitude; //货物所在纬度
	private double longitude;//货物所在经度
	private String goodsAddress;//货物所在地址
	@Lob
	private String picture;
	
	private Date departureTime; // 发货时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date publishTime;

	private String validity; // 有效期或长期货源

	private String notice; // 注意事项
	private String contactName;// 联系人
	private String contactMobile;
	private String phone;

	@Lob
	private String remark;

	private int isDeleted = 0;

	public long getGoodsOwnerId() {
		return goodsOwnerId;
	}

	public void setGoodsOwnerId(long goodsOwnerId) {
		this.goodsOwnerId = goodsOwnerId;
	}

	public int getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(int goodsName) {
		this.goodsName = goodsName;
	}

	public int getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	public int getShippingType() {
		return shippingType;
	}

	public void setShippingType(int shippingType) {
		this.shippingType = shippingType;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(int shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public int getDepartureProvinceCode() {
		return departureProvinceCode;
	}

	public void setDepartureProvinceCode(int departureProvinceCode) {
		this.departureProvinceCode = departureProvinceCode;
	}

	public int getDepartureCityCode() {
		return departureCityCode;
	}

	public void setDepartureCityCode(int departureCityCode) {
		this.departureCityCode = departureCityCode;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public int getDestinationProvinceCode() {
		return destinationProvinceCode;
	}

	public void setDestinationProvinceCode(int destinationProvinceCode) {
		this.destinationProvinceCode = destinationProvinceCode;
	}

	public int getDestinationCityCode() {
		return destinationCityCode;
	}

	public void setDestinationCityCode(int destinationCityCode) {
		this.destinationCityCode = destinationCityCode;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
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

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public String getGoodsAddress() {
		return goodsAddress;
	}

	public void setGoodsAddress(String goodsAddress) {
		this.goodsAddress = goodsAddress;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "Goods [goodsOwnerId=" + goodsOwnerId + ", goodsName="
				+ goodsName + ", goodsType=" + goodsType + ", shippingType="
				+ shippingType + ", quantity=" + quantity + ", volume="
				+ volume + ", weight=" + weight + ", shippingPrice="
				+ shippingPrice + ", departureProvinceCode="
				+ departureProvinceCode + ", departureCityCode="
				+ departureCityCode + ", departure=" + departure
				+ ", destinationProvinceCode=" + destinationProvinceCode
				+ ", destinationCityCode=" + destinationCityCode
				+ ", destination=" + destination + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", goodsAddress=" + goodsAddress
				+",picture = " + picture 
				+ ", departureTime=" + departureTime + ", publishTime="
				+ publishTime + ", validity=" + validity + ", notice=" + notice
				+ ", contactName=" + contactName + ", contactMobile="
				+ contactMobile + ", phone=" + phone + ", remark=" + remark
				+ ", isDeleted=" + isDeleted + ", id=" + id + "]";
	}
	
	
}
