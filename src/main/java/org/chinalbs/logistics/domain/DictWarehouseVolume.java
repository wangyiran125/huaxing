package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseDict;

@Entity
public class DictWarehouseVolume extends BaseDict<Long> {

	@Override
	public String toString() {
		return "DictWarehouseVolume [getCode()=" + getCode() + ", getName()="
				+ getName() +  "]";
	}
	

}
