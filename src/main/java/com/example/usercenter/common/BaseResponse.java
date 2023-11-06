package com.example.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 * @param <T>
 */
@Data
public class BaseResponse<T> implements Serializable {
    private T Data;

    private int Code;
    private String message;
    private String description;

    public BaseResponse(int code,T data, String message,String description) {
        Data = data;
        Code = code;
        this.message = message;
        this.description=description;
    }

    public BaseResponse(EoorCode  eoorCode) {
        this(eoorCode.getCode(),null,eoorCode.getMessage(),eoorCode.getDescription());
    }

    public BaseResponse(int code, T data,String description) {
        this(code,data,  "",description);
    }

}
