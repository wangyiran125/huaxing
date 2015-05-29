package org.chinalbs.logistics.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.chinalbs.logistics.common.domain.BaseEntity;

@Entity
public class LogisticsOrder extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7152190235372075005L;

	private long goodsId; // 货物Id
	private long truckOwnerId; // 车主Id

	private int status; //订单状态：1、（货主）已发布；2、已抢单；3、（货主）确认发货 4、（车主）确认提货 5，（货主）确认到货， 

	@Temporal(TemporalType.TIMESTAMP)
	private Date orderTime;

	private int truckOwnerIsEvaluated; // 车主是否已评 1 未评价， 2 已评价
	private int goodsOwnerIsEvaluated; // 货主是否已评 1 未评价， 2 已评价
	private int scoreFromGoodsOwner = -1;// 货主评分
	private int commentLevelFromGoodsOwner;// 货主评价等级 1好评，2中评，3差评
	
	@Lob
	private String commentFromGoodsOwner;// 货主评价说明
	private int scoreFromTruckOwner = -1;// 车主评分
	private int commentLevelFromTruckOwner;// 车主评价等级 1好评，2中评，3差评
	
	@Lob
	private String commentFromTruckOwner; // 车主评价说明

	private Date commentTimeFromGoodsOwner;
	private Date commentTimeFromTruckOwner;



	private int isDeleted = 0;

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

	public int getTruckOwnerIsEvaluated() {
		return truckOwnerIsEvaluated;
	}

	public void setTruckOwnerIsEvaluated(int truckOwnerIsEvaluated) {
		this.truckOwnerIsEvaluated = truckOwnerIsEvaluated;
	}

	public int getGoodsOwnerIsEvaluated() {
		return goodsOwnerIsEvaluated;
	}

	public void setGoodsOwnerIsEvaluated(int goodsOwnerIsEvaluated) {
		this.goodsOwnerIsEvaluated = goodsOwnerIsEvaluated;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}	
	
	
	public int getScoreFromGoodsOwner() {
		return scoreFromGoodsOwner;
	}

	public void setScoreFromGoodsOwner(int scoreFromGoodsOwner) {
		this.scoreFromGoodsOwner = scoreFromGoodsOwner;
	}

	public String getCommentFromGoodsOwner() {
		return commentFromGoodsOwner;
	}

	public void setCommentFromGoodsOwner(String commentFromGoodsOwner) {
		this.commentFromGoodsOwner = commentFromGoodsOwner;
	}

	public int getScoreFromTruckOwner() {
		return scoreFromTruckOwner;
	}

	public void setScoreFromTruckOwner(int scoreFromTruckOwner) {
		this.scoreFromTruckOwner = scoreFromTruckOwner;
	}

	public String getCommentFromTruckOwner() {
		return commentFromTruckOwner;
	}

	public void setCommentFromTruckOwner(String commentFromTruckOwner) {
		this.commentFromTruckOwner = commentFromTruckOwner;
	}

	public Date getCommentTimeFromGoodsOwner() {
		return commentTimeFromGoodsOwner;
	}

	public void setCommentTimeFromGoodsOwner(Date commentTimeFromGoodsOwner) {
		this.commentTimeFromGoodsOwner = commentTimeFromGoodsOwner;
	}

	public Date getCommentTimeFromTruckOwner() {
		return commentTimeFromTruckOwner;
	}

	public void setCommentTimeFromTruckOwner(Date commentTimeFromTruckOwner) {
		this.commentTimeFromTruckOwner = commentTimeFromTruckOwner;
	}

	public int getCommentLevelFromGoodsOwner() {
		return commentLevelFromGoodsOwner;
	}

	public void setCommentLevelFromGoodsOwner(int commentLevelFromGoodsOwner) {
		this.commentLevelFromGoodsOwner = commentLevelFromGoodsOwner;
	}

	public int getCommentLevelFromTruckOwner() {
		return commentLevelFromTruckOwner;
	}

	public void setCommentLevelFromTruckOwner(int commentLevelFromTruckOwner) {
		this.commentLevelFromTruckOwner = commentLevelFromTruckOwner;
	}

	@Override
	public String toString() {
		return "LogisticsOrder [goodsId=" + goodsId + ", truckOwnerId="
				+ truckOwnerId + ", status=" + status + ", orderTime="
				+ orderTime + ", truckOwnerIsEvaluated="
				+ truckOwnerIsEvaluated + ", goodsOwnerIsEvaluated="
				+ goodsOwnerIsEvaluated + ", scoreFromGoodsOwner="
				+ scoreFromGoodsOwner + ", commentFromGoodsOwner="
				+ commentFromGoodsOwner + ", scoreFromTruckOwner="
				+ scoreFromTruckOwner + ", commentFromTruckOwner="
				+ commentFromTruckOwner + ", commentTimeFromGoodsOwner="
				+ commentTimeFromGoodsOwner + ", commentTimeFromTruckOwner="
				+ commentFromTruckOwner + ", commentLevelFromGoodsOwner="
				+ commentLevelFromGoodsOwner + ", commentLevelFromTruckOwner="
				+ commentLevelFromTruckOwner + ", isDeleted=" + isDeleted
				+ ", id=" + id + "]";
	}
	
	

}
