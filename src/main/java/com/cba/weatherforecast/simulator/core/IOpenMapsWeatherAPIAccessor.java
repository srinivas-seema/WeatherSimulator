package com.cba.weatherforecast.simulator.core;

import javax.ws.rs.core.Response;

/**
 * Interface for the WeatherAPI
 * @author Srinivas
 *
 */
public interface IOpenMapsWeatherAPIAccessor {
	
	public Response getByGeographicCoordinates(String latitude, String longitude);
	
	public Response getByCityId(String cityId);
	
	public Response getByZIPCode(String zipCode,String countryCode);
	
	public Response getBySeveralCities(String  ... cityId);
	
	public Response getSimulatedData(int count);

}
