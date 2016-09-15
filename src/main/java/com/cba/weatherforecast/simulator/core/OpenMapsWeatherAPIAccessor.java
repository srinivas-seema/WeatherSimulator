package com.cba.weatherforecast.simulator.core;

import java.text.DecimalFormat;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cba.weatherforecast.simulator.config.WeatherApiConfiguration;
import com.cba.weatherforecast.simulator.model.PositionInfoModel;
import com.cba.weatherforecast.simulator.model.WeatherInfoModel;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Service class provides access methods to the rest endpoints
 * 
 * @author Srinivas
 *
 */
public class OpenMapsWeatherAPIAccessor implements IOpenMapsWeatherAPIAccessor {
	
	/**
	 * logger
	 */
	private static Logger logger = LoggerFactory.getLogger(OpenMapsWeatherAPIAccessor.class);
	
	private static OpenMapsWeatherAPIAccessor weatherServiceInstance = null;
	
	private static final String FIND_PATH = "find";
	private static final String WEATHER_PATH = "weather";
	private static final String GROUP_PATH = "group";
	
	private WeatherApiConfiguration weatherApiConfiguration;
	
	public void setWeatherApiConfiguration(WeatherApiConfiguration weatherApiConfiguration) {
		this.weatherApiConfiguration = weatherApiConfiguration;
	}

	/**
	 * returns the object to invoke the service level methods
	 * @return instance of OpenMapsWeatherAPIAccessor
	 */
	public static OpenMapsWeatherAPIAccessor getWeatherServiceInstace(){
		if(weatherServiceInstance == null){
			weatherServiceInstance =  new OpenMapsWeatherAPIAccessor();
		}
		return weatherServiceInstance;
	}
	
	/**
	 * This method will build the weatherInfoModel in order to obtain the required details from the available source , openWeatherApi
	 * @param weatherObjectString
	 * @return object of WeatherInfoModel
	 */
	private WeatherInfoModel buildWeatherModel(String weatherObjectString){
		
		String lattitude = null;
		String longitude = null;
		String location = null;
		String temperature = null;
		String humidity = null;
		String pressure = null;
		String localTime = null;
		String seaLevel = null;
		String weatherCondition = null;
		JSONObject weatherObject = null;
		
		try {
			weatherObject = (JSONObject) new JSONParser().parse(weatherObjectString);
		} catch (ParseException e) {
			logger.error("error while trying to parse the json form of openweather data ",e);
		}
		
		if(weatherObject.containsKey("coord")){
		JSONObject coordsObject1 = (JSONObject) weatherObject.get("coord");
		logger.info("coordinates in the json form "+coordsObject1.toJSONString());
		}

		
		//get coordinates
		if (weatherObject.get("coord") instanceof JSONObject) {
			JSONObject coordsObject = (JSONObject) weatherObject.get("coord");
			if(coordsObject.containsKey("lat")){
				 lattitude = coordsObject.get("lat")!=null ? coordsObject.get("lat").toString() : null;
			}
			if(coordsObject.containsKey("lon")){
				 longitude = coordsObject.get("lon")!=null ? coordsObject.get("lon").toString() : null;
			}
		} 
		
		if (weatherObject.get("weather") instanceof JSONArray) {
			JSONArray weatherArr = (JSONArray) weatherObject.get("weather");
			JSONObject weatherConditionsObj = (JSONObject) weatherArr.get(0);
			weatherCondition = weatherConditionsObj.get("main")!=null ? weatherConditionsObj.get("main").toString() : "NA";
		}else{
			weatherCondition = "NA";
		}
		
		if (weatherObject.get("main") instanceof JSONObject) {
			JSONObject mainObject = (JSONObject) weatherObject.get("main");
			if(mainObject.containsKey("temp")){
				 temperature = mainObject.get("temp")!=null ? mainObject.get("temp").toString() : "NA";
				 if(!temperature.equals("NA")){
					 if(Double.parseDouble(temperature) > 0){
						 temperature = "+"+temperature;
					 }
				 }
			}else{
				temperature = "NA";
			}
			if(mainObject.containsKey("humidity")){
				humidity = mainObject.get("humidity")!=null ? mainObject.get("humidity").toString() : "NA";
			}else{
				humidity = "NA";
			}
			if(mainObject.containsKey("pressure")){
				pressure = mainObject.get("pressure")!=null ? mainObject.get("pressure").toString() : "NA";
			}else{
				pressure = "NA";
			}
			if(mainObject.containsKey("sea_level")){
				seaLevel = mainObject.get("sea_level")!=null ? mainObject.get("sea_level").toString() : "NA";
			}else{
				seaLevel = "NA";
			}
		}
		

		if(weatherObject.containsKey("dt")){
			String localtimeMS = weatherObject.get("dt")!=null ? weatherObject.get("dt").toString() : null;
			DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
			localTime = formatter.print(Long.valueOf(localtimeMS));
		}
		
		if(weatherObject.containsKey("name")){
			location = weatherObject.get("name")!=null ? weatherObject.get("name").toString() : "NA";
		}else{
			location = "NA";
		}
		
		//constructing the positionInfo Model
		PositionInfoModel positionInfo = new PositionInfoModel(lattitude, longitude, seaLevel);
		//constructing the WeatherInfoModel object
		WeatherInfoModel weatherInfoModel = new WeatherInfoModel(location, positionInfo, localTime, weatherCondition, temperature, pressure, humidity);
		return weatherInfoModel;
	}
	
