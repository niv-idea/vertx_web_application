package com.niv.controller.employeeController;

import com.niv.admin.authent.AccessMiddleware;

import com.niv.admin.user.request.UserLoginRequest;
import com.niv.exception.RoutingError;
import com.niv.models.dto.EmployeeRequest;
import com.niv.models.dto.EmployeeMapper;
import com.niv.models.dto.SuccessResponse;
import com.niv.utils.ResponseUtils;


import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

public enum AddEmployeeController {
    INSTANCE;

    public void handle(RoutingContext context) {
        AccessMiddleware.INSTANCE.authenticateRequest(context)

                .map(this::createEmployee)
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
    public SuccessResponse createEmployee(UserLoginRequest request){
        try{
            JsonObject requestBody =  request.getContext().getBodyAsJson();
            System.out.println("Employee Request : "+requestBody.encode());
            EmployeeRequest req = requestBody.mapTo(EmployeeRequest.class);

            EmployeeMapper.createEmployeeAndSave(req);

            //ResponseUtils.writeJsonResponse(context);

        }catch (Exception e){
            e.printStackTrace();
           // ResponseUtils.handleError(context);
            throw new RoutingError(e.getMessage());
        }
      return  SuccessResponse.generateSuccessResponse();
    }


}
