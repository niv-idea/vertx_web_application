package com.niv.models.repository;

import com.niv.models.entity.Address;

public enum AddressRepo {
    INSTANCE;

    private SqlFinder<Address, Integer> addressFinder = new SqlFinder<>(Address.class);

    public Address findById(Integer id) {
        return addressFinder.findById(id);
    }
}
