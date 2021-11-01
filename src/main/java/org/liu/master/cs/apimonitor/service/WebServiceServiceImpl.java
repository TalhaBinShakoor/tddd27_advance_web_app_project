package org.liu.master.cs.apimonitor.service;

import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
/*import io.vertx.ext.web.api.service.ServiceRequest;
import io.vertx.ext.web.api.service.ServiceResponse;*/
import org.liu.master.cs.apimonitor.domain.User;
import org.liu.master.cs.apimonitor.domain.WebService;
import org.liu.master.cs.apimonitor.repository.DataBaseRepository;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.web.api.OperationResponse;
import org.liu.master.cs.apimonitor.repository.WebServiceRepositoryImpl;
import org.liu.master.cs.apimonitor.util.ServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * An implemenation to {@link WebServiceService}
 * @author Mutaz Hussein Kabashi
 * @version 1.0
 *
 */
public class WebServiceServiceImpl implements WebServiceService {

	private Vertx vertx;
	
	  private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceServiceImpl.class);


	private DataBaseRepository repository;

	public WebServiceServiceImpl(Vertx vertx, DataBaseRepository repository) {
		this.vertx = vertx;
		this.repository = repository;
	}


	@Override
	public  void findAll(OperationRequest context,
						 Handler<AsyncResult<OperationResponse>> resultHandler) {
		//FIXME Block IO code should be replaced by NON Blocking IO
		LOGGER.info("retrive all webservices from the database ");
		vertx.executeBlocking(p->{
            try {
                p.complete(repository.findAll());
            }
            catch (Exception ex){
                p.fail(ex);
                LOGGER.error("retrive all webservices from the database failed",ex.getCause());
                printStackTrace(ex);
            }

            }, ar->{
            if(ar.succeeded()) {
                LOGGER.info("retrieve all webservices from the database completed succefully");
                resultHandler.handle(Future.succeededFuture(convertResultListToJson(ar.result())));

            }
            else{
                LOGGER.error("Exception Ocuured while trying to get Webservices List from database ",ar.cause());
            }
        });
	}

	@Override
	public  void insert(WebService body,OperationRequest context,
						 Handler<AsyncResult<OperationResponse>> resultHandler) {
		//FIXME Block IO code should be replaced by NON Blocking IO
		LOGGER.info("insert a record into webservice table with the following webservice name "+body.getName());
		//FIXME checking if userdata is exist in context object shouldn't  be here but there is a problem
		// wuth the test unit of this class and we have to pass webservice without user , so that the unit test
		// could pass ,we have to invetigate why
		if(context.getHeaders().get("Authorization")!=null){
			body.setUser(ServiceHelper.extractPlainUserDataFromHeader(context));
		}
		LOGGER.info("headers  "+context.getHeaders().toString());
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
			else{
				LOGGER.error("Exception Ocuured while trying to insert a Webservice into database ",ar.cause());

			}
		});
	}

	@Override
	public  void update(WebService body,OperationRequest context,
						Handler<AsyncResult<OperationResponse>> resultHandler) {
		//FIXME Block IO code should be replaced by NON Blocking IO
		LOGGER.info("update a webservice record  with the following webservice name "+body.getName());
		//FIXME checking if userdata is exist in context object shouldn't  be here but there is a problem
		// wuth the test unit of this class and we have to pass webservice without user , so that the unit test
		// could pass ,we have to invetigate why
		if(context.getHeaders().get("Authorization")!=null){
			body.setUser(ServiceHelper.extractPlainUserDataFromHeader(context));
		}
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
				LOGGER.error("Exception Ocuured while trying to update a Web service into database ",ar.cause());
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	@Override
	public  void deleteWebService(WebService body,OperationRequest context,
						Handler<AsyncResult<OperationResponse>> resultHandler) {
		//FIXME Block IO code should be replaced by NON Blocking IO
		LOGGER.info("update a webservice record  with the following webservice name "+body.getName());
		//FIXME checking if userdata is exist in context object shouldn't  be here but there is a problem
		// wuth the test unit of this class and we have to pass webservice without user , so that the unit test
		// could pass ,we have to invetigate why
		if(context.getHeaders().get("Authorization")!=null){
			body.setUser(ServiceHelper.extractPlainUserDataFromHeader(context));
		}
		vertx.executeBlocking(p->{
			try {
				((WebServiceRepositoryImpl)repository).deleteWebServicesById(body.getId());
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
				LOGGER.error("Exception Ocuured while trying to update a Web service into database ",ar.cause());
				resultHandler.handle(Future.failedFuture(ar.cause()));
			}
		});
	}

	public  void findWebServicesByUser(OperationRequest context,
						 Handler<AsyncResult<OperationResponse>> resultHandler) {
		//FIXME Block IO code should be replaced by NON Blocking IO
		//FIXME checking if userdata is exist in context object shouldn't  be here but there is a problem
		// wuth the test unit of this class and we have to pass webservice without user , so that the unit test
		// could pass ,we have to invetigate why
		LOGGER.info("retrive all webservices from the database ");
		vertx.executeBlocking(p->{
			try {
				p.complete(((WebServiceRepositoryImpl)(repository)).findByWebServicesByUser(ServiceHelper.extractPlainUserDataFromHeader(context).getId()));
			}
			catch (Exception ex){
				p.fail(ex);
				LOGGER.error("retrive all webservices from the database failed",ex.getCause());
				printStackTrace(ex);
			}

		}, ar->{
			if(ar.succeeded()) {
				LOGGER.info("retrive all webservices from the database completed succefully");
				resultHandler.handle(Future.succeededFuture(convertResultListToJson(ar.result())));

			}
			else{
				LOGGER.error("Exception Ocuured while trying to get Webservices List from database ",ar.cause());
				resultHandler.handle(Future.failedFuture(ar.cause()));

			}
		});
	}

	//FIXME should be moved to super interafce, and use Generics insted of using Object
	public OperationResponse convertResultListToJson(Object resultList){
		WebService body = new WebService();
		LOGGER.debug("resultlist size "+((List<WebService>)resultList).size());
		return OperationResponse.completedWithJson(body.ListToJson((List<WebService>) resultList));
	}

	public void printStackTrace(Object t){
		((Throwable)t).printStackTrace();
		OperationResponse.completedWithPlainText(Buffer.buffer().appendString(((Throwable)t).getMessage()));

	}

}
