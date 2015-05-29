package org.chinalbs.logistics.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.chinalbs.logistics.common.domain.BaseEntity;

@Entity
public class LogisticsUser extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4856447957011571619L;

    @Column(unique = true)
	private String username;//登陆账号
    @Column(nullable = false)
	private String password;//登陆密码

    @Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date registerTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginTime;

	private int isDeleted = 0; //-1表示被删除，不可登陆
	private int role;//用户角色：1、管理员；2、仓库主；3、货主；4、车主 5、司机

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

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "LogisticsUser [username=" + username + ", password=" + password
				+ ", registerTime=" + registerTime + ", lastLoginTime="
				+ lastLoginTime + ", isDeleted=" + isDeleted + ", role=" + role
				+ ", id=" + id + "]";
	}
	
	

}
