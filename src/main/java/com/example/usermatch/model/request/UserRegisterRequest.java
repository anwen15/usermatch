package com.example.usermatch.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -150010351605082849L;

    private String userAccount;

    private String checkPassword;

    private String userPassword;

    private String planetCode;
}
