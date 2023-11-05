package com.example.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.usercenter.model.domain.User;
import com.example.usercenter.model.request.UserLoginRequest;
import com.example.usercenter.model.request.UserRegisterRequest;
import com.example.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.usercenter.contant.UserContant.ADMIN_ROLE;
import static com.example.usercenter.contant.UserContant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userpassword = userRegisterRequest.getUserPassword();
        String checkpassword = userRegisterRequest.getCheckPassword();
        String planetCode=userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyEmpty(checkpassword, userAccount, userpassword,planetCode)) {
            return null;
        }
        return userService.userRegister(userAccount, userpassword, checkpassword,planetCode);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userpassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyEmpty(userAccount, userpassword)) {
            return null;
        }

        return  userService.dologin(userAccount, userpassword, request);
    }
    @PostMapping("/logout")
    public Integer userLogout(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        return  userService.userlogout(request);
    }
    @GetMapping("/current")
    public User getCurrentUser(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentuser=(User)attribute;
        if (currentuser == null) {
            return null;
        }
        Long id = currentuser.getId();
        // TODO: 5/11/2023 校验用户是否合法 
        User byId = userService.getById(id);
        return userService.getSaftyUser(byId);
        
    }

    @GetMapping("/search")
    public List<User> searchUser(String username,HttpServletRequest request) {
        //管理员功能
        if (!isAdmin(request)) {
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        return userService.list(queryWrapper).stream().map(user -> userService.getSaftyUser(user)).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody long id,HttpServletRequest request) {
        if (!isAdmin(request)) {
            return false;
        }
        if (id <= 0) {
            return false;
        }
        return userService.removeById(id);
    }

    /**
     * 验证是否为管理员
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        //管理员功能
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user=(User) attribute;
        return user!=null&&user.getUserRole()==ADMIN_ROLE;
    }
}
