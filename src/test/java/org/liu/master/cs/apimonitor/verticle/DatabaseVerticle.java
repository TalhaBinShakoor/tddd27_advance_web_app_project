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

//import io.vertx.ext.jdbc.JDBCClient;


/**
 *
 * @author Mutaz Hussein Kabashi
 * @version 1.0
 *
 */
public class DatabaseVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseVerticle.class);
  
  ServiceBinder serviceBinder;
  MessageConsumer<JsonObject> consumer;
  MessageConsumer<JsonObject> userConsumer;

	//for close/clean up whe should put it here
  DataBaseRepository respository;
  DataBaseRepository userRespository;



	//FIXME properies filed could be part of vertx like global context so we dont have to pass properies to all other verticle ,
  public DatabaseVerticle() {
	super();
}

@Override
  public void start(Promise<Void> promise) throws Exception {
	  try{
	  serviceBinder = new ServiceBinder(vertx);	
	  respository = new WebServiceRepositoryImpl("mysql-test");
	  userRespository = new UserRepositoryImpl("mysql-test");

		  LOGGER.debug("Respository has been created successfully");
	  
	    WebServiceService dataBaseService = WebServiceService.create(vertx , respository);
		  UserService userService =UserService.create(vertx , userRespository);
		  LOGGER.debug("DatabaseService has been created succefully");
	    consumer = serviceBinder
	    	//FIXME address should be in the config file 
	        .setAddress("db_service.webService")
	        .register(WebServiceService.class, dataBaseService);

	    consumer = serviceBinder.setAddress("db_service.userService")
				  .register(UserService.class, userService);
	    LOGGER.debug("WebService_DatabaseService has been registred successfully to  Event Bus");

	        
	    // promise to check if database startup is succefull or not 
         promise.complete();
         LOGGER.info("Database Satarted Successfully");
	  }
	  catch(Exception ex){
		  promise.fail(ex.getCause());
	     LOGGER.error("Could not connect to the  database",ex.getCause());
		  throw new Exception(ex);
	  }
  }

  
  
  @Override
	public void stop() throws Exception {
	    //TODO Databse Cleanup
	  //respository.close();
	}
}
