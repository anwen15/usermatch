package com.example.usercenter.service;

import com.example.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author nicefang
* @description 针对表【user】的数据库操作Service
* @createDate 2023-10-30 21:06:35
*/
public interface UserService extends IService<User> {
    /**
     * 用户注释
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkpassword  用户确认密码
     * @return
     */
    long userRegister(String userAccount, String userPassword,String checkpassword);

    /**
     * 用户登录
     *
     * @param userAccount
     * @param userPassword
     * @return
     */
    User dologin(String userAccount, String userPassword);
}
