package com.niv.controller.employeeController;

import com.niv.models.dto.EmployeeRequest;
import com.niv.models.dto.EmployeeMapper;
import com.niv.utils.ResponseUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.Data;

public enum AddEmployeeController {
    INSTANCE;
    public void handle(RoutingContext context){
        try{
            // context.request().getParam("id");
            JsonObject requestBody=context.getBodyAsJson();
            EmployeeRequest request=requestBody.mapTo(EmployeeRequest.class);
            EmployeeMapper.createEmployeeAndSave(request);
            ResponseUtils.INSTANCE.writeJsonResponse(context);
        }catch (Exception e){
            e.printStackTrace();
            ResponseUtils.handleError(context,e.getMessage());
        }
    }

}
