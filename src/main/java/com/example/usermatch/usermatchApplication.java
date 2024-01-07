package com.example.usermatch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com/example/usercenter/mapper")
public class usermatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(usermatchApplication.class, args);
    }

}
