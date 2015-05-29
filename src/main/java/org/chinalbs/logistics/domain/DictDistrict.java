package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseDict;

@Entity
public class DictDistrict extends BaseDict<Long> {

	private Long parentCode;

	public Long getParentCode() {
		return parentCode;
	}

	public void setParentCode(Long parentCode) {
		this.parentCode = parentCode;
	}

	@Override
	public String toString() {
		return "DictDistrict [getParentCode()=" + getParentCode()
				+ ", getCode()=" + getCode() + ", getName()=" + getName()
				+  "]";
	}
	
	
	
}
