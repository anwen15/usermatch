package com.example.usercenter.common;

import lombok.Data;

public class ResultUtils {
    /**
     * 通用类
     * @param data
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 通用异常
     * @param eoorCode
     * @return
     */
    public static BaseResponse error(EoorCode eoorCode) {
        return new BaseResponse(eoorCode);
    }
    public static BaseResponse error(EoorCode eoorCode,String message,String description) {
        return new BaseResponse(eoorCode.getCode(),null,message,description);
    }
    public static BaseResponse error(int Code,String message,String description) {
        return new BaseResponse(Code,null, message, description);
    }
    public static BaseResponse error(EoorCode eoorCode,String description) {
        return new BaseResponse(eoorCode.getCode(),null,eoorCode.getMessage(),description);
    }

}
