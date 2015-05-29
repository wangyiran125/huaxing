 package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseDict;

@Entity
public class DictGoodsVolume extends BaseDict<Long> {

	@Override
	public String toString() {
		return "DictGoodsVolume [getCode()=" + getCode() + ", getName()="
				+ getName() + "]";
	}

	
}
