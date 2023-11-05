package com.example.usercenter.common;

import lombok.Data;

public class ResultUtils {
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0, data, "ok");
    }
}
