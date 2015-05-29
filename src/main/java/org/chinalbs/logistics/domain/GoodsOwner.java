package org.chinalbs.logistics.domain;

import javax.persistence.Entity;

@Entity
public class GoodsOwner extends UserCommon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 669243663895023486L;

	@Override
	public String toString() {
		return "GoodsOwner [getUserId()=" + getUserId() + ", getName()="
				+ getName() + ", getIdCard()=" + getIdCard() + ", getMobile()="
				+ getMobile() + ", getPhone()=" + getPhone() + ", getQq()="
				+ getQq() + ", getEmail()=" + getEmail() + ", getAvatar()="
				+ getAvatar() + ", getIsDeleted()=" + getIsDeleted()
				+  "]";
	}
	
	
	
}
