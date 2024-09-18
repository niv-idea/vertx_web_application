package com.niv.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "employee")
public class Employee  extends BaseModel{
    private String employeeName;
    private String employeeEmail;
    private Integer employeeAge;
    private double employeeSalary;
    private String gender;

}