	/**
	 * Method takes parameters latitude,longitude and gets the weather updates of the location
	 * @param latitude
	 * @param longitude
	 * @return Response
	 */
	@Override
	public Response getByGeographicCoordinates(String latitude, String longitude) {
		String baseURI = weatherApiConfiguration.getBaseURI();
		//build queryparamsMap
		String geoCooradinatesParams = weatherApiConfiguration.getByGeographicCoordinates();
		String[] coordsArrKeys = geoCooradinatesParams.split("&");
		String lattitudeKey = coordsArrKeys[0];
		String longitudeKey = coordsArrKeys[1];
		
		MultivaluedMap<String, String> queryParamsMap = getDefaultQueryMap();
		queryParamsMap.add(lattitudeKey, latitude);
		queryParamsMap.add(longitudeKey, longitude);
		
		ClientResponse clientResponse = OpenWeatherRestClientService.getClientResponse(baseURI,WEATHER_PATH,queryParamsMap);
		String weatherData = clientResponse.getEntity(String.class);
		
		logger.info("weather data retrieved from the open weather API for the coordinates "+weatherData);
		
		//build required weather model from the JSON Object
		WeatherInfoModel weatherInfo = buildWeatherModel(weatherData);
		
		int statusCode;
		if((statusCode = getStatusCodeFromOpenWeatherApi(weatherData))!=200){
			return Response.status(statusCode).build();
		}
		
		return Response.ok(weatherInfo.toString()).build();
	}
	
	/**
	 * Method takes the cityId as parameter and gets the weather updates of the city 
	 * @param cityId
	 * @return Response
	 */
	@Override
	public Response getByCityId(String cityId) {

		String baseURI = weatherApiConfiguration.getBaseURI();
		//build queryparamsMap
		String cityIdKey = weatherApiConfiguration.getByCityId();
		
		MultivaluedMap<String, String> queryParamsMap = getDefaultQueryMap();
		queryParamsMap.add(cityIdKey, cityId);
		logger.info("query params form object "+queryParamsMap);
		
		ClientResponse clientResponse = OpenWeatherRestClientService.getClientResponse(baseURI,WEATHER_PATH,queryParamsMap);
		String weatherData = clientResponse.getEntity(String.class);
		
		logger.info("weather data retrieved from the open weather API for the given city: "+weatherData);
		
		//build required weather model from the JSON Object
		WeatherInfoModel weatherInfo = buildWeatherModel(weatherData);
		
		int statusCode;
		if((statusCode = getStatusCodeFromOpenWeatherApi(weatherData))!=200){
			return Response.status(statusCode).build();
		}
		
		return Response.ok(weatherInfo.toString()).build();
	}

