package org.chinalbs.logistics.vo;

public class FavoritesInfo {

	private Long favoritesId; // 收藏ID
	private Long truckOwnerId;
	private String name;
	private String idCard;
	private String mobile;
	private String phone;
	private String qq;
	private String email;
	private String avatar;
	private String truckLabel;
	private int vipLevel;

//	public FavoritesInfo(Long favoritesId, Long truckOwnerId, String name,
//			String idCard, String mobile, String phone, String qq,
//			String email, String avatar, String truckLabel, int vipLevel) {
//		this.favoritesId = favoritesId;
//		this.truckOwnerId = truckOwnerId;
//		this.name = name;
//		this.idCard = idCard;
//		this.mobile = mobile;
//		this.phone = phone;
//		this.qq = qq;
//		this.email = email;
//		this.avatar = avatar;
//		this.truckLabel = truckLabel;
//		this.vipLevel = vipLevel;
//	}

	public Long getFavoritesId() {
		return favoritesId;
	}

	public void setFavoritesId(Long favoritesId) {
		this.favoritesId = favoritesId;
	}

	public Long getTruckOwnerId() {
		return truckOwnerId;
	}

	public void setTruckOwnerId(Long truckOwnerId) {
		this.truckOwnerId = truckOwnerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	@Override
	public String toString() {
		return "FavoritesInfo [getFavoritesId()=" + getFavoritesId()
				+ ", getTruckOwnerId()=" + getTruckOwnerId() + ", getName()="
				+ getName() + ", getIdCard()=" + getIdCard() + ", getMobile()="
				+ getMobile() + ", getPhone()=" + getPhone() + ", getQq()="
				+ getQq() + ", getEmail()=" + getEmail() + ", getAvatar()="
				+ getAvatar() + ", getTruckLabel()=" + getTruckLabel()
				+ ", getVipLevel()=" + getVipLevel() + "]";
	}

	
}
