package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseDict;

@Entity
public class DictTruckCondition extends BaseDict<Long> {

	@Override
	public String toString() {
		return "DictTruckCondition [getCode()=" + getCode() + ", getName()="
				+ getName() + "]";
	}

	
}
