package org.chinalbs.logistics.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
/**
 * Spring Pageable的简单实现，只支持起始位置，和最大返回结果。
 * @author Jason
 *
 */
public class SimplePageable implements Pageable {

	private int from; 
	private int max;

	public SimplePageable(int from, int max) {
		this.from = from;
		this.max = max;
	}
	
	@Override
	public int getPageNumber() {
		return 0;
	}

	@Override
	public int getPageSize() {
		return max;
	}

	@Override
	public int getOffset() {
		return from;
	}

	@Override
	public Sort getSort() {
		return null;
	}

	@Override
	public Pageable next() {
		return null;
	}

	@Override
	public Pageable previousOrFirst() {
		return null;
	}

	@Override
	public Pageable first() {
		return null;
	}

	@Override
	public boolean hasPrevious() {
		return false;
	}

}
