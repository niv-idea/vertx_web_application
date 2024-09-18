package com.niv.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "address")
//@EqualsAndHashCode(callSuper = true)
public class Address extends BaseModel{
    private String city;
    private String state;
    private Integer pinCode;
//here we are showing relationship between two tables/resources
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
