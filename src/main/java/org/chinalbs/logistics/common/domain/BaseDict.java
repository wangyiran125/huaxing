package org.chinalbs.logistics.common.domain;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseDict<T> {

	@Id
	private T code;
	private String name;
	public T getCode() {
		return code;
	}
	public void setCode(T code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
