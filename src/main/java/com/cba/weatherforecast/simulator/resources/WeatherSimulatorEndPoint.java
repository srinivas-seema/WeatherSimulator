package com.cba.weatherforecast.simulator.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cba.weatherforecast.simulator.config.ServiceConfiguration;
import com.cba.weatherforecast.simulator.core.OpenMapsWeatherAPIAccessor;


@Path("/")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
/**
 * Restful endpoint to interacts with user for weather API
 * @author srinivass858
 *
 */
public class WeatherSimulatorEndPoint {

	/**
	 * logger
	 */
	private static Logger logger = LoggerFactory.getLogger(WeatherSimulatorEndPoint.class);
	
	private OpenMapsWeatherAPIAccessor openMapsWeatherAPIAccessor;
	
	public WeatherSimulatorEndPoint(ServiceConfiguration serviceConfiguration) {
		super();
		this.openMapsWeatherAPIAccessor = OpenMapsWeatherAPIAccessor.getWeatherServiceInstace();
		this.openMapsWeatherAPIAccessor.setWeatherApiConfiguration(serviceConfiguration.getWeatherApiConfig());
	}

	@GET
	@Path("sample")
	public Response getWeatherSimulatedData(@QueryParam("cnt") int cnt){
		Response response =  this.openMapsWeatherAPIAccessor.getSimulatedData(cnt);
		String responseOutput = (String) response.getEntity();
		logger.info("output in getWeatherSimulatedData endpoint "+responseOutput);
		return response;
	}
	
	@GET
	@Path("city")
	public Response getByCity(@QueryParam("cityId") String cityId){
		Response response =  this.openMapsWeatherAPIAccessor.getByCityId(cityId);
		String responseOutput = (String) response.getEntity();
		logger.info("output in getByCity endpoint "+responseOutput);
		return response;
	}
	
	@GET
	@Path("cities")
	public Response getByCities(@QueryParam("ids") String ids){
		Response response =  this.openMapsWeatherAPIAccessor.getBySeveralCities(ids.split(","));
		String responseOutput = (String) response.getEntity();
		logger.info("output in getByCities endpoint "+responseOutput);
		return response;
	}
	
	@GET
	@Path("zip")
	public Response getByZipCode(@QueryParam("zipcode") String zipcode,@QueryParam("country") String country){
		Response response =  this.openMapsWeatherAPIAccessor.getByZIPCode(zipcode, country);
		String responseOutput = (String) response.getEntity();
		logger.info("output in getByZipCode endpoint "+responseOutput);
		return response;
	}
	
	@GET
	@Path("geo")
	public Response getByGeographicCoordinates(@QueryParam("lat") String lat, @QueryParam("lon") String lon){
		Response response = this.openMapsWeatherAPIAccessor.getByGeographicCoordinates(lat, lon);
		String responseOutput = (String) response.getEntity();
		logger.info("output in getByGeographicCoordinates endpoint "+responseOutput);
		return response;
	}
}
