package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseDict;

@Entity
public class DictGoodsName extends BaseDict<Long> {

	@Override
	public String toString() {
		return "DictGoodsName [getCode()=" + getCode() + ", getName()="
				+ getName() +  "]";
	}

	
}
