package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseDict;

@Entity
public class DictGoodsWeight extends BaseDict<Long> {

	@Override
	public String toString() {
		return "DictGoodsWeight [getCode()=" + getCode() + ", getName()="
				+ getName() + "]";
	}

}
