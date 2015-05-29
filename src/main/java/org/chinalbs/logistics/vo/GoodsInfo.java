package org.chinalbs.logistics.vo;

import org.chinalbs.logistics.domain.Goods;

@SuppressWarnings("serial")
public class GoodsInfo extends Goods {
	
	private int status; //货物状态
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "GoodsInfo [getGoodsOwnerId()=" + getGoodsOwnerId()
				+ ", getGoodsName()=" + getGoodsName() + ", getGoodsType()="
				+ getGoodsType() + ", getShippingType()=" + getShippingType()
				+ ", getQuantity()=" + getQuantity() + ", getVolume()="
				+ getVolume() + ", getWeight()=" + getWeight()
				+ ", getShippingPrice()=" + getShippingPrice()
				+ ", getDepartureProvinceCode()=" + getDepartureProvinceCode()
				+ ", getDepartureCityCode()=" + getDepartureCityCode()
				+ ", getDeparture()=" + getDeparture()
				+ ", getDestinationProvinceCode()="
				+ getDestinationProvinceCode() + ", getDestinationCityCode()="
				+ getDestinationCityCode() + ", getDestination()="
				+ getDestination() + ", getLatitude()=" + getLatitude()
				+ ", getLongitude()=" + getLongitude()
				+ ", getDepartureTime()=" + getDepartureTime()
				+ ", getGoodsAddress()=" + getGoodsAddress()
				+ ", getPublishTime()=" + getPublishTime() + ", getValidity()="
				+ getValidity() + ", getNotice()=" + getNotice()
				+",getPicture()=" + getPicture()
				+ ", getContactName()=" + getContactName()
				+ ", getContactMobile()=" + getContactMobile()
				+ ", getPhone()=" + getPhone() + ", getRemark()=" + getRemark()
				+ ", getIsDeleted()=" + getIsDeleted() + ", getId()=" + getId()
				+ "]";
	}
	
	
}
