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

    public BaseResponse(int code,T data, String message) {
        Data = data;
        Code = code;
        this.message = message;
    }

    public BaseResponse(int code,T data) {
        this(code,data,  "");
    }
}
