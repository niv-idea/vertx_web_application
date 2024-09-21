package com.niv.controller.employeeController;

import com.niv.exception.RoutingError;
import com.niv.models.dto.EmployeeMapper;
import com.niv.models.dto.EmployeeResponse;
import com.niv.models.entity.Employee;
import com.niv.models.dao.EmployeeRepo;
import com.niv.user.CommonController;
import com.niv.utils.ResponseUtils;
import io.vertx.rxjava.ext.web.RoutingContext;
import rx.Single;

public enum GetEmployeeController implements CommonController {
    INSTANCE;

    @Override
    public void handle(RoutingContext context) {
        // Using Single to handle the asynchronous flow of getting employee by ID
        Single.fromCallable(() -> getEmployeeById(context))
                .subscribe(
                        response -> {
                            if (response != null) {
                                // Send the JSON response if successful
                                ResponseUtils.INSTANCE.writeJsonResponse(context, response);
                            } else {
                                // Handle case when employee not found
                                ResponseUtils.INSTANCE.handleError(context, "Employee not found");
                            }
                        },
                        error -> {
                            // Handle any error that occurred during the process
                            ResponseUtils.INSTANCE.handleError(context, error.getMessage());
                        }
                );
    }

    // Updated method to just return EmployeeResponse and avoid side effects inside it
    public EmployeeResponse getEmployeeById(RoutingContext context) {
        try {
            Integer id = Integer.valueOf(context.pathParam("employeeId"));
            Employee employee = EmployeeRepo.INSTANCE.findById(id);

            // If the employee is not found, return null
            if (employee == null) {
                return null;
            }

            // Map Employee to EmployeeResponse and return
            EmployeeResponse response= EmployeeMapper.INSTANCE.createEmployeeResponse(employee);
            return response;

        } catch (Exception e) {
            // If there's an exception, propagate it
            throw new RoutingError(e.getMessage(),402);
        }
    }
}
