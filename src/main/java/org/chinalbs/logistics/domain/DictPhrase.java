package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

import org.chinalbs.logistics.common.domain.BaseDict;

@Entity
public class DictPhrase extends BaseDict<Long> {

	@Override
	public String toString() {
		return "DictPhrase [getCode()=" + getCode() + ", getName()="
				+ getName() +  "]";
	}

	
}
