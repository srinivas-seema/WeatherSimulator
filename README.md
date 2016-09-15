# WeatherSimulator
A Weather simulation service as a rest API provides the real time weather updates using geological coordinates also generates simulated data

WeatherAPI usage:
1.	git clone https://github.com/srinivas-seema/WeatherSimulator.git
2.	mvn clean package 
3.	copy the generated jar in the target directory, configuration.yml to the desired folder 
Eg: C:\Users\srinivass858\Desktop\Weather Analytics
 
4.	Run the jar with the parameters as “server”,”configuration.yml”  as restful service

eg: C:\Users\srinivass858\Desktop\Weather Analytics>java -jar WeatherSimulatorAPI-0.0.1-SNAPSHOT.jar server configuration.yml
5.	Server will get start make sure ports 7000,7001 are not used for some other processes, if so change the port numbers configured in the configuration.yml to some other ports

Server start trace   with the available endpoints 
WARN  [2016-09-15 13:38:37,674] com.yammer.dropwizard.config.ServerFactory: 
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
!    THIS SERVICE HAS NO HEALTHCHECKS. THIS MEANS YOU WILL NEVER KNOW IF IT    !
!    DIES IN PRODUCTION, WHICH MEANS YOU WILL NEVER KNOW IF YOU'RE LETTING     !
!     YOUR USERS DOWN. YOU SHOULD ADD A HEALTHCHECK FOR EACH DEPENDENCY OF     !
!     YOUR SERVICE WHICH FULLY (BUT LIGHTLY) TESTS YOUR SERVICE'S ABILITY TO   !
!      USE THAT SERVICE. THINK OF IT AS A CONTINUOUS INTEGRATION TEST.         !
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
INFO  [2016-09-15 13:38:37,784] com.yammer.dropwizard.cli.ServerCommand: Starting WeatherReportingService
INFO  [2016-09-15 13:38:37,784] org.eclipse.jetty.server.Server: jetty-8.1.10.v20130312
INFO  [2016-09-15 13:38:37,940] com.sun.jersey.server.impl.application.WebApplicationImpl: Initiating Jersey application, version 'Jersey: 1.17.1 02/28/2013 12:47 PM'
INFO  [2016-09-15 13:38:38,018] com.yammer.dropwizard.config.Environment: The following paths were found for the configured resources:

    GET     /cities (com.cba.weatherforecast.simulator.resources.WeatherSimulatorEndPoint)
    GET     /city (com.cba.weatherforecast.simulator.resources.WeatherSimulatorEndPoint)
    GET     /geo (com.cba.weatherforecast.simulator.resources.WeatherSimulatorEndPoint)
    GET     /sample (com.cba.weatherforecast.simulator.resources.WeatherSimulatorEndPoint)

INFO  [2016-09-15 13:38:38,018] com.yammer.dropwizard.config.Environment: tasks = 

    POST    /tasks/gc (com.yammer.dropwizard.tasks.GarbageCollectionTask)


#Example for sample simulation with the input as the number of outputs expected


 

# Weather updates for the provided city Ids, please refer src/main/resources/city.list.json for the city Ids mapping to the specific city’s
 





#Weather updates for the specific city

 

#Weather updates according to the given zipcode, country code 
 
#weather updates for the specific latitude, longitude

 

