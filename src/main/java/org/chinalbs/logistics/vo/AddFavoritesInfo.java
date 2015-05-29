package org.chinalbs.logistics.vo;

public class AddFavoritesInfo {

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
		return "AddFavoritesInfo [getGoodsOwnerId()=" + getGoodsOwnerId()
				+ ", getTruckOwnerId()=" + getTruckOwnerId() + "]";
	}
	
	

}
