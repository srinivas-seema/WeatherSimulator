package com.cba.weatherforecast.simulator.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Weather API Configuration 
 * 
 * @author Srinivas
 *
 */
public class WeatherApiConfiguration {
	
	private String byCityId;
	private String byGeographicCoordinates;
	private String byZIPcode;
	private String citiesInCycle;
	private String severalCityIds;
	private String baseURI;
	private String unitFormat;
	private String openWeatherAPIKey;

	@JsonCreator
	public WeatherApiConfiguration(@JsonProperty("byCityId") String byCityId, @JsonProperty("byGeographicCoordinates") String byGeographicCoordinates,
			@JsonProperty("byZIPcode") String byZIPcode,@JsonProperty("citiesInCycle") String citiesInCycle, @JsonProperty("severalCityIds") String severalCityIds,
			@JsonProperty("baseURI") String baseURI,@JsonProperty("unitFormat") String unitFormat, @JsonProperty("openWeatherAPIKey") String openWeatherAPIKey) {
		super();
		
		this.byCityId = byCityId;                
		this.byGeographicCoordinates = byGeographicCoordinates; 
		this.byZIPcode = byZIPcode;               
		this.citiesInCycle = citiesInCycle;           
		this.severalCityIds = severalCityIds;          
		this.baseURI = baseURI;                 
		this.unitFormat = unitFormat;              
		this.openWeatherAPIKey = openWeatherAPIKey;       
		
		
	}

	public String getByCityId() {
		return byCityId;
	}

	public void setByCityId(String byCityId) {
		this.byCityId = byCityId;
	}

	public String getByGeographicCoordinates() {
		return byGeographicCoordinates;
	}

	public void setByGeographicCoordinates(String byGeographicCoordinates) {
		this.byGeographicCoordinates = byGeographicCoordinates;
	}

	public String getByZIPcode() {
		return byZIPcode;
	}

	public void setByZIPcode(String byZIPcode) {
		this.byZIPcode = byZIPcode;
	}

	public String getCitiesInCycle() {
		return citiesInCycle;
	}

	public void setCitiesInCycle(String citiesInCycle) {
		this.citiesInCycle = citiesInCycle;
	}

	public String getSeveralCityIds() {
		return severalCityIds;
	}

	public void setSeveralCityIds(String severalCityIds) {
		this.severalCityIds = severalCityIds;
	}

	public String getBaseURI() {
		return baseURI;
	}

	public void setBaseURI(String baseURI) {
		this.baseURI = baseURI;
	}

	public String getUnitFormat() {
		return unitFormat;
	}

	public void setUnitFormat(String unitFormat) {
		this.unitFormat = unitFormat;
	}

	public String getOpenWeatherAPIKey() {
		return openWeatherAPIKey;
	}

	public void setOpenWeatherAPIKey(String openWeatherAPIKey) {
		this.openWeatherAPIKey = openWeatherAPIKey;
	}

}
