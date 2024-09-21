package com.niv.controller.employeeController;

import com.niv.admin.authent.AccessMiddleware;
import com.niv.admin.user.request.UserLoginRequest;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public enum GetAllEmployeeController implements CommonController {
    INSTANCE;


    @Override
    public void handle(RoutingContext context) {
       //here we are doing reactive programming

        AccessMiddleware.INSTANCE.authenticateRequest(context)
                    .map(this::getAllEmployees)
                    .subscribe(
                            success -> ResponseUtils.writeJsonResponse(context, success),
                            error -> ResponseUtils.handleError(context, error)
                    );

    }
    private Response  getAllEmployees(UserLoginRequest request){
        try{  //iam going to write here the normal programming
            Response response=new Response();
            List<Employee> employees = EmployeeRepo.INSTANCE.findAll();
            //retrieve now

           List<EmployeeResponse> responses =employees.stream()
                    .map(EmployeeMapper.INSTANCE::createEmployeeResponse)
                    .collect(Collectors.toList());
           response.employees=responses;
           return response;
        }catch (Exception e){
            e.printStackTrace();
            throw new RoutingError(e.getMessage());
        }
    }
    private static class Response{
        public List<EmployeeResponse> employees=new ArrayList<>();

    }

}

