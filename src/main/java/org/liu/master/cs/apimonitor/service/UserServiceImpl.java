package org.liu.master.cs.apimonitor.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.web.api.OperationResponse;
import org.liu.master.cs.apimonitor.domain.User;
import org.liu.master.cs.apimonitor.domain.WebService;
import org.liu.master.cs.apimonitor.repository.DataBaseRepository;
import org.liu.master.cs.apimonitor.repository.UserRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * An implemenation to {@link UserService}
 * @author Talha Bin Shakoor
 * @version 1.0
 *
 */
public class UserServiceImpl implements UserService {

	private Vertx vertx;

	  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


	private DataBaseRepository repository;

	public UserServiceImpl(Vertx vertx, DataBaseRepository repository) {
		this.vertx = vertx;
		this.repository = repository;
	}

	@Override
	public  void findAllUsers(OperationRequest context,
						 Handler<AsyncResult<OperationResponse>> resultHandler) {
		//FIXME Block IO code should be replaced by NON Blocking IO
		LOGGER.info("retrive all webservices from the database ");
		vertx.executeBlocking(p->{
            try {
                p.complete(repository.findAll());

            }
            catch (Exception ex){
                p.fail(ex);
                LOGGER.error("retrive all users from the database failed",ex.getCause());
                printStackTrace(ex);

            }

            }, ar->{
            if(ar.succeeded()) {
                LOGGER.info("retrive all users from the database completed successfully");
                resultHandler.handle(Future.succeededFuture(convertResultListToJson(ar.result())));

            }
            else{
                LOGGER.error("Exception Ocuured while trying to get users List from database ",ar.cause());

            }
        });


	}

	@Override
	public  void insertUser(User body, OperationRequest context,
						Handler<AsyncResult<OperationResponse>> resultHandler) {
		//FIXME Block IO code should be replaced by NON Blocking IO
		LOGGER.info("insert a record into user table with the following user email "+body.getEmail());
		vertx.executeBlocking(p->{
			try {
				repository.insert(body);
				p.complete();

			}
			catch (Exception ex){
				p.fail(ex);
				LOGGER.error("insertion  failed",ex.getCause());
				printStackTrace(ex);

			}

		}, ar->{
			if(ar.succeeded()) {
				LOGGER.info("insertion completed succefully");
				resultHandler.handle(Future.succeededFuture(OperationResponse.completedWithPlainText(Buffer.buffer().appendString("201"))));

			}
			else if(ar.failed() && ar.cause().getMessage().contains("Duplicate entry")){
				LOGGER.error("Exception Ocuured while trying to insert a Webservice into database ",ar.cause());
				resultHandler.handle(Future.succeededFuture(OperationResponse.completedWithPlainText(Buffer.buffer("Email is already Exist"))));
			}
			else{
				LOGGER.error("Exception Ocuured while trying to insert a Webservice into database ",ar.cause());
				resultHandler.handle(Future.failedFuture(ar.cause().getMessage()));
			}
		});


	}

	@Override
	public  void updateUser(User body,OperationRequest context,
						Handler<AsyncResult<OperationResponse>> resultHandler) {
		//FIXME Block IO code should be replaced by NON Blocking IO
		LOGGER.info("update a webservice record  with the following user email "+body.getEmail());
		vertx.executeBlocking(p->{
			try {
				repository.update(body);
				p.complete();

			}
			catch (Exception ex){
				p.fail(ex);
				LOGGER.error("update  failed",ex.getCause());
				printStackTrace(ex);

			}

		}, ar->{
			if(ar.succeeded()) {
				LOGGER.info("update completed successfully");
				resultHandler.handle(Future.succeededFuture(OperationResponse.completedWithPlainText(Buffer.buffer().appendString("201"))));

			}
			else{
				LOGGER.info("update failed");
				LOGGER.error("Exception Ocuured while trying to update a User into database ",ar.cause());
				resultHandler.handle(Future.failedFuture(ar.cause()));

			}
		});


	}

	@Override
	public void findUser(User body, OperationRequest context, Handler<AsyncResult<OperationResponse>> resultHandler) {
		LOGGER.info("retrive a User from the database by email "+body);
		vertx.executeBlocking(p->{
			try {
				//FIXME ,should revise DatabseReposiorty design
				p.complete(((UserRepositoryImpl)repository).findByEmail(body.getEmail()));

			}
			catch (Exception ex){
				p.fail(ex);
				LOGGER.error("retrive all users from the database failed",ex.getCause());
				printStackTrace(ex);

			}

		}, ar->{
			if(ar.succeeded() && ar.result()!=null) {
				LOGGER.info("retrive user by email from the database completed successfully");
				resultHandler.handle(Future.succeededFuture(convertResultRecordToJson(ar.result())));

			}
			else if(ar.succeeded() && ar.result()==null) {
				LOGGER.info("retrive user by email from the database completed successfully");
				resultHandler.handle(Future.failedFuture(ar.cause()));

			}
			else{
				LOGGER.error("Exception Ocuured while trying to get users List from database ",ar.cause());

			}
		});
	}

	//FIXME should be moved to super interafce, and use Generics insted of using Object
	public OperationResponse convertResultListToJson(Object resultList){
		User body = new User();
		LOGGER.debug("resultlist size "+((List<User>)resultList).size());
		return OperationResponse.completedWithJson(body.ListToJson((List<User>) resultList));
	}

	public OperationResponse convertResultRecordToJson(Object result){
		User body = new User();
		body = (User) result;
		LOGGER.debug("result record "+ result);
		return OperationResponse.completedWithJson(body.toJson());
	}

	public void printStackTrace(Object t){
		((Throwable)t).printStackTrace();
		OperationResponse.completedWithPlainText(Buffer.buffer().appendString(((Throwable)t).getMessage()));

	}

}
