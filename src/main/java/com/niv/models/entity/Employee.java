package com.niv.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
@EqualsAndHashCode(callSuper = false)
public class Employee  extends BaseModel{
    @Column(name = "employeeName")
    private String employeeName;

    @Column(name = "employeeEmail")
    private String employeeEmail;

    @Column(name = "employeeAge")
    private Integer employeeAge;

    @Column(name = "employeeSalary")
    private double employeeSalary;

    @Column(name = "gender")
    private String gender;

}
