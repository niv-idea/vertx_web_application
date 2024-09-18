package com.niv.models.dto;

import lombok.Data;

@Data
public class EmployeeRequest {
    private String employeeName;
    private String employeeEmail;
    private Integer employeeAge;
    private double employeeSalary;
}
