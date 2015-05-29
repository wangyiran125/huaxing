package org.chinalbs.logistics.session;

public class UserInfo {
	private boolean isLogin;
	private String username;
	private long userId;
    private int role;
    private String capcareToken;

	public UserInfo() {
	}
	
	public UserInfo(boolean isLogin, String username) {
		this.isLogin = isLogin;
		this.username = username;
	}

	public boolean isLogin() {
        return isLogin;
    }

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

	public String getCapcareToken() {
		return capcareToken;
	}

	public void setCapcareToken(String capcareToken) {
		this.capcareToken = capcareToken;
	}
    
    
}