package com.cba.weatherforecast.simulator.model;

public class WeatherInfoModel {
	
	private String location;
	private PositionInfoModel positionInfo;
	private String localTime;
	private String weatherConditions;
	private String temperature;
	private String pressure;
	private String humidity;
	
	public WeatherInfoModel(String location, PositionInfoModel positionInfo,
			String localTime, String weatherConditions, String temperature,
			String pressure, String humidity) {
		super();
		this.location = location;
		this.positionInfo = positionInfo;
		this.localTime = localTime;
		this.weatherConditions = weatherConditions;
		this.temperature = temperature;
		this.pressure = pressure;
		this.humidity = humidity;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public PositionInfoModel getPositionInfo() {
		return positionInfo;
	}
	public void setPositionInfo(PositionInfoModel positionInfo) {
		this.positionInfo = positionInfo;
	}
	public String getLocalTime() {
		return localTime;
	}
	public void setLocalTime(String localTime) {
		this.localTime = localTime;
	}
	public String getWeatherConditions() {
		return weatherConditions;
	}
	public void setWeatherConditions(String weatherConditions) {
		this.weatherConditions = weatherConditions;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getPressure() {
		return pressure;
	}
	public void setPressure(String pressure) {
		this.pressure = pressure;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	
	
	@Override
	public String toString() {
		return String.format("%s|%s|%s|%s|%s|%s|%s", this.location,this.positionInfo,this.localTime,this.weatherConditions,this.temperature,this.pressure,this.humidity);
	}

}
