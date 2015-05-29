package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseDict;

@Entity
public class DictTruckLength extends BaseDict<Long> {

	@Override
	public String toString() {
		return "DictTruckLength [getCode()=" + getCode() + ", getName()="
				+ getName() + "]";
	}

	
}
