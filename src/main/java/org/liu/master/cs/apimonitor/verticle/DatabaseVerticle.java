package org.liu.master.cs.apimonitor.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ServiceBinder;
import org.liu.master.cs.apimonitor.repository.DataBaseRepository;
import org.liu.master.cs.apimonitor.repository.UserRepositoryImpl;
import org.liu.master.cs.apimonitor.repository.WebServiceRepositoryImpl;
import org.liu.master.cs.apimonitor.service.WebServiceService;
import org.liu.master.cs.apimonitor.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A vert.x verticle which is responsible for deploying Database Services
 * @author Mutaz Hussein Kabashi
 * @version 1.0
 *
 */
public class DatabaseVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseVerticle.class);
  
  ServiceBinder serviceBinder;
  MessageConsumer<JsonObject> consumer;
  //for close/clean up whe should put it here
  DataBaseRepository respository;
  DataBaseRepository userRespository;

  public DatabaseVerticle() {
	super();
}

@Override
  public void start(Promise<Void> promise) throws Exception {
	  try{
	  serviceBinder = new ServiceBinder(vertx);	
	  respository = new WebServiceRepositoryImpl("mysql-example");
	  userRespository = new UserRepositoryImpl("mysql-example");

		  LOGGER.debug("Respository has been created successfully");
	  
	    WebServiceService webService = WebServiceService.create(vertx , respository);
	    LOGGER.debug("WebService Service has been created succefully");
	    consumer = serviceBinder
	    	//FIXME address should be in the config file 
	        .setAddress("db_service.webService")
	        .register(WebServiceService.class, webService);
	    LOGGER.debug("WebService_DatabaseService has been registred successfully to  Event Bus");

		  UserService userService =UserService.create(vertx , userRespository);
		  LOGGER.debug("DatabaseService has been created succefully");
		  consumer = serviceBinder
				  //FIXME address should be in the config file
				  .setAddress("db_service.user")
				  .register(UserService.class, userService);
		  LOGGER.debug("userService_DatabaseService has been registred successfully to  Event Bus");

	        
	    // promise to check if database startup is successful or not
         promise.complete();
         LOGGER.info("Database Satarted Successfully");
	  }
	  catch(Exception ex){
		  promise.fail(ex.getCause());
	     LOGGER.error("Could not connect to the  database",ex.getCause());
		  throw new Exception(ex);
	  }
  }



	/**
	 * This method closes the http server and unregister all services loaded to
	 * Event Bus
	 */
  @Override
	public void stop() throws Exception {
	    //TODO Databse Cleanup
	  //respository.close();
	  //LOGGER.info("Databse Connection Closed");
	  //consumer.unregister();
	  //LOGGER.info("Unreagterster from Event Bus completed successfully");

  }
}
