package com.cba.weatherforecast.simulator.core;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Rest service to interact with OpenWeatherAPI and to get the weather updates
 * 
 * @author Srinivas
 *
 */
public class OpenWeatherRestClientService {
	
	/**
	 * logger
	 */
	private static Logger logger = LoggerFactory.getLogger(OpenWeatherRestClientService.class);
	
	private static Client client = null;
	private static WebResource webResource = null;
	
	/**
	 * to rest client to access open weatherAPI
	 * @return Client instance
	 */
	public static Client getClient(){
		if(client == null){
			 client = Client.create();
		}
		 return client;
	}
	
	/**
	 * Builds the URI of the given url in String
	 * 
	 * @param baseURI
	 * @return URL
	 */
	private static URI getBaseURI(String baseURI) {
		 return UriBuilder.fromUri(baseURI).build();
	}
	
	/**
	 * Resource object from the given URI
	 * 
	 * @param baseURI
	 * @return WebResource
	 */
	private static WebResource getWebResource(String baseURI){
		if(webResource == null){
		  webResource = getClient().resource(getBaseURI(baseURI));
		}
		return webResource;
	}
	
	/**
	 * Method requests OpenWeatherApi for the weather updates in JSON
	 * @param baseURI
	 * @param queryParamsMap
	 * @return ClientResponse
	 */
	public static ClientResponse getClientResponse(String baseURI,String path, MultivaluedMap<String, String> queryParamsMap){
		logger.info("baseURI: {}, path:{}, query params map:{}  ",baseURI,path,queryParamsMap);
		ClientResponse response = getWebResource(baseURI).path(path).queryParams(queryParamsMap).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		return response; 
	}
}
