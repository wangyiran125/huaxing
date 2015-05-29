package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseEntity;

@Entity
public class VipLevel extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 410092276283050345L;

	private int level;
	private String name;
	private int delayTime; // 延迟时间，默认为分钟

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}

	@Override
	public String toString() {
		return "VipLevel [level=" + level + ", name=" + name + ", delayTime="
				+ delayTime + ", id=" + id + "]";
	}
	
	
}
