package com.niv.models.repository;

import com.niv.models.entity.Customer;

import java.util.List;

public enum CustomerRepo {
    INSTANCE;

    private SqlFinder<Customer, Integer> customerFinder = new SqlFinder<>(Customer.class);

    public Customer findById(Integer id) {
        return customerFinder.findById(id);
    }

    public List<Customer> findAllCustomers() {
        return customerFinder.findAll();
    }
}
