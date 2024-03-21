package com.xh.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.entity.Menu;
import com.xh.entity.Role;

import java.util.List;


/**
 * 角色表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-25 13:57:54
 */
public interface RoleMapper extends BaseMapper<Role> {
    //查询普通用户的角色权限
    List<String> selectRoleKeyByOtherUserId(Long userId);
    //修改用户-①根据id查询用户信息
    List<Long> selectRoleIdByUserId(Long userId);
}