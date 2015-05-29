package org.chinalbs.logistics.domain;

import javax.persistence.MappedSuperclass;

import org.chinalbs.logistics.common.domain.BaseEntity;

@SuppressWarnings("serial")
@MappedSuperclass
public class UserCommon extends BaseEntity<Long> {
	private long userId;
	private String name;
	private String idCard;
	private String mobile;
	private String phone;
	private String qq;
	private String email;
	private String avatar;
	private int isDeleted = 0;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "UserCommon [userId=" + userId + ", name=" + name + ", idCard="
				+ idCard + ", mobile=" + mobile + ", phone=" + phone + ", qq="
				+ qq + ", email=" + email + ", avatar=" + avatar
				+ ", isDeleted=" + isDeleted + ", id=" + id + "]";
	}
	
	
}
