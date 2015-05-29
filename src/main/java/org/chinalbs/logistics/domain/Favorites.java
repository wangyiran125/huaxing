package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseEntity;

@Entity
public class Favorites extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8250391632097468337L;

	private long goodsOwnerId;
	private long truckOwnerId;
 
	public long getGoodsOwnerId() {
		return goodsOwnerId;
	}

	public void setGoodsOwnerId(long goodsOwnerId) {
		this.goodsOwnerId = goodsOwnerId;
	}

	public long getTruckOwnerId() {
		return truckOwnerId;
	}

	public void setTruckOwnerId(long truckOwnerId) {
		this.truckOwnerId = truckOwnerId;
	}

	@Override
	public String toString() {
		return "Favorites [goodsOwnerId=" + goodsOwnerId + ", truckOwnerId="
				+ truckOwnerId + ", id=" + id + "]";
	}
	
	

}
