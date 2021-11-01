package org.liu.master.cs.apimonitor.verticle;

import io.vertx.core.*;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ServiceBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Vert.x verticle which is responsible for starting and deploying application's verticles
 * @author Mutaz Hussein Kabashi
 * @version 1.0
 *
 */
public class MainVerticle extends AbstractVerticle {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);
    
    //FIXME properies filed could be part of vertx like global context so we dont have to pass properies to all other 
    // verticle
     static Properties properties; 
	HttpServer server;
	ServiceBinder serviceBinder;
	MessageConsumer<JsonObject> consumer;

   //Blocking method but we need it to make sure the config file is existed
   // https://www.bookstack.cn/read/guide-for-java-devs/spilt.4.spilt.3.guide-for-java-devs
	public void start(Promise<Void> promise) throws Exception {
		Promise<String> dbVerticleDeployment = Promise.promise();
        Promise<String> httpVerticleDeployment = Promise.promise();
		Promise<String> apiVerticleDeployment = Promise.promise();

		Verticle dbVerticle = new DatabaseVerticle();
		Verticle httpVerticle = new HttpServerVerticle();
		Verticle apiVerticle = new ApiVerticle();

		 vertx.deployVerticle(dbVerticle,dbVerticleDeployment);
		dbVerticleDeployment.future().compose(id->
        {
            vertx.deployVerticle(httpVerticle,httpVerticleDeployment);
             return httpVerticleDeployment.future().compose(id2->
		{
			vertx.deployVerticle(apiVerticle,apiVerticleDeployment);
			return apiVerticleDeployment.future();
		});
		})
	    .onSuccess((result)->
        {
            LOGGER.info("Application Statred Successfully");
            promise.complete();
        })
		.onFailure((ex)->{
		    LOGGER.error("An Exception Occurred while starting the Application ");
            promise.fail(ex.getCause());
		});

	  }

	/**
	 * This method closes the http server and unregister all services loaded to
	 * Event Bus
	 */
	@Override
	public void stop() {
	 //TODO undeploy Deplyed Vertciles
	}


}
