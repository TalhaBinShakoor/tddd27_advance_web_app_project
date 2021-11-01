package org.liu.master.cs.apimonitor.service;


import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.web.api.OperationResponse;
import io.vertx.ext.web.api.generator.WebApiServiceGen;
import io.vertx.ext.web.client.WebClient;
import org.liu.master.cs.apimonitor.domain.User;

/**
 * Authentication service which is responsible for authentication operations such as
 * AuthenticateUser (for signin)
 * @author Mutaz Hussein Kabashi
 * @version 1.0
 *
 */
@WebApiServiceGen
@ProxyGen
public interface AuthenticationService {


    void AuthenticateUser(User body, OperationRequest context, Handler<AsyncResult<OperationResponse>> resultHandler);

	static AuthenticationService create(Vertx vertx) {

		return new AuthenticationServiceImpl(vertx);
	}

	static AuthenticationService createProxy(Vertx vertx, String address) {
		return new AuthenticationServiceVertxEBProxy(vertx, address);
	}


}
