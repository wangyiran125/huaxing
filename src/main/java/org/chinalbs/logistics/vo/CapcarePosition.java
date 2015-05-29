package org.chinalbs.logistics.vo;

import java.util.List;

public class CapcarePosition extends CapcareBase{
	private List<Position> list;
	
	public CapcarePosition() {	
	}
	
	public class Position {
		private String lng;
		private String lat;
		private String deviceSn;
		public Position() {
			
		}
		public String getLng() {
			return lng;
		}
		public void setLng(String lng) {
			this.lng = lng;
		}
		public String getLat() {
			return lat;
		}
		public void setLat(String lat) {
			this.lat = lat;
		}
		public String getDeviceSn() {
			return deviceSn;
		}
		public void setDeviceSn(String deviceSn) {
			this.deviceSn = deviceSn;
		}
		
	}

	public List<Position> getList() {
		return list;
	}

	public void setList(List<Position> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "CapcarePosition [list=" + list + ", getList()=" + getList()
				+ ", getRet()=" + getRet() + ", getExplain()=" + getExplain()+ "]";
	}
	
	
}
