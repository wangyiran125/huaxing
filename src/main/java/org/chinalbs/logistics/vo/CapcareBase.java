package org.chinalbs.logistics.vo;

public class CapcareBase{
	private int ret;
	private String explain = "";
	
	public CapcareBase () {
		
	}
	
	public CapcareBase (int ret) {
		this.ret = ret;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	@Override
	public String toString() {
		return "CapcareBase [ret=" + ret + ", explain=" + explain + "]";
	}
	
	

}
