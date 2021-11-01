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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.liu.master.cs.apimonitor.verticle.DatabaseVerticle;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(VertxExtension.class)
//@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {



    private UserService userService;
    //private DatabaseVerticle databaseVerticle;
    private String databaseVerticleId ="";
    //private TransactionsManagerService transactionsManagerServiceProxy;

    @BeforeEach
    void prepare(Vertx vertx, VertxTestContext ctx) {
        vertx.deployVerticle(new DatabaseVerticle(), ctx.succeeding(id -> {
            userService = UserService.createProxy(vertx, "db_service.userService");
            databaseVerticleId = id;
            ctx.completeNow();
        }));
    }

    //https://dev.to/sip3/how-to-write-beautiful-unit-tests-in-vert-x-2kg7

    //@Test
    void testInsert(Vertx vertx, VertxTestContext ctx) {
        Checkpoint checkUserServiceName = ctx.checkpoint();
        Checkpoint checkListSize = ctx.checkpoint();


        JsonObject m1 = new JsonObject()
                .put("email", "talha@gmail.com")
                .put("firstName","Talha").put("lastName","Shakoor").put("password", "12345");
        JsonObject m2 = new JsonObject()
                .put("body",m1);
        DeliveryOptions options = new DeliveryOptions();
        options.addHeader("action", "insertUser");
        OperationRequest context = new OperationRequest();
        context.setHeaders(options.getHeaders());
        context.setParams(m2);
        JsonObject mBody = new JsonObject()
                .put("context",context.toJson());
        //m1.put("context",context.toJson());
        vertx.eventBus()
                .publish("db_service.userService", mBody,options);


        userService.findAllUsers(context, ctx.succeeding(data -> ctx.verify(() -> {
            assertThat(data.getPayload().toJsonArray().getJsonObject(0).getString("email")).isEqualTo("talha@gmail.com");
            checkUserServiceName.flag();
            System.out.println(data.getPayload().toJsonArray().size());
            assertThat(data.getPayload().toJsonArray().size()).isEqualTo(1);
            checkListSize.flag();
        })));



    }

    @AfterEach
    void tearDown(Vertx vertx, VertxTestContext ctx){
        vertx.undeploy(databaseVerticleId, ctx.succeeding(id -> {
            System.out.println("verticle "+databaseVerticleId+" "+"has been undeployed successfully" );
            ctx.completeNow();
        }));

    }



}
