package org.chinalbs.logistics.vo;

import java.util.Arrays;
import java.util.List;


public class ChartInfo{
	private String[] xCategories; //横坐标类别名称
	private List<ChartSingleInfo> series; //报表数据
	public String[] getxCategories() {
		return xCategories;
	}
	public void setxCategories(String[] xCategories) {
		this.xCategories = xCategories;
	}
	public List<ChartSingleInfo> getSeries() {
		return series;
	}
	public void setSeries(List<ChartSingleInfo> series) {
		this.series = series;
	}
	@Override
	public String toString() {
		return "ChartInfo [getxCategories()="
				+ Arrays.toString(getxCategories()) + ", getSeries()="
				+ getSeries() + "]";
	}
	
	
}
