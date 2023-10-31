package com.example.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usercenter.model.domain.User;
import com.example.usercenter.service.UserService;
import com.example.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author nicefang
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-10-30 21:06:35
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Resource
    UserMapper userMapper;

    //密码加密
    public static final String stal="anwen";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkpassword) {
        //1.校验
        if (StringUtils.isAnyEmpty(userAccount,userPassword,checkpassword)) {
            return -1;
        }
        if (userAccount.length() < 4) {
            return -1;
        }
        if (userPassword.length() < 8 || checkpassword.length() < 8) {
            return -1;
        }
        //校验账户不能包含特殊字符
        String validRule = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%…… &*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validRule).matcher(userAccount);
        // 如果包含非法字符,则返回
        if(matcher.find()){
            return -1;
        }
        //账户不能重复
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }
        //校验输入密码与user密码是否相同
        if (userPassword != checkpassword) {
            return -1;
        }
        //对密码进行加密
        String encodepassword = DigestUtils.md5DigestAsHex((stal + userPassword).getBytes());
        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encodepassword);
        boolean save = this.save(user);
        if (!save) {
            return -1;
        }
        return user.getId();
    }

    @Override
    public User dologin(String userAccount, String userPassword) {
        //1.校验
        if (StringUtils.isAnyEmpty(userAccount,userPassword)) {
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 8 ) {
            return null;
        }
        //校验账户不能包含特殊字符
        String validRule = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%…… &*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validRule).matcher(userAccount);
        // 如果包含非法字符,则返回
        if(matcher.find()){
            return null;
        }
        //对密码进行加密
        String encodepassword = DigestUtils.md5DigestAsHex((stal + userPassword).getBytes());
        //查询用户是否存在
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encodepassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("用户密码不匹配");
            return null;
        }
        //记录用户的登录态

        return user;
    }
}




