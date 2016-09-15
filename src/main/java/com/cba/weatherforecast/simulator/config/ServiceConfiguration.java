package com.cba.weatherforecast.simulator.config;

import javax.validation.Valid;

import com.yammer.dropwizard.config.Configuration;

/**
 * Configuation class takes the weather Api config parameters from the configuration yml file 
 * 
 * @author Srinivas
 *
 */
public class ServiceConfiguration extends Configuration {
	
		@Valid
		private WeatherApiConfiguration weatherApiConfig;

		public WeatherApiConfiguration getWeatherApiConfig() {
			return weatherApiConfig;
		}

		public void setWeatherApiConfig(WeatherApiConfiguration weatherApiConfig) {
			this.weatherApiConfig = weatherApiConfig;
		}
}
