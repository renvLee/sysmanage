package com.xh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication //把这个类标识为引导类(也叫启动类)
@MapperScan(basePackages={"com.xh.security.mapper","com.xh.system.mapper"})
public class UserManageApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(UserManageApplication.class, args);
        System.out.println("*********用户管理模块启动成功**********");
    }
}
