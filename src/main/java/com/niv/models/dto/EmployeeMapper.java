package com.niv.models.dto;

import com.niv.controller.employeeController.AddEmployeeController;
import com.niv.factory.MySqlBeanFactory;
import com.niv.models.entity.Employee;

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
    public static void createEmployeeAndSave(EmployeeRequest request){
        Employee employee = new Employee();
        employee.setEmployeeAge(request.getEmployeeAge());
        employee.setEmployeeName(request.getEmployeeName());
        employee.setEmployeeEmail(request.getEmployeeEmail());

        // DbConnection.sqlDb.save(employee);
        MySqlBeanFactory.INSTANCE.save(employee);
    }
}
