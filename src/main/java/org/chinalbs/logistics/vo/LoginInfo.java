package org.chinalbs.logistics.vo;

public class LoginInfo {
	private String username;
	private String password;
	private String vcode;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	@Override
	public String toString() {
		return "LoginInfo [username=" + username + ", password=" + password
				+ ",vcode="+vcode+"]";
	}

	
	
}
