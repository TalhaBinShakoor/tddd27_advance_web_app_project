package org.liu.master.cs.apimonitor.service;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.web.api.OperationResponse;
import org.liu.master.cs.apimonitor.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implemenation to {@link AuthenticationService}
 * @author Mutaz Hussein Kabashi
 * @version 1.0
 *
 */
public class AuthenticationServiceImpl implements AuthenticationService {


	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	private UserService userService;
	private final Vertx vertx;
	//private JWTAuth jwtAuth;


	public AuthenticationServiceImpl(Vertx vertx) {
		this.vertx = vertx;
		userService = UserService.createProxy(vertx, "db_service.user");

	}


	@Override
	public void AuthenticateUser(User body, OperationRequest context, Handler<AsyncResult<OperationResponse>> resultHandler) {
		userService.findUser(body,context, ar -> {
			LOGGER.debug("find user by email");
			if(ar.succeeded()){
				String userPasswordInDb =ar.result().getPayload().toJsonObject().getString("password");
				Integer userId =ar.result().getPayload().toJsonObject().getInteger("id");

				if(body.getPassword().equals(userPasswordInDb)){
					JsonObject token = new JsonObject();
					//We need user-id to use it with hibernate
					token.put("token",body.getEmail() + "-" + userId);
					token.put("firstName",ar.result().getPayload().toJsonObject().getString("firstName"));
					token.put("lastName",ar.result().getPayload().toJsonObject().getString("lastName"));

					resultHandler.handle(Future.succeededFuture( OperationResponse.completedWithJson(token)));
				}
				else{
					JsonObject errorObject = new JsonObject()
							.put("code", 401);
					resultHandler.handle(Future.failedFuture("401"));
				}
			}
			else {
				LOGGER.error(ar.cause().getMessage(),ar.cause());

				resultHandler.handle(Future.failedFuture(ar.cause().getMessage()));
			}
		});

	}



}
