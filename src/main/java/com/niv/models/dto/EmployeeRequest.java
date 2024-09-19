package com.niv.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeRequest {
    private String employeeName;
    private String employeeEmail;
    private Integer employeeAge;
    private double employeeSalary;
    private String gender;
}
