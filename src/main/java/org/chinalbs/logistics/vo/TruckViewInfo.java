package org.chinalbs.logistics.vo;

import org.chinalbs.logistics.domain.Truck;
import org.chinalbs.logistics.domain.TruckPlan;

public class TruckViewInfo {

	private Truck truck;
	private TruckPlan truckPlan;
    private String truckLabel;
    private int vipLevel;
	public TruckViewInfo(){
		
	}
	public Truck getTruck() {
		return truck;
	}
	public void setTruck(Truck truck) {
		this.truck = truck;
	}
	public TruckPlan getTruckPlan() {
		return truckPlan;
	}
	public void setTruckPlan(TruckPlan truckPlan) {
		this.truckPlan = truckPlan;
	}

    public String getTruckLabel() {
        return truckLabel;
    }

    public void setTruckLabel(String truckLabel) {
        this.truckLabel = truckLabel;
    }

    public int getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}
	@Override
	public String toString() {
		return "TruckViewInfo [getTruck()=" + getTruck() + ", getTruckPlan()="
				+ getTruckPlan() + ", getVipLevel()=" + getVipLevel() +  "]";
	}
	
	
}
