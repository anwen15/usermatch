package com.example.usercenter.excepttion;

import com.example.usercenter.common.EoorCode;

public class BusinessException extends RuntimeException {
    private int code;
    private String description;

    public BusinessException(int code,String message, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(EoorCode errorcode,String description) {
        super(errorcode.getMessage());
        this.code = errorcode.getCode();
        this.description = description;
    }
    public BusinessException(EoorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
