package com.niv.controller.employeeController;

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

            Single.just(context)
                    .subscribeOn(RxHelper.blockingScheduler(context.vertx()))
                    .map((routingContext)->{
                        GetAllEmployeeController.INSTANCE.getAllEmployees(routingContext);
                        return routingContext;
                    })
                    .subscribe(
                            response -> {
                                ResponseUtils.writeJsonResponse(context,response);
                            },
                            error -> ResponseUtils.handleError(context,error)
                    );

    }
    public void getAllEmployees(RoutingContext context){
        try{  //iam going to write here the normal programming
            Response response=new Response();
            List<Employee> employees = EmployeeRepo.INSTANCE.findAll();
            //retrieve now

           List<EmployeeResponse> responses =employees.stream()
                    .map(EmployeeMapper.INSTANCE::createEmployeeResponse)
                    .collect(Collectors.toList());
           response.employees=responses;
            ResponseUtils.writeJsonResponse(context,response);

        }catch (Exception e){
            e.printStackTrace();
            ResponseUtils.handleError(context,e.getMessage());
        }
    }
    private static class Response{
        public List<EmployeeResponse> employees=new ArrayList<>();

    }
}

