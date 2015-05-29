package org.chinalbs.logistics.vo;

public class CapcareToken extends CapcareBase{
	private String userToken;

	public CapcareToken () {
		
	}
	
	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	@Override
	public String toString() {
		return "CapcareToken [getUserToken()=" + getUserToken() + ", getRet()="
				+ getRet() + ", getExplain()=" + getExplain()  + "]";
	}
	
	

}
