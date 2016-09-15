package com.cba.weatherforecast.simulator.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cba.weatherforecast.simulator.config.ServiceConfiguration;
import com.cba.weatherforecast.simulator.resources.WeatherSimulatorEndPoint;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

/**
 * Service class helps to start the service 
 * 
 * @author Srinivas Seema
 *
 */
public class WeatherReportingService extends Service<ServiceConfiguration> {

	private static final String WEATHER_REPORTING_SERIVICE = "WeatherReportingService";
	
	/**
	 * logger
	 */
	private static Logger logger = LoggerFactory.getLogger(WeatherReportingService.class);
	
	
	public static void main(String[] args) throws Exception {
		new WeatherReportingService().run(args);
	}

	@Override
	public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
		
		logger.info("Weather Report API service is initializing {} ", WEATHER_REPORTING_SERIVICE);
		bootstrap.setName(WEATHER_REPORTING_SERIVICE);
	}

	/**
	 * configure the resources 
	 */
	@Override
	public void run(ServiceConfiguration conf, Environment env)
			throws Exception {
		
		env.addResource(new WeatherSimulatorEndPoint(conf));
	}

}
