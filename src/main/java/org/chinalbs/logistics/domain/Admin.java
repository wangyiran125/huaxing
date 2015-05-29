package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

@Entity
public class Admin extends UserCommon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3990803549697618668L;

	@Override
	public String toString() {
		return "Admin [getUserId()=" + getUserId() + ", getName()=" + getName()
				+ ", getIdCard()=" + getIdCard() + ", getMobile()="
				+ getMobile() + ", getPhone()=" + getPhone() + ", getQq()="
				+ getQq() + ", getEmail()=" + getEmail() + ", getAvatar()="
				+ getAvatar() + ", getIsDeleted()=" + getIsDeleted()
				+ ", getId()=" + getId() +  "]";
	}
	
	

}
