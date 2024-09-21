package com.niv;
import com.niv.configure.ConfigManager;
import com.niv.factory.MySqlBeanFactory;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.rxjava.core.AbstractVerticle;

import java.util.concurrent.CompletableFuture;

public class MainVerticle extends AbstractVerticle {
    public static final int PORT = 8080;
    @Override
    public void start() throws Exception {
/*
This line sets the main configuration in the singleton instance of the ConfigManager class.
It fetches the configuration using a method config(), and then sets it via the setMainConfig() method.
 The configuration becomes accessible globally via ConfigManager.INSTANCE.
 */
        ConfigManager.INSTANCE.setMainConfig(config());

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



// this is used for provide current runtime
private int getProcessors() {
    return Math.max(1, Runtime.getRuntime().availableProcessors());
    }
}


