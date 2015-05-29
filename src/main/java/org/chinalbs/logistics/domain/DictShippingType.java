package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseDict;

@Entity
public class DictShippingType extends BaseDict<Long> {

	@Override
	public String toString() {
		return "DictShippingType [getCode()=" + getCode() + ", getName()="
				+ getName() +  "]";
	}

	
}
