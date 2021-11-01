package org.liu.master.cs.apimonitor.verticle;

import java.util.Properties;

import org.liu.master.cs.apimonitor.service.ApiService;
import org.liu.master.cs.apimonitor.service.AuthenticationService;
import org.liu.master.cs.apimonitor.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
//import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.serviceproxy.ServiceBinder;



/**
 * a vert.x Verticle, which is responsilobe for debploying {@link org.liu.master.cs.apimonitor.service.APIServiceImpl}
 * @author Mutaz Hussein Kabashi
 * @version 1.0
 *
 */
public class ApiVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiVerticle.class);
  
  ServiceBinder serviceBinder;
  MessageConsumer<JsonObject> consumer;
  WebClient webClient ;
  Properties properties;
  
  
   //FIXME properies filed could be part of vertx like global context so we dont have to pass properies to all other 
  public ApiVerticle() {
	super();
	this.properties = properties;
}


@Override
  public void start(Promise<Void> promise) throws Exception {
	  try{
	  serviceBinder = new ServiceBinder(vertx);	
	  webClient = WebClient.create(vertx);
	   LOGGER.debug("Webclient has been created succefully");
	    // Create an instance of WebClient and mount to event bus
	   //FIXME we pass vertx since we need it to create multiple instance of webclient inside APIService
	   // am not sure about that may be am wrong
	    ApiService apiService =ApiService.create(webClient,vertx,properties);
	    LOGGER.debug("ApiService has been created successfully");
	    consumer = serviceBinder
	    	//FIXME address should be in the config file 
	        .setAddress("api_service")
	        .register(ApiService.class, apiService);
	    LOGGER.debug("ApiService has been registred successfully to  Event Bus");

		  AuthenticationService authenticationService =AuthenticationService.create(vertx);
		  LOGGER.debug("authenticationService has been created successfully");
		  consumer = serviceBinder
				  //FIXME address should be in the config file
				  .setAddress("authentication")
				  .register(AuthenticationService.class, authenticationService);
		  LOGGER.debug("authenticationService has been registred successfully to  Event Bus");

	        
	    // promise to check if database startup is succefull or not 
         promise.complete();
         LOGGER.info("API Satarted Successfully");
	  }
	  catch(Exception ex){
		  promise.fail(ex.getCause());
	     LOGGER.error("Could not start the api service");
		  throw new Exception(ex);
	  }
  }

  
  @Override
	public void stop() throws Exception {
		 consumer.unregister();
		 LOGGER.info("Unreagterster from Event Bus completed successfully");

	}
}
