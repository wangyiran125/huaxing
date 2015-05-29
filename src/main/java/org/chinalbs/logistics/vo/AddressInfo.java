package org.chinalbs.logistics.vo;

import org.chinalbs.logistics.domain.Address;

public class AddressInfo extends Address{

	private static final long serialVersionUID = 9115979420700542043L;

	@Override
	public String toString() {
		return "AddressInfo [getUserId()=" + getUserId()
				+ ", getProvinceCode()=" + getProvinceCode()
				+ ", getCityCode()=" + getCityCode() + ", getAddress()="
				+ getAddress() + ", getFlag()=" + getFlag() + ", toString()="
				+ super.toString() + ", getId()=" + getId() + "]";
	}
	
	

}
