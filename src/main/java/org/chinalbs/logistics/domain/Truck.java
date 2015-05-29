package org.chinalbs.logistics.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;

import org.chinalbs.logistics.common.domain.BaseEntity;

@Entity
public class Truck extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1051304737452808037L;
	private long truckOwnerId; // 车主Id
	private String licensePlateNumber=""; // 车牌号
	private double truckLength; // 车长
	private double truckLoad; // 载重
	private double volume; // 体积
	private String truckStation; // 车辆常驻地
	private String truckAddress; // 车辆常停放地址
	private int type; // 车辆类型
	private String brand; // 品牌型号
	private int truckCondition; // 车体状况
	private String truckOwnerPhone;
	private String patcher; // 随车联系人
	private String truckUDID; // 车辆识别码
	@Lob
	private String truckPicture; // 车辆照片url
	private String truckLicensePicture; // 行车证照片url
	private int truckStatus = 0; // 车的状态：1、空车，2、半满，3、已满
	private int intentPermission = 1; // 车的状态：1、可抢单 2、不可抢单
	private String residualCapacity; // 剩余容量（吨）
	private String deviceKey;
	private String devicePhone = "";
	private String alarmCall = "";
	private int isDeleted = 0;

	public long getTruckOwnerId() {
		return truckOwnerId;
	}

	public void setTruckOwnerId(long truckOwnerId) {
		this.truckOwnerId = truckOwnerId;
	}



	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}

	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}


	public double getTruckLength() {
		return truckLength;
	}

	public void setTruckLength(double truckLength) {
		this.truckLength = truckLength;
	}

	public double getTruckLoad() {
		return truckLoad;
	}

	public void setTruckLoad(double truckLoad) {
		this.truckLoad = truckLoad;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public String getTruckStation() {
		return truckStation;
	}

	public void setTruckStation(String truckStation) {
		this.truckStation = truckStation;
	}

	public String getTruckAddress() {
		return truckAddress;
	}

	public void setTruckAddress(String truckAddress) {
		this.truckAddress = truckAddress;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getTruckCondition() {
		return truckCondition;
	}

	public void setTruckCondition(int truckCondition) {
		this.truckCondition = truckCondition;
	}

	public String getTruckOwnerPhone() {
		return truckOwnerPhone;
	}

	public void setTruckOwnerPhone(String truckOwnerPhone) {
		this.truckOwnerPhone = truckOwnerPhone;
	}

	public String getPatcher() {
		return patcher;
	}

	public void setPatcher(String patcher) {
		this.patcher = patcher;
	}

	public String getTruckUDID() {
		return truckUDID;
	}

	public void setTruckUDID(String truckUDID) {
		this.truckUDID = truckUDID;
	}

	public String getTruckPicture() {
		return truckPicture;
	}

	public void setTruckPicture(String truckPicture) {
		this.truckPicture = truckPicture;
	}

	public String getTruckLicensePicture() {
		return truckLicensePicture;
	}

	public void setTruckLicensePicture(String truckLicensePicture) {
		this.truckLicensePicture = truckLicensePicture;
	}

	public int getTruckStatus() {
		return truckStatus;
	}

	public void setTruckStatus(int truckStatus) {
		this.truckStatus = truckStatus;
	}

	public String getResidualCapacity() {
		return residualCapacity;
	}

	public void setResidualCapacity(String residualCapacity) {
		this.residualCapacity = residualCapacity;
	}

	public String getDeviceKey() {
		return deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public String getDevicePhone() {
		return devicePhone;
	}

	public void setDevicePhone(String devicePhone) {
		this.devicePhone = devicePhone;
	}

	public String getAlarmCall() {
		return alarmCall;
	}

	public void setAlarmCall(String alarmCall) {
		this.alarmCall = alarmCall;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getIntentPermission() {
		return intentPermission;
	}

	public void setIntentPermission(int intentPermission) {
		this.intentPermission = intentPermission;
	}

	@Override
	public String toString() {
		return "Truck [truckOwnerId=" + truckOwnerId + ", licensePlateNumber="
				+ licensePlateNumber + ", truckLength=" + truckLength
				+ ", truckLoad=" + truckLoad + ", volume=" + volume
				+ ", truckStation=" + truckStation + ", truckAddress="
				+ truckAddress + ", type=" + type + ", brand=" + brand
				+ ", truckCondition=" + truckCondition + ", truckOwnerPhone="
				+ truckOwnerPhone + ", patcher=" + patcher + ", truckUDID="
				+ truckUDID + ", truckPicture=" + truckPicture
				+ ", truckLicensePicture=" + truckLicensePicture
				+ ", truckStatus=" + truckStatus + ", intentPermission="
				+ intentPermission + ", residualCapacity=" + residualCapacity
				+ ", deviceKey=" + deviceKey + ", devicePhone=" + devicePhone
				+ ", alarmCall=" + alarmCall + ", isDeleted=" + isDeleted
				+ ", id=" + id + "]";
	}
	
	

}
