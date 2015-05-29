package org.chinalbs.logistics.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.chinalbs.logistics.common.domain.BaseEntity;

@Entity
public class Feedback extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5889572455383920113L;

	private long userId;

	@Lob
	private String content;

    private String name;

	private String mobile;
	private String email;
	private int isProcessed; // 是否处理

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date processTime;// 处理时间

	private int isDeleted;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIsProcessed() {
		return isProcessed;
	}

	public void setIsProcessed(int isProcessed) {
		this.isProcessed = isProcessed;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
	public String toString() {
		return "Feedback [userId=" + userId + ", content=" + content
				+ ", mobile=" + mobile + ", email=" + email + ", isProcessed="
				+ isProcessed + ", createTime=" + createTime + ", processTime="
				+ processTime + ", isDeleted=" + isDeleted + ", id=" + id + "]";
	}
	
	

}
