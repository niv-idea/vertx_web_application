package com.niv.controller.customerController;


import com.niv.models.dto.CommonMapper;
import com.niv.models.dto.CustomerRequest;
import com.niv.utils.ResponseUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public enum AddCustomerController {
    INSTANCE;

    public static void handler(RoutingContext routingContext){

        try{
            JsonObject requestBody = routingContext.getBodyAsJson();
            CustomerRequest request = requestBody.mapTo(CustomerRequest.class);
            CommonMapper.INSTANCE.createCustomer(request);
            ResponseUtils.writeJsonResponse(routingContext);
        }catch (Exception e){
            e.printStackTrace();
            ResponseUtils.handleError(routingContext,e.getMessage());
        }

    }
}
