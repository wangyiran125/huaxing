package org.chinalbs.logistics.vo;

public class PasswordInfo {
	private String oldPassword;
	private String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "PasswordInfo [getOldPassword()=" + getOldPassword()
				+ ", getNewPassword()=" + getNewPassword() + "]";
	}
	
	

}
