package com.xh.system.service;

import com.xh.security.result.ResponseResult;
import com.xh.system.vo.AdminUserInfoVO;
import com.xh.system.vo.RoutersVO;

public interface UserService {
    ResponseResult<AdminUserInfoVO> getInfo();

    ResponseResult<RoutersVO> getRouters();
}
