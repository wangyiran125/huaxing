package org.chinalbs.logistics.vo;

import org.chinalbs.logistics.domain.Truck;

@SuppressWarnings("serial")
public class TruckInfo extends Truck {

	public TruckInfo() {
		
	}
	
	private String lan;//经度
	private String lat;//纬度
	
	public String getLan() {
		return lan;
	}

	public void setLan(String lan) {
		this.lan = lan;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	@Override
	public String toString() {
		return "TruckInfo [getLan()=" + getLan() + ", getLat()=" + getLat()
				+ ", getTruckOwnerId()=" + getTruckOwnerId()
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
				+ ", getIsDeleted()=" + getIsDeleted()
				+ ", getIntentPermission()=" + getIntentPermission()
				+ ", getId()=" + getId() +  "]";
	}
	
	
}
