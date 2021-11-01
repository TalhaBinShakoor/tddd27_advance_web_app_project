package org.liu.master.cs.apimonitor.service;



import io.vertx.codegen.annotations.ProxyGen;
import org.liu.master.cs.apimonitor.domain.WebService;
import org.liu.master.cs.apimonitor.repository.DataBaseRepository;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.web.api.OperationResponse;
import io.vertx.ext.web.api.generator.WebApiServiceGen;



/**
 * A Service which provides/expose {@link WebService} Database operations as Rest-api
 * @author Mutaz Hussein Kabashi
 * @version 1.0
 *
 *
 */
@WebApiServiceGen
@ProxyGen
public interface WebServiceService {

	/**
	 * Getting All {@link WebService}(s) from the dattabase
	 * @param context
	 * @param resultHandler
	 */
	void findAll(OperationRequest context, Handler<AsyncResult<OperationResponse>> resultHandler);

	/**
	 * Insert a new {@link WebService} into a database
	 * @param body
	 * @param context
	 * @param resultHandler
	 */
	void insert(WebService body, OperationRequest context, Handler<AsyncResult<OperationResponse>> resultHandler);

	/**
	 * Update {@link WebService} into the Database
	 * @param body
	 * @param context
	 * @param resultHandler
	 */
	void update(WebService body, OperationRequest context, Handler<AsyncResult<OperationResponse>> resultHandler);

	/**
	 * Delete {@link WebService} from the Database
	 * @param body
	 * @param context
	 * @param resultHandler
	 */
	void deleteWebService(WebService body, OperationRequest context, Handler<AsyncResult<OperationResponse>> resultHandler);


	/**
	 * Getting ${{@link org.liu.master.cs.apimonitor.domain.User}'s @link WebService}(S) from the database
	 * @param context
	 * @param resultHandler
	 */
	public  void findWebServicesByUser(OperationRequest context,  Handler<AsyncResult<OperationResponse>> resultHandler);




	// Factory method to instantiate the implementation
	static WebServiceService create(Vertx vertx, DataBaseRepository repository) {

		return new WebServiceServiceImpl(vertx,repository);
	}

	static WebServiceService createProxy(Vertx vertx, String address) {
		return new WebServiceServiceVertxEBProxy(vertx, address);
	}

}
