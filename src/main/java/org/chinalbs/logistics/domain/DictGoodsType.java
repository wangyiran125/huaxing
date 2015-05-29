package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseDict;

@Entity
public class DictGoodsType extends BaseDict<Long> {

	@Override
	public String toString() {
		return "DictGoodsType [getCode()=" + getCode() + ", getName()="
				+ getName() + "]";
	}

	
}
