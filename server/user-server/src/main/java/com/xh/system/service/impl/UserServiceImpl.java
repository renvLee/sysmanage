package com.xh.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.entity.Menu;
import com.xh.entity.User;
import com.xh.security.entity.LoginUser;
import com.xh.security.mapper.UserMapper;
import com.xh.security.result.ResponseResult;
import com.xh.system.service.MenuService;
import com.xh.system.service.RoleService;
import com.xh.system.service.UserService;
import com.xh.system.utils.BeanCopyUtils;
import com.xh.system.utils.SecurityUtils;
import com.xh.system.vo.AdminUserInfoVO;
import com.xh.system.vo.RoutersVO;
import com.xh.system.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult<AdminUserInfoVO> getInfo() {
        //获取当前登录的用户。
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getXxuser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getXxuser().getId());
        //获取用户信息
        User user = loginUser.getXxuser();
        //BeanCopyUtils是我们在utils包中写的类
        UserInfoVO userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVO.class);

        //封装响应返回
        AdminUserInfoVO adminUserInfoVo = new AdminUserInfoVO(perms, roleKeyList, userInfoVo);
        return ResponseResult.success("用户信息获取成功", adminUserInfoVo);
    }

    @Override
    public ResponseResult<RoutersVO> getRouters() {

        //获取用户id
        Long userId = SecurityUtils.getUserId();

        //根据用户id来查询menu(权限菜单)。要求查询结果是tree的形式，也就是子父菜单树
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装响应返回
        return ResponseResult.success("获取菜单路由成功",new RoutersVO(menus));
    }
}
