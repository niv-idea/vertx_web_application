package com.niv.models.repository;

import com.niv.models.entity.Employee;
import io.ebean.ExpressionList;

import java.util.List;

public enum EmployeeRepo {
    INSTANCE;

    private SqlFinder<Employee, Integer> employeeFinder = new SqlFinder<>(Employee.class);

    public Employee findById(Integer id) {
        return employeeFinder.findById(id);
    }

    public List<Employee> findAll() {
        return employeeFinder.findAll();
    }

    public ExpressionList<Employee> findAllEmployee() {
        return employeeFinder.query().where();
    }

}
