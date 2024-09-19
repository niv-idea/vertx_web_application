package com.niv.controller.employeeController;

import com.niv.models.dto.EmployeeMapper;
import com.niv.models.entity.Employee;
import com.niv.models.repository.EmployeeRepo;
import com.niv.user.NewCommonController;
import com.niv.utils.ResponseUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.RxHelper;
import io.vertx.rxjava.ext.web.RoutingContext;
import rx.Single;

import java.util.Optional;

public enum DeleteEmployeeController implements NewCommonController {
    INSTANCE;
    @Override
    public void handle(RoutingContext context) {
        Single.just(context)
                .subscribeOn(RxHelper.blockingScheduler(context.vertx()))
                .map(routingContext->{
                    DeleteEmployeeController.deleteEmployeeById(routingContext);
                    return routingContext;
                })
                .subscribe(
                        response -> {
                           if(!context.response().ended()) {
                                ResponseUtils.writeJsonResponse(context, response);
                            }
                        },
                        error -> {
                            if(!context.response().ended()) {
                                ResponseUtils.handleError(context, error);
                            }
                        }
                );
    }
    public static void deleteEmployeeById(RoutingContext context) {
        try{
            String empId = context.request().getParam("empId");
            System.out.println("empId : " + empId);
            Integer id = Integer.valueOf(empId);
            Optional<Employee> employee = Optional.ofNullable(EmployeeRepo.INSTANCE.findById(id));
            if (employee.isPresent()) {
                employee.get().delete();
                // Create a JSON response object for success
                JsonObject jsonResponse = new JsonObject()
                        .put("status", true)
                        .put("message", "Employee deleted successfully");
                ResponseUtils.writeJsonResponse(context, jsonResponse);
            }else {
                // Handle case where employee is not found
                JsonObject jsonResponse = new JsonObject()
                        .put("status", false)
                        .put("error", "Employee not found");
                ResponseUtils.writeJsonResponse(context, jsonResponse);
            }
          }catch (Exception e){
            e.printStackTrace();
            ResponseUtils.handleError(context,e.getMessage());
        }


    }


}
