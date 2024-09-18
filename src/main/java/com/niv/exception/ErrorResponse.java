package com.niv.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private String  error;
}
