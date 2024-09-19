package com.niv;

import com.niv.configure.ConfigManager;
import com.niv.v2.employee.MountEmployeeRouter;

import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.BodyHandler;

public class RxHttpRouter extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startFuture) throws Exception {
        System.out.println("inside RxHttp Router");

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        HttpServerOptions serverOptions = new HttpServerOptions();
        serverOptions.setCompressionSupported(true);

        vertx.createHttpServer(serverOptions)
                .requestHandler(router)
                .listen(ConfigManager.INSTANCE.getMainConfig().getInteger("port"), handler -> {
                    if (handler.succeeded()) {
                        System.out.println("RxHttp server started on port: " + ConfigManager.INSTANCE.getMainConfig().getInteger("port"));
                        startFuture.complete(); // Signal that the start was successful
                    } else {
                        System.out.println("RxHttpRouter failed to start on port");
                        startFuture.fail(handler.cause()); // Propagate the cause of the failure
                    }
                });

        createRouter(router); // Ensure router is created before binding the server
    }

    private void createRouter(Router router){
        router.mountSubRouter("/employee", MountEmployeeRouter.INSTANCE.router(vertx));
    }
}
