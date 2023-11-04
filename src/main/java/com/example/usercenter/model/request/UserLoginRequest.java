package com.example.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = -8499958557021549254L;

    private String userAccount;

    private String userPassword;
}
