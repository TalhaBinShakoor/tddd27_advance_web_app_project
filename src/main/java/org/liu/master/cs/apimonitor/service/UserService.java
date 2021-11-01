package org.liu.master.cs.apimonitor.service;


import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.web.api.OperationResponse;
import io.vertx.ext.web.api.generator.WebApiServiceGen;
import org.liu.master.cs.apimonitor.domain.User;
import org.liu.master.cs.apimonitor.domain.WebService;
import org.liu.master.cs.apimonitor.repository.DataBaseRepository;


/**
 * A Service which provides/expose {@link User} Database operations as Rest-api
 * @author Talha Bin Shakoor
 * @version 1.0
 *
 *
 */
@WebApiServiceGen
@ProxyGen
public interface UserService {

	/**
	 * Getting All {@link User}(s) from the dattabase
	 * @param context
	 * @param resultHandler
	 */
	void findAllUsers(OperationRequest context, Handler<AsyncResult<OperationResponse>> resultHandler);

	/**
	 * Insert a new {@link User} into a database
	 * @param body
	 * @param context
	 * @param resultHandler
	 */
	void insertUser(User body, OperationRequest context, Handler<AsyncResult<OperationResponse>> resultHandler);

	/**
	 * Update {@link User} into the Database
	 * @param body
	 * @param context
	 * @param resultHandler
	 */
	void updateUser(User body, OperationRequest context, Handler<AsyncResult<OperationResponse>> resultHandler);

	/**
	 * Getting ${{@link User}'s data from the database
	 * @param body
	 * @param context
	 * @param resultHandler
	 */
	void findUser(User body, OperationRequest context, Handler<AsyncResult<OperationResponse>> resultHandler);


	// Factory method to instantiate the implementation
	static UserService create(Vertx vertx, DataBaseRepository repository) {

		return new UserServiceImpl(vertx,repository);
	}

	static UserService createProxy(Vertx vertx, String address) {
		return new UserServiceVertxEBProxy(vertx, address);
	}

}
