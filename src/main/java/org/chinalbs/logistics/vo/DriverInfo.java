package org.chinalbs.logistics.vo;

import org.chinalbs.logistics.domain.Driver;
import org.chinalbs.logistics.domain.LogisticsUser;

public class DriverInfo {
	private LogisticsUser logisticsUser;
	private Driver driver;
	private String licensePlateNumber;
	
	public DriverInfo() {
		
	}
	
	public DriverInfo(LogisticsUser logisticsUser, Driver driver) {
		this.logisticsUser = logisticsUser;
		this.driver = driver;
	}
	
	public DriverInfo(LogisticsUser logisticsUser, Driver driver, String licensePlateNumber) {
		this.logisticsUser = logisticsUser;
		this.driver = driver;
		this.licensePlateNumber = licensePlateNumber;
	}
	
	public LogisticsUser getLogisticsUser() {
		return logisticsUser;
	}
	public void setLogisticsUser(LogisticsUser logisticsUser) {
		this.logisticsUser = logisticsUser;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}

	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}

	@Override
	public String toString() {
		return "DriverInfo [getLogisticsUser()=" + getLogisticsUser()
				+ ", getDriver()=" + getDriver() + ", getLicensePlateNumber()="
				+ getLicensePlateNumber() + "]";
	}
	
	

}
