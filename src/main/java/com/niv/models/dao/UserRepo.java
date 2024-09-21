package com.niv.models.dao;

import com.niv.models.entity.User;

import java.util.Optional;

public enum UserRepo {
    INSTANCE;
    public SqlFinder<User, Integer> userFinder =new SqlFinder<>(User.class);



    public Optional<User> findUserById(Integer id) {
        return userFinder.getExpressionList()
                .idEq(id)
                .findOneOrEmpty();
    }




}
