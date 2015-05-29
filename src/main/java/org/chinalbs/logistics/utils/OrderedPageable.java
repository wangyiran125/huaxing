package org.chinalbs.logistics.utils;

import org.springframework.data.domain.Sort;

public class OrderedPageable extends SimplePageable {

    private	Sort sort ;
      
	public OrderedPageable(int from, int max, Sort sort)
	{
		super(from, max);
		this.sort = sort;
	}
	
	@Override
	public Sort getSort() {
		return sort;
	}
	
	
}
