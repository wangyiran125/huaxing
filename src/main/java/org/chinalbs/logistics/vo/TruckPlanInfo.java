package org.chinalbs.logistics.vo;

import java.util.Date;

@SuppressWarnings("serial")
public class TruckPlanInfo extends TruckInfo{
	private long truckId;
	private long truckPlanId;
	private int isBackTruck;// 是否回程车
	private int isLongTerm;// 是否长期车
	private int departureProvinceCode;
	private int departureCityCode;
	private int destinationProvinceCode;
	private int destinationCityCode;
	private int shippingPrice;// 运价

	private Date departureTime; // 发车时间

	private Date publishTime;

	private String remark;

	private int isDeleted = 0;
	
	private String phrase; //常用语，格式：id1,id2

	private int isLocalFreeTruck; //1:本地空闲车，2：不是
	
	public long getTruckId() {
		return truckId;
	}

	public void setTruckId(long truckId) {
		this.truckId = truckId;
	}

	public long getTruckPlanId() {
		return truckPlanId;
	}

	public void setTruckPlanId(long truckPlanId) {
		this.truckPlanId = truckPlanId;
	}

	public int getIsBackTruck() {
		return isBackTruck;
	}

	public void setIsBackTruck(int isBackTruck) {
		this.isBackTruck = isBackTruck;
	}

	public int getIsLongTerm() {
		return isLongTerm;
	}

	public void setIsLongTerm(int isLongTerm) {
		this.isLongTerm = isLongTerm;
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

	public int getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(int shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
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

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public int getIsLocalFreeTruck() {
		return isLocalFreeTruck;
	}

	public void setIsLocalFreeTruck(int isLocalFreeTruck) {
		this.isLocalFreeTruck = isLocalFreeTruck;
	}

	@Override
	public String toString() {
		return "TruckPlanInfo [getTruckId()=" + getTruckId()
				+ ", getTruckPlanId()=" + getTruckPlanId()
				+ ", getIsBackTruck()=" + getIsBackTruck()
				+ ", getIsLongTerm()=" + getIsLongTerm()
				+ ", getDepartureProvinceCode()=" + getDepartureProvinceCode()
				+ ", getDepartureCityCode()=" + getDepartureCityCode()
				+ ", getDestinationProvinceCode()="
				+ getDestinationProvinceCode() + ", getDestinationCityCode()="
				+ getDestinationCityCode() + ", getShippingPrice()="
				+ getShippingPrice() + ", getDepartureTime()="
				+ getDepartureTime() + ", getPublishTime()=" + getPublishTime()
				+ ", getRemark()=" + getRemark() + ", getIsDeleted()="
				+ getIsDeleted() + ", getLan()=" + getLan() + ", getLat()="
				+ getLat() + ", getTruckOwnerId()=" + getTruckOwnerId()
				+ ", getTruckLength()=" + getTruckLength()
				+ ", getLicensePlateNumber()=" + getLicensePlateNumber()
				+ ", getTruckLoad()=" + getTruckLoad() + ", getVolume()="
				+ getVolume() + ", getTruckStation()=" + getTruckStation()
				+ ", getTruckAddress()=" + getTruckAddress() + ", getType()="
				+ getType() + ", getBrand()=" + getBrand()
				+ ", getTruckCondition()=" + getTruckCondition()
				+ ", getTruckOwnerPhone()=" + getTruckOwnerPhone()
				+ ", getPatcher()=" + getPatcher() + ", getTruckUDID()="
				+ getTruckUDID() + ", getTruckPicture()=" + getTruckPicture()
				+ ", getTruckLicensePicture()=" + getTruckLicensePicture()
				+ ", getTruckStatus()=" + getTruckStatus()
				+ ", getResidualCapacity()=" + getResidualCapacity()
				+ ", getDeviceKey()=" + getDeviceKey() + ", getDevicePhone()="
				+ getDevicePhone() + ", getAlarmCall()=" + getAlarmCall()
				+ ", getIntentPermission()=" + getIntentPermission()
				+ ", getId()=" + getId() + ", getPhrase()=" + getPhrase() + ", getIsLocalFreeTruck()=" + getIsLocalFreeTruck() + "]";
	}
	
	
}
