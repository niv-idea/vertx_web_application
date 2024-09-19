package com.niv.models.dto;

import com.niv.exception.RoutingError;
import com.niv.factory.MySqlBeanFactory;
import com.niv.models.entity.Employee;

import java.util.Optional;

public enum EmployeeMapper {
    INSTANCE;

    public EmployeeResponse createEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .employeeId(employee.getId())
                .employeeName(employee.getEmployeeName())
                .employeeEmail(employee.getEmployeeEmail())
                .employeeSalary(employee.getEmployeeSalary())
                .employeeAge(employee.getEmployeeAge())
                .createdAt(employee.getCreatedAt().getTime())
                .updatedAt(employee.getUpdatedAt().getTime())
                .build();
    }
    public static Employee createEmployeeAndSave(EmployeeRequest request){
        Employee employee = new Employee();
        if(request.getEmployeeName()==null){
            throw  new RoutingError("please provide name",400);
        }
        employee.setEmployeeName(request.getEmployeeName());

        if(request.getEmployeeEmail()==null){
            throw new RoutingError("please provide email",400);
        }
        employee.setEmployeeEmail(request.getEmployeeEmail());

        if(request.getEmployeeAge()==null){
          throw new RoutingError("please provide age ",400);
        }
        employee.setEmployeeAge(request.getEmployeeAge());

        employee.setEmployeeSalary(request.getEmployeeSalary());

        if(request.getGender()==null){
            throw new RoutingError("[please provide gender",400);
        }
        employee.setGender(request.getGender());


        // DbConnection.sqlDb.save(employee);
        MySqlBeanFactory.INSTANCE.save(employee);

        return employee;
    }

}
