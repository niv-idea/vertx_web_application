package com.niv.models.dao;

import com.niv.models.entity.Employee;
import io.ebean.ExpressionList;

import java.awt.font.OpenType;
import java.util.List;
import java.util.Optional;

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
   public Optional<Employee> findEmployeeById(Integer id){
        return employeeFinder.getExpressionList().idEq(id).findOneOrEmpty();
   }

   public ExpressionList<Employee> finder() {
        return employeeFinder.query().where();
   }
}
