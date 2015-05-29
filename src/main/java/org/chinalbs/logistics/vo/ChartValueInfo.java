package org.chinalbs.logistics.vo;


public class ChartValueInfo{
	private String name; //单个值的名称
	private double y; //某一值的数据
	public ChartValueInfo(){}
	public ChartValueInfo(String name, long y) {
		this.name = name;
		this.y = y;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "ChartValueInfo [getName()=" + getName() + ", getY()=" + getY()
				+ "]";
	}
	
	
}
