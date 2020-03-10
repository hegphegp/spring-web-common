package com.hegp.core.exceptions;

public class ParamsMissingException extends RuntimeException {
    private Integer code = 500;

    public ParamsMissingException() { }

    public ParamsMissingException(String message) {
        super(message);
        this.code = 500;
    }

    public ParamsMissingException(String message, int code) {
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