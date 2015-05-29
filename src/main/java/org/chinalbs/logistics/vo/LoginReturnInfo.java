package org.chinalbs.logistics.vo;
import org.chinalbs.logistics.domain.LogisticsUser;

public class LoginReturnInfo extends LogisticsUser {

	private static final long serialVersionUID = 2367966424745208687L;
	private String capcareToken;
	private String logisticsToken;
	private UserInfo userInfo;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public LoginReturnInfo() {
		
	}

	public String getCapcareToken() {
		return capcareToken;
	}

	public void setCapcareToken(String capcareToken) {
		this.capcareToken = capcareToken;
	}

	public String getLogisticsToken() {
		return logisticsToken;
	}

	public void setLogisticsToken(String logisticsToken) {
		this.logisticsToken = logisticsToken;
	}

	@Override
	public String toString() {
		return "LoginReturnInfo [getCapcareToken()=" + getCapcareToken()
				+ ", getLogisticsToken()=" + getLogisticsToken()
				+ ", getUsername()=" + getUsername() + ", getPassword()="
				+ getPassword() + ", getRegisterTime()=" + getRegisterTime()
				+ ", getLastLoginTime()=" + getLastLoginTime()
				+ ", getIsDeleted()=" + getIsDeleted() + ", getRole()="
				+ getRole() + ", getId()=" + getId() + "]";
	}
	
	

}