	/**
	 * Method takes the zipcode and country code and gets the weather updates for that zipcode, country
	 * @param zipCode
	 * @param countryCode
	 * @return Response
	 */
	@Override
	public Response getByZIPCode(String zipCode,String country) {
		String baseURI = weatherApiConfiguration.getBaseURI();
		//build queryparamsMap
		String zipKey = weatherApiConfiguration.getByZIPcode();
		
		MultivaluedMap<String, String> queryParamsMap = getDefaultQueryMap();
		
		//unit format metric is (for degrees of celsius) removing as this queryparam was not supporting for the openWeatherApi 
		String unitFormat = weatherApiConfiguration.getUnitFormat().split("=")[0];
		queryParamsMap.remove(unitFormat);
		String zipValue = String.format("%s,%s", zipCode,country);
		queryParamsMap.add(zipKey, zipValue);
		
		ClientResponse clientResponse = OpenWeatherRestClientService.getClientResponse(baseURI,WEATHER_PATH,queryParamsMap);
		String weatherData = clientResponse.getEntity(String.class);
		
		logger.info("weather data retrieved from the open weather API for the given zipcode: "+weatherData);
		
		//build required weather model from the JSON Object
		WeatherInfoModel weatherInfo = buildWeatherModel(weatherData);
		
		int statusCode;
		if((statusCode = getStatusCodeFromOpenWeatherApi(weatherData))!=200){
			return Response.status(statusCode).build();
		}
		
		return Response.ok(weatherInfo.toString()).build();
	}

	/**
	 * Method returns the weather updates for the list of the given city Ids
	 * @param array of city Ids
	 * @return Response
	 */
	@Override
	public Response getBySeveralCities(String  ... cityId) {
		String baseURI = weatherApiConfiguration.getBaseURI();
		String cityIdKey = weatherApiConfiguration.getByCityId();
		String cityIds = String.join(",", cityId);
		MultivaluedMap<String, String> queryParamsMap = getDefaultQueryMap();
		queryParamsMap.add(cityIdKey, cityIds);
		
		ClientResponse clientResponse = OpenWeatherRestClientService.getClientResponse(baseURI,GROUP_PATH,queryParamsMap);
		String weatherData = clientResponse.getEntity(String.class);
		
		logger.info("weather data retrieved from the open weather API for the several cities "+weatherData);
		StringBuilder weatherObjectsBuilder = new StringBuilder();
		try {
			JSONObject weatherObjectList = (JSONObject) new JSONParser().parse(weatherData);
			if(weatherObjectList.containsKey("list")){
				if(weatherObjectList.get("list") instanceof JSONArray){
					JSONArray weatherObjectJsonArr = (JSONArray)weatherObjectList.get("list");
					for(int i=0;i<weatherObjectJsonArr.size();i++){
						WeatherInfoModel weatherInfo = buildWeatherModel(weatherObjectJsonArr.get(i).toString());
						weatherObjectsBuilder.append(weatherInfo.toString());
						weatherObjectsBuilder.append(System.lineSeparator());
					}
				}
			}
		} catch (ParseException e) {
			logger.error("error while trying to parse the json form of openweather data ",e);
		}
		logger.info("simulated data "+weatherObjectsBuilder.toString());
		
		int statusCode;
		if((statusCode = getStatusCodeFromOpenWeatherApi(weatherData))!=200){
			return Response.status(statusCode).build();
		}
		
		return Response.ok(weatherObjectsBuilder.toString()).build();
	}
	
