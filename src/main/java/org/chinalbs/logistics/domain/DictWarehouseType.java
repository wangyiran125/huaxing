package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseDict;

@Entity
public class DictWarehouseType extends BaseDict<Long> {

	@Override
	public String toString() {
		return "DictWarehouseType [getCode()=" + getCode() + ", getName()="
				+ getName() + "]";
	}

}
