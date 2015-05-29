package org.chinalbs.logistics.vo;

import org.chinalbs.logistics.domain.Warehouse;

@SuppressWarnings("serial")
public class WarehouseInfo extends Warehouse {

	@Override
	public String toString() {
		return "WarehouseInfo [getGoodsOwnerId()=" + getGoodsOwnerId()
				+ ", getType()=" + getType() + ", getVolume()=" + getVolume()
				+ ", getArea()=" + getArea() + ", getDistrict()="
				+ getDistrict() + ", getAddress()=" + getAddress()
				+ ", getRent()=" + getRent() + ", getResidualCapacity()="
				+ getResidualCapacity()
				+",getLatitude" + getLatitude()
				+",getLongitude" + getLongitude()
				+ ", getPicture()=" + getPicture()
				+ ", getCompanyName()=" + getCompanyName()
				+ ", getIsCoolStore()=" + getIsCoolStore()
				+ ", getContactName()=" + getContactName()
				+ ", getContactMobile()=" + getContactMobile()
				+ ", getCreateTime()=" + getCreateTime() + ", getIsDeleted()="
				+ getIsDeleted() + ", getId()=" + getId() + "]";
	}

	
	
}
