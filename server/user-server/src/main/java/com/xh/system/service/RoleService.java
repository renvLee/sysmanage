package com.xh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.entity.Role;

import java.util.List;


/**
 * 角色表(Role)表服务接口
 *
 * @author makejava
 * @since 2024-01-25 13:57:54
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}
