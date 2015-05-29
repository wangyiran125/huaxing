package org.chinalbs.logistics.vo;

public class UserInfo {

	private long userId;
	private String username;
	private String password;
	private int role;
	private String name;
	private String idCard;
	private String mobile;
	private String phone;
	private String qq;
	private String email;
	private String avatar;
	private String truckLabel;
	private int vipLevel;
	private int truckId;
	private int score;
	private int isDeleted;
    private String licensePlateNumber;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
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

	public int getTruckId() {
		return truckId;
	}

	public void setTruckId(int truckId) {
		this.truckId = truckId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "UserInfo [getUserId()=" + getUserId() + ", getUsername()="
				+ getUsername() + ", getPassword()=" + getPassword()
				+ ", getRole()=" + getRole() + ", getName()=" + getName()
				+ ", getIdCard()=" + getIdCard() + ", getMobile()="
				+ getMobile() + ", getPhone()=" + getPhone() + ", getQq()="
				+ getQq() + ", getEmail()=" + getEmail() + ", getAvatar()="
				+ getAvatar() + ", getTruckLabel()=" + getTruckLabel()
				+ ", getVipLevel()=" + getVipLevel() + ", getTruckId()="
				+ getTruckId() + ", getScore()=" + getScore() + ", getIsDeleted()=" + getIsDeleted() + "]";
	}


    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }
}