	/**
	 * generates the simulated weather data for the randomly calculated geographical coordinates
	 * @param count expected number of result set
	 * @return the list of the weather data objects in the pipe separated format
	 */
	@Override
	public Response getSimulatedData(int count){
		String baseURI = weatherApiConfiguration.getBaseURI();
		//build queryparamsMap
		String geoCooradinatesParams = weatherApiConfiguration.getCitiesInCycle();
		String[] coordsArrKeys = geoCooradinatesParams.split("&");
		
		String lattitudeKey = coordsArrKeys[0];
		String longitudeKey = coordsArrKeys[1];
		String cntKey = coordsArrKeys[2];
		
		MultivaluedMap<String, String> queryParamsMap = getDefaultQueryMap();
		queryParamsMap.add(lattitudeKey, getRandomLattitude());
		queryParamsMap.add(longitudeKey, getRandomLongitude());
		queryParamsMap.add(cntKey, String.valueOf(count));
		
		ClientResponse clientResponse = OpenWeatherRestClientService.getClientResponse(baseURI,FIND_PATH,queryParamsMap);
		String weatherData = clientResponse.getEntity(String.class);
		
		logger.info("weather data retrieved from the open weather API for the simulated data "+weatherData);
		StringBuilder weatherObjectsBuilder = new StringBuilder();
		try {
			JSONObject weatherObjectList = (JSONObject) new JSONParser().parse(weatherData);
			if(weatherObjectList.containsKey("list")){
				if(weatherObjectList.get("list") instanceof JSONArray){
					JSONArray weatherObjectJsonArr = (JSONArray)weatherObjectList.get("list");
					for(int i=0;i<weatherObjectJsonArr.size();i++){
						WeatherInfoModel weatherInfo = buildWeatherModel(weatherObjectJsonArr.get(i).toString());
						weatherObjectsBuilder.append(weatherInfo.toString());
						weatherObjectsBuilder.append(System.lineSeparator());
					}
				}
			}
		} catch (ParseException e) {
			logger.error("error while trying to parse the json form of openweather data ",e);
		}
		logger.info("simulated data "+weatherObjectsBuilder.toString());
		
		int statusCode;
		if((statusCode = getStatusCodeFromOpenWeatherApi(weatherData))!=200){
			return Response.status(statusCode).build();
		}
		
		return Response.ok(weatherObjectsBuilder.toString()).build();
	}
	
	/**
	 * returns the queryMap with the commonly required parameters 
	 * @return
	 */
	private MultivaluedMap<String, String> getDefaultQueryMap(){
		
		MultivaluedMap<String, String> queryParamsMap = new MultivaluedMapImpl();
		
		String unitFormat = weatherApiConfiguration.getUnitFormat();
		String[] unitFormatKeyValue = unitFormat.split("=");
		String unitFormatKey = unitFormatKeyValue[0];
		String unitFormatValue = unitFormatKeyValue[1];
		
		String weatherApiKeyConf = weatherApiConfiguration.getOpenWeatherAPIKey();
		String[] weatherApiKeyVal = weatherApiKeyConf.split("=");
		String weatherApiKey = weatherApiKeyVal[0];
		String weatherApiVal = weatherApiKeyVal[1];
		
		queryParamsMap.add(unitFormatKey, unitFormatValue);
		queryParamsMap.add(weatherApiKey, weatherApiVal);
		
		return queryParamsMap;
	}
	
	/**
	 * generates the random latitude position
	 * @return latitude position
	 */
	private String getRandomLattitude(){
		double minLat = -90.00;
	    double maxLat = 90.00;      
	    double latitude = minLat + (double)(Math.random() * ((maxLat - minLat) + 1));
	    DecimalFormat df = new DecimalFormat("#.#####"); 
	    return df.format(latitude);
	}
	
	/**
	 * generates the random longitude position
	 * @return longitude position 
	 */
	private String getRandomLongitude(){
		double minLon = -180.00;
	    double maxLon = 180.00;     
	    double longitude = minLon + (double)(Math.random() * ((maxLon - minLon) + 1));
	    DecimalFormat df = new DecimalFormat("#.#####");  
	    return df.format(longitude);
	}
	
	/**
	 * Method is responsible to retrieve the status code from the openweatherApi response
	 * @param weatherOutput
	 * @return
	 */
	private int getStatusCodeFromOpenWeatherApi(String weatherOutput){
		int statusCode = 200;
		try {
			JSONObject weatherObject = (JSONObject) new JSONParser().parse(weatherOutput);
			if(weatherObject.containsKey("cod")){
				 statusCode = Integer.parseInt(weatherObject.get("cod").toString());
			}
		} catch (ParseException e) {
			logger.error("error while trying to parse the json form of openweather data ",e);
		}
		return statusCode;
}
}
