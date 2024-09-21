package com.niv.controller.employeeController;

import com.niv.factory.MySqlBeanFactory;
import com.niv.models.dto.EmployeeRequest;
import com.niv.models.entity.Employee;
import com.niv.models.dao.EmployeeRepo;
import com.niv.user.CommonController;
import com.niv.utils.ResponseUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.RxHelper;
import io.vertx.rxjava.ext.web.RoutingContext;
import rx.Single;

import java.util.Optional;

public enum UpdateEmployeeController implements CommonController {
    INSTANCE;
    @Override
    public void handle(RoutingContext context) {
        Single.just(context)
                .subscribeOn(RxHelper.blockingScheduler(context.vertx()))
                .map(routingContext -> {
                    updateEmployee(routingContext);
                    return routingContext;
                })
                .subscribe(
                        response -> {
                            if (!context.response().ended()) {
                                ResponseUtils.writeJsonResponse(context, response);
                            }
                        },
                        error -> {
                            if (!context.response().ended()) {
                                ResponseUtils.handleError(context, error);
                            }
                        }
                );
    }

    public void updateEmployee(RoutingContext context) {
        try {
            // Retrieve employee ID from the path parameter
            String empId = context.request().getParam("empId");
            Integer id = Integer.valueOf(empId);

            // Find the employee by ID
            Optional<Employee> employee = Optional.ofNullable(EmployeeRepo.INSTANCE.findById(id));

            if (employee.isPresent()) {
                // Parse the request body for updated details from client on his demand
                EmployeeRequest request = context.getBodyAsJson().mapTo(EmployeeRequest.class);

                // Update employee details
                //updateEmployeeById(request, employee.get());
                updateEmployee(request,id);

                // Create success response
                JsonObject jsonResponse = new JsonObject()
                        .put("status", true)
                        .put("message", "Employee updated successfully");

                ResponseUtils.writeJsonResponse(context, jsonResponse);

            } else {
                // Handle case where employee is not found
                JsonObject jsonResponse = new JsonObject()
                        .put("status", false)
                        .put("error", "Employee not found");

                ResponseUtils.writeJsonResponse(context, jsonResponse);
            }

        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtils.handleError(context, e.getMessage());
        }
    }

//    public void updateEmployeeById(EmployeeRequest request, Employee employee) {
//        // Update the employee object with new values
//
//        employee.setEmployeeName(request.getEmployeeName());
//        employee.setEmployeeEmail(request.getEmployeeEmail());
//        employee.setEmployeeSalary(request.getEmployeeSalary());
////        employee.setEmployeeAge(request.getEmployeeAge());
//        employee.setGender(request.getGender());
//
//        // Save the updated employee details to the database
//        MySqlBeanFactory.INSTANCE.save(employee);
//    }
    private static void updateEmployee(EmployeeRequest request, Integer id){
       Employee employee =EmployeeRepo.INSTANCE.findById(id);
       if(request.getEmployeeName()!=null)
        {
            employee.setEmployeeName(request.getEmployeeName());
        }
        if(request.getEmployeeEmail()!=null){
            employee.setEmployeeEmail(request.getEmployeeEmail());
        }
        employee.setEmployeeSalary(request.getEmployeeSalary());

        employee.setEmployeeAge(request.getEmployeeAge());
//        employee.setEmployeeAge(request.getEmployeeAge());
        if(request.getGender()!=null){
            employee.setGender(request.getGender());
        }

        // Save the updated employee details to the database
        MySqlBeanFactory.INSTANCE.save(employee);

    }


}
