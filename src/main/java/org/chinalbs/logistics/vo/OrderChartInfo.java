package org.chinalbs.logistics.vo;


public class OrderChartInfo {

	private String goodsOwnerUsername;
	private String goodsOwnerName;
	private String goodsName;
	private double volume;
	private double weight;
	private String departure;
	private String destination;
	// private int departureProvinceCode;
	// private int departureCityCode;
	// private int destinationProvinceCode;
	// private int destinationCityCode;
	private String departureTime;
	private String shippingPrice;
	private String truckOwnerUsername;
	private String truckOwnerName;
	private String licensePlateNumbers;

	public String getGoodsOwnerUsername() {
		return goodsOwnerUsername;
	}

	public void setGoodsOwnerUsername(String goodsOwnerUsername) {
		this.goodsOwnerUsername = goodsOwnerUsername;
	}

	public String getGoodsOwnerName() {
		return goodsOwnerName;
	}

	public void setGoodsOwnerName(String goodsOwnerName) {
		this.goodsOwnerName = goodsOwnerName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	// public int getDepartureProvinceCode() {
	// return departureProvinceCode;
	// }
	// public void setDepartureProvinceCode(int departureProvinceCode) {
	// this.departureProvinceCode = departureProvinceCode;
	// }
	// public int getDepartureCityCode() {
	// return departureCityCode;
	// }
	// public void setDepartureCityCode(int departureCityCode) {
	// this.departureCityCode = departureCityCode;
	// }
	// public int getDestinationProvinceCode() {
	// return destinationProvinceCode;
	// }
	// public void setDestinationProvinceCode(int destinationProvinceCode) {
	// this.destinationProvinceCode = destinationProvinceCode;
	// }
	// public int getDestinationCityCode() {
	// return destinationCityCode;
	// }
	// public void setDestinationCityCode(int destinationCityCode) {
	// this.destinationCityCode = destinationCityCode;
	// }

	public String getDepartureTime() {
		return departureTime;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(String shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public String getTruckOwnerUsername() {
		return truckOwnerUsername;
	}

	public void setTruckOwnerUsername(String truckOwnerUsername) {
		this.truckOwnerUsername = truckOwnerUsername;
	}

	public String getTruckOwnerName() {
		return truckOwnerName;
	}

	public void setTruckOwnerName(String truckOwnerName) {
		this.truckOwnerName = truckOwnerName;
	}

	public String getLicensePlateNumbers() {
		return licensePlateNumbers;
	}

	public void setLicensePlateNumbers(String licensePlateNumbers) {
		this.licensePlateNumbers = licensePlateNumbers;
	}
}
