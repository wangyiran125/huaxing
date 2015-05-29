package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseDict;

@Entity
public class DictTruckLoad extends BaseDict<Long> {

	@Override
	public String toString() {
		return "DictTruckLoad [getCode()=" + getCode() + ", getName()="
				+ getName() +  "]";
	}

	
}
