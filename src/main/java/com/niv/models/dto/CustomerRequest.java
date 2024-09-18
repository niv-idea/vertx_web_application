package com.niv.models.dto;

import lombok.Data;

@Data
public class CustomerRequest {
    private String firstName;
    private String lastName;
    private Integer age;
    private AddressRequest address;
}
