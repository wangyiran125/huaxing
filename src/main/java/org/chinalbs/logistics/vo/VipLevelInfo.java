package org.chinalbs.logistics.vo;

public class VipLevelInfo {

	private int level;
	private String name;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "VipLevelInfo [level=" + level + ", name=" + name
				+ ", getLevel()=" + getLevel() + ", getName()=" + getName()
				+  "]";
	}
	
	

}
