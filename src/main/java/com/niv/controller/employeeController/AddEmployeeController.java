package com.niv.controller.employeeController;

import com.niv.models.dto.EmployeeRequest;
import com.niv.models.dto.EmployeeMapper;
import com.niv.models.entity.Employee;
import com.niv.utils.ResponseUtils;


import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.RxHelper;
import io.vertx.rxjava.ext.web.RoutingContext;
import lombok.Data;
import rx.Single;

import java.util.concurrent.Callable;

public enum AddEmployeeController {
    INSTANCE;

    public void handle(RoutingContext context) {
        Single.just(context)
                .subscribeOn(RxHelper.blockingScheduler(context.vertx()))
                .map(routingContext-> {
                    createEmployee(routingContext);
                    return routingContext;
                })
                .subscribe(
                        response -> {
                            if (!context.response().ended()) {
                                ResponseUtils.writeJsonResponse(context);
                            }
                        },
                        error -> {
                            if (!context.response().ended()) {
                                ResponseUtils.handleError(context);
                            }
                        }
                );


    }
    public static void createEmployee(RoutingContext context){
        try{
            JsonObject requestBody =  context.getBodyAsJson();
            EmployeeRequest request = requestBody.mapTo(EmployeeRequest.class);

            EmployeeMapper.createEmployeeAndSave(request);

            ResponseUtils.writeJsonResponse(context);

        }catch (Exception e){
            e.printStackTrace();
            ResponseUtils.handleError(context);
        }

    }


}
