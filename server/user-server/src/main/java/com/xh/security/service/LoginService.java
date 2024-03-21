package com.xh.security.service;

import com.xh.entity.User;
import com.xh.security.result.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}