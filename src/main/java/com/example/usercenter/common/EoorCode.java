package com.example.usercenter.common;

public enum EoorCode {
    SUCCESS(20000, "成功", ""),
    PARAMS_ERROR(40000, "参数错误", ""),
    NULL_ERROR(40001, "参数为空", ""),
    NOT_LOGIN(40100, "未登录", ""),
    NO_AUTH(40101, "无权限", ""),
    SYSTEM_ERROR(50000, "系统异常", "");

    private final int code;
    private final String message;
    private final String description;

    EoorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
