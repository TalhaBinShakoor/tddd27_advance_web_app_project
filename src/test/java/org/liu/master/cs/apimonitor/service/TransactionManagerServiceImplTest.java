package org.liu.master.cs.apimonitor.service;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThat;

import org.liu.master.cs.apimonitor.verticle.DatabaseVerticle;

import org.junit.jupiter.api.Test;

@ExtendWith(VertxExtension.class)
//@RunWith(MockitoJUnitRunner.class)
public class TransactionManagerServiceImplTest  {



    private WebServiceService webServiceService;
    private UserService userService;

    //private DatabaseVerticle databaseVerticle;
    private String databaseVerticleId ="";
    //private TransactionsManagerService transactionsManagerServiceProxy;

    @BeforeEach
    void prepare(Vertx vertx, VertxTestContext ctx) {
        vertx.deployVerticle(new DatabaseVerticle(), ctx.succeeding(id -> {
            userService = UserService.createProxy(vertx, "db_service.userService");
            webServiceService = WebServiceService.createProxy(vertx, "db_service.webService");
            databaseVerticleId = id;
            ctx.completeNow();
        }));
    }

    //https://dev.to/sip3/how-to-write-beautiful-unit-tests-in-vert-x-2kg7

    @Test
    void testInsert(Vertx vertx, VertxTestContext ctx) {
        Checkpoint checkWebServiceName = ctx.checkpoint();
        Checkpoint checkListSize = ctx.checkpoint();

        //Insert User Block
        JsonObject user = new JsonObject()
                .put("email", "talha@gmail.com")
                .put("firstName","Talha").put("lastName","Shakoor").put("password", "12345");
        JsonObject userRequestBody = new JsonObject()
                .put("body",user);
        DeliveryOptions userOptions = new DeliveryOptions();
        userOptions.addHeader("action", "insertUser");
        OperationRequest context1 = new OperationRequest();
        context1.setHeaders(userOptions.getHeaders());
        context1.setParams(userRequestBody);
        JsonObject userMessageBody = new JsonObject()
                .put("context",context1.toJson());
        //m1.put("context",context.toJson());
        vertx.eventBus()
                .publish("db_service.userService", userMessageBody,userOptions);


        JsonObject m1 = new JsonObject()
                .put("url", "http://cnn.com")
                .put("name","CNN").put("method","GET");
        JsonObject m2 = new JsonObject()
                .put("body",m1);
        DeliveryOptions options = new DeliveryOptions();
        options.addHeader("action", "insert");
        //options.addHeader("user","\"iat\":1622624522,\"exp\":1623229322,\"iss\":\"api-monitor\",\"sub\":\"123@test.com\"");
        OperationRequest context = new OperationRequest();
        context.setHeaders(options.getHeaders());
        //context.setUser(getUserData());
        context.setParams(m2);
        JsonObject mBody = new JsonObject()
                .put("context",context.toJson());
        //m1.put("context",context.toJson());
        vertx.eventBus()
                .publish("db_service.webService", mBody,options);


        webServiceService.findAll(context, ctx.succeeding(data -> ctx.verify(() -> {
            assertThat(data.getPayload().toJsonArray().getJsonObject(0).getString("name")).isEqualTo("CNN");
            checkWebServiceName.flag();
            System.out.println(data.getPayload().toJsonArray().size());
            assertThat(data.getPayload().toJsonArray().size()).isEqualTo(1);
            checkListSize.flag();
        })));



    }

      private JsonObject getUserData(){
        JsonObject user = new JsonObject();
        user.put("iat",1622624522);
        user.put("exp", 1623229322);
        user.put("iss","api-monitor");
        user.put("sub","123@test.com-1");
        return user;
      }

    @AfterEach
    void tearDown(Vertx vertx, VertxTestContext ctx){
        vertx.undeploy(databaseVerticleId, ctx.succeeding(id -> {
            System.out.println("verticle "+databaseVerticleId+" "+"has been undeployed successfully" );
            ctx.completeNow();
        }));

    }



}
