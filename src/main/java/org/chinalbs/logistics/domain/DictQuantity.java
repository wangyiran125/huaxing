package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseDict;

@Entity
public class DictQuantity extends BaseDict<Long> {

	@Override
	public String toString() {
		return "DictQuantity [getCode()=" + getCode() + ", getName()="
				+ getName() + "]";
	}

	
}
