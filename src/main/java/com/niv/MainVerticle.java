package com.niv;

import com.niv.configure.ConfigManager;
import com.niv.factory.MySqlBeanFactory;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.rxjava.core.AbstractVerticle;

import java.util.concurrent.CompletableFuture;

public class MainVerticle extends AbstractVerticle {
    public static final int PORT = 9999;
    @Override
    public void start() throws Exception {

        ConfigManager.INSTANCE.setMainConfig(config());

//        vertx.deployVerticle(HttpRoutes.class.getName(),
//                new DeploymentOptions().setInstances(getProcessors()),
//                handler -> {
//                    if (handler.succeeded()) {
//                        System.out.println("HttpRouter deploy successfully");
//                        startPromise.complete();
//                    } else {
//                        System.out.println("HttpRouter failed in deploy");
//                        startPromise.fail(handler.cause());
//                    }
//                });
        vertx.deployVerticle(RxHttpRouter.class.getName(),
                new DeploymentOptions().setInstances(getProcessors()),
                completionHandler -> {
                    if (completionHandler.succeeded()) {

                        System.out.println("Deployed : "+ RxHttpRouter.class.getSimpleName() +", with Id : "+completionHandler);
//
                    } else {
                        System.out.println("RxHttp router failed in deploy");
                      //  startPromise.fail(completionHandler.cause());
                    }
                });
        CompletableFuture.runAsync(()-> {
            MySqlBeanFactory.INSTANCE.init();
        });
    }

    public static void main(String[] args) {
        Vertx vertx1=Vertx.vertx();
        // com.example.MainVerticle mainVerticle=new com.example.MainVerticle();
        //no worry he direct pass the object here
        vertx1.deployVerticle(new MainVerticle(),(handle)->{
            if(handle.succeeded()){
                System.out.println("com.example.MainVerticle deployed successfully");
            } else {
                handle.cause().printStackTrace();
            }
        });
        CompletableFuture.runAsync(()-> {
           MySqlBeanFactory.INSTANCE.init();
        });
    }

// this is used for provide current runtime
private int getProcessors() {
    return Math.max(1, Runtime.getRuntime().availableProcessors());
    }
}


