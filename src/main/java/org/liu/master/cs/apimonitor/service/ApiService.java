package org.liu.master.cs.apimonitor.service;


import java.util.Properties;



import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.web.api.OperationResponse;
import io.vertx.ext.web.api.generator.WebApiServiceGen;
import io.vertx.ext.web.client.WebClient;


/**
 * A service which is resoposible for checking/retriving the Rest-apis status
 * by retriving all the api from the database and make GET rest-api call to each
 * webserice to check it;s ststus (UP,200 or Down)
 * @author Mutaz Hussein Kabashi
 * @version 1.0
 */
@WebApiServiceGen
public interface ApiService {
	
	void getApiStatus(/*User body,*/ OperationRequest context, Handler<AsyncResult<OperationResponse>> resultHandler);
	
	// Factory method to instantiate the implementation
	  static ApiService create(WebClient webClient, Vertx vertx, Properties properties) {
	    return new APIServiceImpl(webClient,vertx,properties);
	  }

}
