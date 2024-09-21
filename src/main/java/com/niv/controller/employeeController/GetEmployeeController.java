package com.niv.controller.employeeController;

import com.niv.exception.RoutingError;
import com.niv.models.dto.EmployeeMapper;
import com.niv.models.dto.EmployeeResponse;
import com.niv.models.entity.Employee;
import com.niv.models.dao.EmployeeRepo;
import com.niv.user.CommonController;
import com.niv.utils.ResponseUtils;
import io.vertx.rxjava.core.RxHelper;
import io.vertx.rxjava.ext.web.RoutingContext;
import rx.Single;

public enum GetEmployeeController implements CommonController {
    INSTANCE;

    @Override
    public void handle(RoutingContext context) {
        Single.just(context)
                .subscribeOn(RxHelper.blockingScheduler(context.vertx()))
                .map(this::getEmployeeById)
                .subscribe(
                        success -> ResponseUtils.writeJsonResponse(context, success),
                        error -> ResponseUtils.handleError(context, error)
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
            e.printStackTrace();
            throw new RoutingError(e.getMessage());
        }
    }
}
