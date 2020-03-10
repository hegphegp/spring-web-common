package com.hegp.core.domain;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private Integer code;
    private T data;
    private String msg;

    public static Response success() {
        return new Response(200, "success");
    }

    public static Response success(String msg) {
        return new Response(200, msg);
    }

    public static Response success(Object data) {
        return new Response(200, "success", data);
    }

    public static Response error(String msg) {
        return new Response(500, msg);
    }

    public static Response error(Integer code, String msg) {
        return new Response(code, msg);
    }

    public static Response build(Object data) {
        return new Response(200, "success", data);
    }

    public static Response build(Integer code, String msg) {
        return new Response(code, msg);
    }

    public static Response build(Integer code, String msg, Object data) {
        return new Response(code, msg, data);
    }

    public Response() {
        super();
    }

    public Response(Integer code) {
        this.code = code;
    }

    public Response(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Response{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}