package com.niv;

import com.niv.configure.ConfigManager;
import com.niv.controller.employeeController.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class HttpRoutes extends AbstractVerticle {
private HttpServer httpServer;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Router router=Router.router(vertx);
        router.route().handler(BodyHandler.create());
        HttpServerOptions httpServerOptions=new HttpServerOptions();

        httpServerOptions.setCompressionSupported(true);
        httpServer=vertx.createHttpServer(httpServerOptions).
                listen(9999,handler->{
            if(handler.succeeded()){
                System.out.println("vertical started on port : "+ConfigManager.INSTANCE.getMainConfig().getJsonObject("port"));
                try {
                    startPromise.complete();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                System.out.println("vertical failed to start on port");
                startPromise.fail(handler.cause().getMessage());
            }
        });
        createRoutes(router);

        //now create the endpoints of web application
       // super.start(startPromise);
    }
    public void createRoutes(final Router router){
        router.post("/employee/save").handler(AddEmployeeController.INSTANCE::handle);
//        router.get("/employee/:employeeId").handler(GetEmployeeController.INSTANCE::handle);
//        router.get("/employee").handler(GetAllEmployeeController.INSTANCE::handle);
//        router.put("/employee/update/:employeeId").handler(UpdateEmployeeController.INSTANCE::handle);
//        router.delete("/employee/delete/:employeeId").handler(DeleteEmployeeController.INSTANCE::handle);
    }

}
