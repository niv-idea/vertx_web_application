package com.niv.models.dto;

import lombok.Data;

@Data
public class AddressRequest {
    private String city;
    private String state;
    private Integer pinCode;
}
