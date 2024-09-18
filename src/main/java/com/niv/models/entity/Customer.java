package com.niv.models.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.*;

@Data
@Entity
@Table(name = "customer")
//@EqualsAndHashCode(callSuper =true)
public class Customer extends BaseModel {
    private String firstName;
    private String lastName;
    private Integer age;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Address address;
}
