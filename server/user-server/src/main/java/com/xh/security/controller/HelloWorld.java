package com.xh.security.controller;

import com.xh.security.result.ResponseResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @RequestMapping("/hello")
    @PreAuthorize("@CustomExpression.hasAuthority('system:user:list')")
    public ResponseResult<String> hello() {
        return ResponseResult.success("Hello，权限认证通过");
    }
}
