package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseDict;

@Entity
public class DictTruckType extends BaseDict<Long> {

	@Override
	public String toString() {
		return "DictTruckType [getCode()=" + getCode() + ", getName()="
				+ getName() +  "]";
	}

}
