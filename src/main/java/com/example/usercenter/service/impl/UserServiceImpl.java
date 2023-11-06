package com.example.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usercenter.common.EoorCode;
import com.example.usercenter.excepttion.BusinessException;
import com.example.usercenter.model.domain.User;
import com.example.usercenter.service.UserService;
import com.example.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.usercenter.contant.UserContant.USER_LOGIN_STATE;

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
    private static final String STAL ="anwen";


    @Override
    public long userRegister(String userAccount, String userPassword, String checkpassword,String planetcode) {
        //1.校验
        if (StringUtils.isAnyEmpty(userAccount,userPassword,checkpassword)) {
            throw new BusinessException(EoorCode.PARAMS_ERROR,"用户名或密码不能为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(EoorCode.PARAMS_ERROR,"长度过小");
        }
        if (userPassword.length() < 8 || checkpassword.length() < 8) {
            throw new BusinessException(EoorCode.PARAMS_ERROR,"长度过小" );
        }
        if (planetcode.length() > 6) {
            throw new BusinessException(EoorCode.PARAMS_ERROR ,"长度过长");
        }
        //校验账户不能包含特殊字符
        String validRule = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%…… &*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validRule).matcher(userAccount);
        // 如果包含非法字符,则返回
        if(matcher.find()){
            throw new BusinessException(EoorCode.PARAMS_ERROR);
        }
        //账户不能重复
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }
        //星球编号
        queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("planetCode",planetcode);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }
        //校验输入密码与user密码是否相同
        if (!userPassword.equals(checkpassword)) {
            return -1;
        }
        //对密码进行加密
        String encodepassword = DigestUtils.md5DigestAsHex((STAL + userPassword).getBytes());
        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encodepassword);
        user.setPlanetCode(planetcode);
        boolean save = this.save(user);
        if (!save) {
            return -1;
        }
        return user.getId();
    }

    @Override
    public User dologin(String userAccount, String userPassword, HttpServletRequest request) {
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
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        // 如果包含非法字符,则返回
        if(matcher.find()){
            return null;
        }
        //对密码进行加密
        String encodepassword = DigestUtils.md5DigestAsHex((STAL + userPassword).getBytes());
        //查询用户是否存在
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encodepassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("用户密码不匹配");
            return null;
        }
        //用户数据脱敏
        User saftyuser = getSaftyUser(user);
        //记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, saftyuser);
        return saftyuser;
    }

    //用户脱敏
    @Override
    public User getSaftyUser(User user) {
        if (user == null) {
            return null;
        }
        User saftyuser = new User();
        saftyuser.setId(user.getId());
        saftyuser.setUsername(user.getUsername());
        saftyuser.setUserAccount(user.getUserAccount());
        saftyuser.setAvatarUrl(user.getAvatarUrl());
        saftyuser.setPhone(user.getPhone());
        saftyuser.setEmail(user.getEmail());
        saftyuser.setUserStatus(user.getUserStatus());
        saftyuser.setCreateTime(user.getCreateTime());
        saftyuser.setUpdateTime(user.getUpdateTime());
        saftyuser.setPlanetCode(user.getPlanetCode());
        saftyuser.setUserRole(user.getUserRole());
        return saftyuser;
    }

    @Override
    public int userlogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
}




