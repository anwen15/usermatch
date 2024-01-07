package com.example.usercenter.service;

import com.example.usermatch.model.domain.User;
import com.example.usermatch.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class UserServiceTest {
    @Resource
    UserService userService;
    @Test
    public void testinertuser() {
        User user = new User();
        user.setUsername("anwen");
        user.setUserAccount("123");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setUserPassword("");
        user.setPhone("");
        user.setEmail("");
        user.setPlanetCode("");
        boolean save = userService.save(user);
        System.out.println(user.getId());
        assertTrue(save);
    }
/**
    @Test
    void userRegister() {
        String usercaaount = "anwen";
        String userpassword = "";
        String checkpassword= "123";
        long register = userService.userRegister(usercaaount, userpassword, checkpassword,planetCode);
        Assertions.assertEquals(-1,register);
    }
    */
}