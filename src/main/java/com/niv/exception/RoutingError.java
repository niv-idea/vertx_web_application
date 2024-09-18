package com.niv.exception;


import lombok.Data;

@Data
public class RoutingError extends RuntimeException{
    private Integer statusCode;

    public RoutingError(String message){
        super(message);
    }
//if you want to pass the statuscode then use this constructor
    public RoutingError(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
