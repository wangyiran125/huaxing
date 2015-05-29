package org.chinalbs.logistics.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.chinalbs.logistics.common.domain.BaseEntity;

@Entity
public class OrderIntent extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4971879446209434715L;

	private long goodsId;
	private long truckOwnerId;
	private String truckIds;
	@Temporal(TemporalType.TIMESTAMP)
	private Date applyTime; // 抢单时间

	private int initiator; // 发起人：1、车主；2、货主
	private int isDeleted = 0;
	private int status = 1 ; //1、生成抢单， 2 货主已拒绝，3、货主确认发货，4、车主确认提货，5、货主确认到货（完成），-1、车主取消抢单

	public long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	public long getTruckOwnerId() {
		return truckOwnerId;
	}

	public void setTruckOwnerId(long truckOwnerId) {
		this.truckOwnerId = truckOwnerId;
	}

	public String getTruckIds() {
		return truckIds;
	}

	public void setTruckIds(String truckIds) {
		this.truckIds = truckIds;
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

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OrderIntent [goodsId=" + goodsId + ", truckOwnerId="
				+ truckOwnerId + ", truckIds=" + truckIds + ", applyTime="
				+ applyTime + ", initiator=" + initiator + ", isDeleted="
				+ isDeleted + ", status=" + status + ", id=" + id + "]";
	}
	
	

}
