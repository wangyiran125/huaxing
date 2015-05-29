package org.chinalbs.logistics.vo;

import java.util.Arrays;


public class ChartSingleInfo{
	private String name; //单独某一项的名称
	private ChartValueInfo[] data; //某一项对应的数据
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ChartValueInfo[] getData() {
		return data;
	}
	public void setData(ChartValueInfo[] data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ChartSingleInfo [getName()=" + getName() + ", getData()="
				+ Arrays.toString(getData()) +  "]";
	}
	
	
}
