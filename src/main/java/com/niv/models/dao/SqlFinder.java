package com.niv.models.dao;

import com.niv.factory.MySqlBeanFactory;
import com.niv.models.entity.Employee;
import io.ebean.Database;
import io.ebean.ExpressionList;
import io.ebean.Query;

import java.util.List;

public class SqlFinder<T,I> {
    private Class<T> tClass;
    //create constructor here
    public SqlFinder(Class<T> tClass) {
        this.tClass = tClass;
    }

    private Database database() {
        return MySqlBeanFactory.INSTANCE.dbConnection();
    }

    public T findById(I id) {
        return database().find(tClass, id);
    }
    public Employee deleteById(T entity){
        database().delete(entity);
        return new Employee();
    }

    public Query<T> query() {
        return database().find(tClass);
    }

    public List<T> findAll() {
        return query().findList();
    }

    public ExpressionList<T> getExpressionList() {
        return database().find(tClass).where();
    }

}
