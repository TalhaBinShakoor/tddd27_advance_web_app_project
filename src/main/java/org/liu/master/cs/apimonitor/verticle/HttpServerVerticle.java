package org.liu.master.cs.apimonitor.verticle;

import io.vertx.core.*;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
//import io.vertx.ext.jwt.JWTOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.api.contract.openapi3.OpenAPI3RouterFactory;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;
import io.vertx.ext.web.handler.StaticHandler;
import org.liu.master.cs.apimonitor.util.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Avert.x verticle which is reponsibole for dtarting up http server
 * @author Mutaz Hussein Kabashi
 * @version 1.0
 */
public class HttpServerVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerVerticle.class);
    private HttpServer server;

    @Override
    public void start(Promise<Void> promise) {
        OpenAPI3RouterFactory.create(this.vertx, "/openapi.json", openAPI3RouterFactoryAsyncResult -> {
            if (openAPI3RouterFactoryAsyncResult.succeeded()) {
                OpenAPI3RouterFactory routerFactory = openAPI3RouterFactoryAsyncResult.result();

                // Mount services on event bus based on extensions
                routerFactory.mountServicesFromExtensions();

                // Generate the router
                //CROS Handler should be added here
                //@see https://github.com/vert-x3/issues/issues/485
                routerFactory.addGlobalHandler(CorsHandler
                        .create("*")
                        .allowedHeaders(allowedHeaders())
                        .allowedMethods(allowedMethods()));

                //AWT token handler
                routerFactory.addSecurityHandler("jwtAuth", JWTAuthHandler.create(JwtHelper.getJWtProvider(vertx)));

                Router router = routerFactory.getRouter();
                //add ReactJs routing
                router.route("/*").handler(StaticHandler.create("app"));
                router.get().handler(routingContext -> routingContext.response().sendFile("app/index.html").end());



                //server = vertx.createHttpServer(new HttpServerOptions().setPort(8080).setHost("localhost"));
                server = vertx.createHttpServer(new HttpServerOptions().setPort(8080));
                server.requestHandler(router).listen(ar -> {
                    // Error starting the HttpServer
                    if (ar.succeeded()){
                        LOGGER.info("Http server start  ");
                        promise.complete();
                    }
                    else{
                        promise.fail(ar.cause());
                        LOGGER.error("server fail ",ar.cause());

                    }
                });
            } else {
                // Something went wrong during router factory initialization
                LOGGER.error("server fail ",openAPI3RouterFactoryAsyncResult.cause());
                promise.fail(openAPI3RouterFactoryAsyncResult.cause());
            }
        });
        //return promise.future();
    }

    private Set<String> allowedHeaders(){
        Set<String> allowedHeaders = new HashSet<>();
        allowedHeaders.add("x-requested-with");
        allowedHeaders.add("Access-Control-Allow-Origin");
        allowedHeaders.add("origin");
        allowedHeaders.add("Content-Type");
        allowedHeaders.add("accept");
        allowedHeaders.add("Authorization");
        return allowedHeaders;
    }

    private Set<HttpMethod> allowedMethods(){
        //The set of allowed HTTP methods(195)
        Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.OPTIONS);
        allowedMethods.add(HttpMethod.PUT);
        return allowedMethods;
    }

}

