package org.chinalbs.logistics.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.chinalbs.logistics.common.domain.BaseEntity;

@Entity
public class Score extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1835726769185788914L;

	private long userId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date recordTime;

	@Lob
	private String description;
	private int points;
	
	public Score(long userId, Date recordTime, String description, int points) {
		this.userId = userId;
		this.recordTime = recordTime;
		this.description = description;
		this.points = points;
	}
	
	public Score() {
		
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "Score [userId=" + userId + ", recordTime=" + recordTime
				+ ", description=" + description + ", points=" + points
				+ ", id=" + id + "]";
	}
	
	

}
