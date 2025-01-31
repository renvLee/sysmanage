package com.xh.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.entity.Role;
import com.xh.system.mapper.RoleMapper;
import com.xh.system.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色表(Role)表服务实现类
 *
 * @author makejava
 * @since 2024-01-25 13:57:54
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    //查询用户的角色信息
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员，如果是，就返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }

        //否则查询对应普通用户所具有的的角色信息
        List<String> otherRole = getBaseMapper().selectRoleKeyByOtherUserId(id);

        return otherRole;
    }
}
