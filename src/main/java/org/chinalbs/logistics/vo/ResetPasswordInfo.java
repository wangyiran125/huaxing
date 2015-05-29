package org.chinalbs.logistics.vo;

public class ResetPasswordInfo {
	private String token;
	private String username;
	private String email;
	private String password;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "ResetPasswordInfo [token=" + token + ", username=" + username + ", email=" + email + ", password=" + password
				+ "]";
	}
}
