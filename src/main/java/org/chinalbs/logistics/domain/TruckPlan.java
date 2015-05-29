package org.chinalbs.logistics.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.chinalbs.logistics.common.domain.BaseEntity;

@Entity
public class TruckPlan extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7261990345304753436L;

	private long truckId;
	private long truckOwnerId;
	private int isBackTruck;// 是否回程车
	private int isLongTerm;// 是否长期车
	private int departureProvinceCode;
	private int departureCityCode;
	private int destinationProvinceCode;
	private int destinationCityCode;
	private int shippingPrice;//运价

	@Temporal(TemporalType.TIMESTAMP)
	private Date departureTime; //发车时间

	@Temporal(TemporalType.TIMESTAMP)
	private Date publishTime;

	private String phrase; //常用语，格式：id1,id2
	
	@Lob
	private String remark;

	private int isDeleted = 0;
	
	private int isLocalFreeTruck; //1:本地空闲车，2：不是

	public long getTruckId() {
		return truckId;
	}

	public void setTruckId(long truckId) {
		this.truckId = truckId;
	}

	public long getTruckOwnerId() {
		return truckOwnerId;
	}

	public void setTruckOwnerId(long truckOwnerId) {
		this.truckOwnerId = truckOwnerId;
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
		return "TruckPlan [truckId=" + truckId + ", truckOwnerId="
				+ truckOwnerId + ", isBackTruck=" + isBackTruck
				+ ", isLongTerm=" + isLongTerm + ", departureProvinceCode="
				+ departureProvinceCode + ", departureCityCode="
				+ departureCityCode + ", destinationProvinceCode="
				+ destinationProvinceCode + ", destinationCityCode="
				+ destinationCityCode + ", shippingPrice=" + shippingPrice
				+ ", departureTime=" + departureTime + ", publishTime="
				+ publishTime + ", remark=" + remark + ", isDeleted="
				+ isDeleted + ", phrase=" + phrase + ", id=" + id + ", isLocalFreeTruck=" + isLocalFreeTruck + "]";
	}
	
	

}
