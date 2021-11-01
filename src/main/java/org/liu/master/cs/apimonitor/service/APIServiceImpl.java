package org.liu.master.cs.apimonitor.service;

import java.util.Properties;

import io.vertx.core.*;
import org.liu.master.cs.apimonitor.domain.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.web.api.OperationResponse;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;


/**
 * An implemenation to {@link ApiService}
 * @author Mutaz Hussein Kabashi
 * @version 1.0
 *
 */
public class APIServiceImpl implements ApiService {


	  private static final Logger LOGGER = LoggerFactory.getLogger(APIServiceImpl.class);


	private WebClient webClient;
	private final Vertx vertx;
	private Properties properties;

	public APIServiceImpl(WebClient webClient,Vertx vertx,Properties properties) {
		//FIXME DAO  factcory
		this.webClient = webClient;
		this.vertx = vertx;
		this.properties = properties;
	}

	@Override
	public void getApiStatus(OperationRequest context, Handler<AsyncResult<OperationResponse>> resultHandler) {
		LOGGER.info("get API web services  which are saved/inserted into the Database ");
		JsonArray apisResultStatus  = new JsonArray();
		WebServiceService webServiceService = WebServiceService.createProxy(vertx, "db_service.webService");
		//FIXME BLOCK
		webServiceService.findWebServicesByUser(context, ar->{
			if (ar.succeeded()) {
				//Close/release web client connection first
				System.out.println("response "+ar.result().getPayload().toJsonArray());
				JsonArray apis = ar.result().getPayload().toJsonArray();
				int apisSize = 0;
				for (Object object : apis) {
					LOGGER.debug("api "+object);
					 WebClient webClient2 = WebClient.create(vertx);
						System.out.println("1");
					//FIXME blocking
					WebService ws = new WebService((JsonObject)object);
					System.out.println("2");
					Promise<Void> promise = Promise.promise();
					webClient2.getAbs(ws.getUrl()).send(ar2 -> {
						if (ar2.succeeded()) {
							HttpResponse<Buffer> response2 = ar2.result();
							LOGGER.info("result from webservice "+ws.getUrl()+"  "+response2.statusCode());
							String result = "{\"name\":\""+ws.getName()+"\",\"url\":\""+ws.getUrl()+"\",\"statusCode\":\""+response2.statusCode()+"\"}";
							LOGGER.debug("result "+result);
							apisResultStatus.add(new JsonObject(result));
							LOGGER.debug("result2 "+result + apisResultStatus);
							webClient2.close();
							promise.complete();
							LOGGER.debug("result3 "+result);
							} else {
							String result = "{\"name\":\""+ws.getName()+"\",\"url\":\""+ws.getUrl()+"\",\"statusCode\":\""+" No Response/Down "+"\"}";
							apisResultStatus.add(new JsonObject(result));
							LOGGER.error("Exception while calling rest api ",ar2.cause());
							promise.fail(ar2.cause());
							}
							});

					promise.future().setHandler(asyncResult -> {
					    if(asyncResult.succeeded() && apisResultStatus.size()==apis.size()) {
					    	System.out.println("apiresultsize "+apisResultStatus.size());
					    	 resultHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(apisResultStatus)));
							 }
						//FIXME same business logic to the previous if (duplicate code)
					    else if(asyncResult.failed() && apisResultStatus.size()==apis.size())  {
							 resultHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(apisResultStatus)));
					    }
					});

				}
				} else {
					LOGGER.error("Exception while getting apis from Datbase ",ar.cause());
					resultHandler.handle(Future.failedFuture(ar.cause()));
				}
				});
		LOGGER.info("insertion completed succefully");
		// FIXME should we return the record or just uccuess message
		
	}

}
