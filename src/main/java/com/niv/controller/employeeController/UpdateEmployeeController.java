package com.niv.controller.employeeController;

import com.niv.exception.RoutingError;
import com.niv.factory.MySqlBeanFactory;
import com.niv.models.dto.EmployeeRequest;
import com.niv.models.dto.SuccessResponse;
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
        // If there's an exception, propagate it
        Single.just(context)
                .subscribeOn(RxHelper.blockingScheduler(context.vertx()))
                .map(this::updateEmployee)
                .subscribe(
                        success -> ResponseUtils.writeJsonResponse(context, success),
                        error -> ResponseUtils.handleError(context, error)
                );
    }

    private SuccessResponse updateEmployee(RoutingContext context) {
        try {
            // Retrieve employee ID from the path parameter
            String empId = context.request().getParam("empId");
            Integer id = Integer.valueOf(empId);
            EmployeeRequest employeeRequest = context.getBodyAsJson().mapTo(EmployeeRequest.class);
            Employee employeeFromDb = EmployeeRepo.INSTANCE.finder().eq("id", id).setMaxRows(1)
                    .findOneOrEmpty().orElseThrow(() -> new RoutingError("Invalid Id, Employee not found"));
            updateEmployee(employeeRequest, employeeFromDb);
            return SuccessResponse.generateSuccessResponse();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RoutingError(e.getMessage());
        }
    }

    private static void updateEmployee(EmployeeRequest request, Employee employee){
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
        employee.update();

    }


}
