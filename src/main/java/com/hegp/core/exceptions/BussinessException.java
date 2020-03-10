package com.hegp.core.exceptions;

public class BussinessException extends RuntimeException {
    private Integer code = 500;

    public BussinessException() { }

    public BussinessException(String message) {
        super(message);
        this.code = 500;
    }

    public BussinessException(String message, int code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}