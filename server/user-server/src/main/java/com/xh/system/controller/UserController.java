package com.xh.system.controller;

import com.xh.entity.Menu;
import com.xh.security.result.ResponseResult;
import com.xh.system.service.UserService;
import com.xh.system.utils.SecurityUtils;
import com.xh.system.vo.AdminUserInfoVO;
import com.xh.system.vo.RoutersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /*获取用户信息和权限信息*/
    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVO> getInfo() {
        return userService.getInfo();
    }

    /*查询路由信息*/
    @GetMapping("/getRouters")
    //RoutersVo是我们在vo包下写的类
    public ResponseResult<RoutersVO> getRouters() {
        return userService.getRouters();
    }

}
