package org.chinalbs.logistics.vo;

import java.util.Date;
import java.util.List;

import org.chinalbs.logistics.domain.Goods;
import org.chinalbs.logistics.domain.Truck;

@SuppressWarnings("serial")
public class OrderIntentInfo extends Goods {

	private long goodsId;
	private long orderId;
	private long truckOwnerId;
	private List<? extends Truck> trucks;
	private Date applyTime; // 抢单时间
	private int initiator; // 发起人：1、车主；2、货主
	private String licensePlateNumber;
	private String goodsOwnerName;
	private String goodsOwnerMobile;
	private String truckOwnerName;
	private String truckOwnerMobile;
	private int vipLevel;
	private String avatar;
	private String truckLabel;
	private int status;
	private int goodsOwnerIsEvaluated; // 货主是否已评 0 未评价， 1 已评价
	private String commentFromGoodsOwner;// 货主评价说明
	private int scoreFromGoodsOwner = -1;// 货主评分
	public long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public long getTruckOwnerId() {
		return truckOwnerId;
	}

	public void setTruckOwnerId(long truckOwnerId) {
		this.truckOwnerId = truckOwnerId;
	}

	public List<? extends Truck> getTrucks() {
		return trucks;
	}

	public void setTrucks(List<? extends Truck> trucks) {
		this.trucks = trucks;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public int getInitiator() {
		return initiator;
	}

	public void setInitiator(int initiator) {
		this.initiator = initiator;
	}

	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}

	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}

	public String getGoodsOwnerName() {
		return goodsOwnerName;
	}

	public void setGoodsOwnerName(String goodsOwnerName) {
		this.goodsOwnerName = goodsOwnerName;
	}

	public String getGoodsOwnerMobile() {
		return goodsOwnerMobile;
	}

	public void setGoodsOwnerMobile(String goodsOwnerMobile) {
		this.goodsOwnerMobile = goodsOwnerMobile;
	}

	public String getTruckOwnerName() {
		return truckOwnerName;
	}

	public void setTruckOwnerName(String truckOwnerName) {
		this.truckOwnerName = truckOwnerName;
	}

	public String getTruckOwnerMobile() {
		return truckOwnerMobile;
	}

	public void setTruckOwnerMobile(String truckOwnerMobile) {
		this.truckOwnerMobile = truckOwnerMobile;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTruckLabel() {
		return truckLabel;
	}

	public void setTruckLabel(String truckLabel) {
		this.truckLabel = truckLabel;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getGoodsOwnerIsEvaluated() {
		return goodsOwnerIsEvaluated;
	}

	public void setGoodsOwnerIsEvaluated(int goodsOwnerIsEvaluated) {
		this.goodsOwnerIsEvaluated = goodsOwnerIsEvaluated;
	}

	public String getCommentFromGoodsOwner() {
		return commentFromGoodsOwner;
	}

	public void setCommentFromGoodsOwner(String commentFromGoodsOwner) {
		this.commentFromGoodsOwner = commentFromGoodsOwner;
	}

	public int getScoreFromGoodsOwner() {
		return scoreFromGoodsOwner;
	}

	public void setScoreFromGoodsOwner(int scoreFromGoodsOwner) {
		this.scoreFromGoodsOwner = scoreFromGoodsOwner;
	}

	@Override
	public String toString() {
		return "OrderIntentInfo [getGoodsId()=" + getGoodsId()
				+ ", getTruckOwnerId()=" + getTruckOwnerId() + ", getTrucks()="
				+ getTrucks() + ", getApplyTime()=" + getApplyTime()
				+ ", getInitiator()=" + getInitiator()
				+ ", getLicensePlateNumber()=" + getLicensePlateNumber()
				+ ", getGoodsOwnerName()=" + getGoodsOwnerName()
				+ ", getGoodsOwnerMobile()=" + getGoodsOwnerMobile()
				+ ", getTruckOwnerName()=" + getTruckOwnerName()
				+ ", getTruckOwnerMobile()=" + getTruckOwnerMobile()
				+ ", getVipLevel()=" + getVipLevel()
				+ ", getStatus()=" + getStatus() + ", getGoodsOwnerId()="
				+ getGoodsOwnerId() + ", getGoodsName()=" + getGoodsName()
				+ ", getGoodsType()=" + getGoodsType() + ", getShippingType()="
				+ getShippingType() + ", getQuantity()=" + getQuantity()
				+ ", getVolume()=" + getVolume() + ", getWeight()="
				+ getWeight() + ", getShippingPrice()=" + getShippingPrice()
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
				+ ", getContactName()=" + getContactName()
				+ ", getContactMobile()=" + getContactMobile()
				+ ", getPhone()=" + getPhone() + ", getRemark()=" + getRemark()
				+ ", getIsDeleted()=" + getIsDeleted() + ", getId()=" + getId()
				+ "]";
	}


	
	

}
