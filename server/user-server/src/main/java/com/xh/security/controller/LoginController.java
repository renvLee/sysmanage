package com.xh.security.controller;

import com.xh.entity.User;
import com.xh.security.result.ResponseResult;
import com.xh.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    //LoginService是我们在service目录写好的接口
    private LoginService loginService;

    @PostMapping("/user/login")
    //RequestBody 参数是放在请求体中带出来的
    public ResponseResult xxlogin(@RequestBody User user){
        System.out.println(user);
        //登录
        return loginService.login(user);
    }
    @RequestMapping("/user/logout")
    //ResponseResult是我们在domain目录写好的实体类
    public ResponseResult xxlogout(){
        return loginService.logout();
    }
}