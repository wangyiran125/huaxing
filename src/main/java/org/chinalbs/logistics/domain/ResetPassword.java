package org.chinalbs.logistics.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.chinalbs.logistics.common.domain.BaseEntity;

@Entity
public class ResetPassword extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -128799117011400587L;

	private long userId; //用户Id
	private String username; //用户名
	private String email; //忘记密码使用邮箱
	private int status; //请求处理状态，0：未处理，1：成功处理，-1：处理失败
	private String token; //本次请求token
	@Temporal(TemporalType.TIMESTAMP)
	private Date applyTime; //申请时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date processTime; //处理时间
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Date getProcessTime() {
		return processTime;
	}
	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}
}
