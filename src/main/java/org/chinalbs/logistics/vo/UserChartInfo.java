package org.chinalbs.logistics.vo;

public class UserChartInfo {
	private long userId; //用户ID
	private String username; //用户名
	private String name; //姓名
	private String phone; //手机号
	private long goodsNum; //货主发货数
	private long goodsIntentNum; //车主抢单数
	private long orderNum; //成单数
	private int score; //积分
	
	public UserChartInfo(){}
	
	public UserChartInfo(long userId, String username, String name, String phone, long goodsNum, long goodsIntentNum, long orderNum, int score) {
		this.userId = userId;
		this.username = username;
		this.name = name;
		this.phone = phone;
		this.goodsNum = goodsNum;
		this.goodsIntentNum = goodsIntentNum;
		this.orderNum = orderNum;
		this.score = score;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public long getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(long goodsNum) {
		this.goodsNum = goodsNum;
	}
	public long getGoodsIntentNum() {
		return goodsIntentNum;
	}
	public void setGoodsIntentNum(long goodsIntentNum) {
		this.goodsIntentNum = goodsIntentNum;
	}
	public long getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(long orderNum) {
		this.orderNum = orderNum;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "UserChartInfo [getUserId()=" + getUserId() + ", getUsername()="
				+ getUsername() + ", getName()=" + getName() + ", getPhone()="
				+ getPhone() + ", getGoodsNum()=" + getGoodsNum()
				+ ", getGoodsIntentNum()=" + getGoodsIntentNum()
				+ ", getOrderNum()=" + getOrderNum() + ", getScore()="
				+ getScore() + "]";
	}
	
	
}
