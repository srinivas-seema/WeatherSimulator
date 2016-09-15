package com.cba.weatherforecast.simulator.model;

public class PositionInfoModel {
	
	private String lattitude;
	private String longitude;
	private String elevation;
	
	public PositionInfoModel(String lattitude, String longitude, String elevation) {
		super();
		this.lattitude = lattitude;
		this.longitude = longitude;
		this.elevation = elevation;
	}
	
	public String getLattitude() {
		return lattitude;
	}
	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getElevation() {
		return elevation;
	}
	public void setElevation(String elevation) {
		this.elevation = elevation;
	}
	
	@Override
	public String toString() {
		return String.format("%s,%s,%s", lattitude,longitude,elevation);
	}

}
