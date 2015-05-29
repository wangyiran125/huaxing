package org.chinalbs.logistics.vo;


public class OperatingBusInfo{

	private String date;
	private int hour;
	private long totalNumber;
	private double mileage;
	private double avgSpeed;
	private String lineName;
	private long passenger;
	
	public OperatingBusInfo(String date,long totalNumber,double mileage,double avgSpeed,String lineName){
		this.date = date;
		this.totalNumber = totalNumber;
		this.mileage = mileage;
		this.avgSpeed = avgSpeed;
		this.lineName = lineName;
	}
	
	public OperatingBusInfo(int hour,double avgSpeed,double mileage,long totalNumber,long passenger){
		this.hour = hour;
		this.avgSpeed = avgSpeed;
		this.mileage = mileage;
		this.totalNumber = totalNumber;
		this.passenger = passenger;
	}
	
	public OperatingBusInfo(double avgspeed){
		this.avgSpeed = avgspeed;
	}
	
	public OperatingBusInfo(int hour,int totalNumber){
		this.hour = hour;
		this.totalNumber = totalNumber;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(long totalNumber) {
		this.totalNumber = totalNumber;
	}

	public double getMileage() {
		return mileage;
	}

	public void setMileage(double mileage) {
		this.mileage = mileage;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public double getAvgSpeed() {
		return avgSpeed;
	}

	public void setAvgSpeed(double avgSpeed) {
		this.avgSpeed = avgSpeed;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public long getPassenger() {
		return passenger;
	}

	public void setPassenger(long passenger) {
		this.passenger = passenger;
	}

	@Override
	public String toString() {
		return "OperatingBusInfo [getDate()=" + getDate()
				+ ", getTotalNumber()=" + getTotalNumber() + ", getMileage()="
				+ getMileage() + ", getHour()=" + getHour()
				+ ", getAvgSpeed()=" + getAvgSpeed() + ", getLineName()="
				+ getLineName() + ", getPassenger()=" + getPassenger()
				+  "]";
	}
	
	
}
