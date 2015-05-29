package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseDict;

@Entity
public class DictWarehouseArea extends BaseDict<Long> {

	@Override
	public String toString() {
		return "DictWarehouseArea [getCode()=" + getCode() + ", getName()="
				+ getName() + "]";
	}

}
