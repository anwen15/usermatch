package com.example.usermatch.service;

import com.example.usermatch.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author nicefang
* &#064;description  针对表【user】的数据库操作Service
* &#064;createDate  2023-10-30 21:06:35
 */
public interface UserService extends IService<User> {
    /**
     *  用户登录态
     */
    /**
     * 用户注释
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkpassword  用户确认密码
     */
    long userRegister(String userAccount, String userPassword,String checkpassword,String planetcode);

    /**
     * 用户登录
     *
     * @return
     */
    User dologin(String userAccount, String userPassword, HttpServletRequest request);

    //用户脱敏
    User getSaftyUser(User user);
    /**
     * 用户注销
     */
    int userlogout(HttpServletRequest request);
}
