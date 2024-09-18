package com.niv.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
//what is use of this
@Builder
public class EmployeeResponse {

    private Integer employeeId;
    private String employeeName;
    private String employeeEmail;
    private Integer employeeAge;
    private double employeeSalary;
    private Long createdAt;
    private Long updatedAt;
}
