package com.xh.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.entity.Menu;
import com.xh.constants.SystemConstants;
import com.xh.security.mapper.MenuMapper;
import com.xh.system.utils.BeanCopyUtils;
import com.xh.system.utils.SecurityUtils;
import com.xh.system.vo.MenuVO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.xh.system.service.MenuService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2024-01-25 13:39:28
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    /**
     * 查询用户的权限信息
     *
     * @param id
     * @return
     */
    @Override
    public List<String> selectPermsByUserId(Long id) {
        //根据用户id查询用户的权限信息。用户id为1代表管理员，如果是管理员就返回所有的权限
        if (id == 1L) {
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            //查询条件是permissions中需要有所有菜单类型为C或者F的权限。SystemCanstants是我们在huanf-framework工程写的类
            wrapper.in(Menu::getMenuType, SystemConstants.TYPE_MENU, SystemConstants.TYPE_BUTTON);
            //查询条件是permissions中需要有状态为正常的权限。SystemCanstants是我们在huanf-framework工程写的类
            wrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            //查询条件是permissions中需要未被删除的权限的权限
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .distinct() // 使用distinct()方法去除重复的权限
                    .collect(Collectors.toList());
            return perms;
        }

        //如果不是管理员就返回对应用户所具有的权限
        List<String> otherPerms = getBaseMapper().selectPermsByOtherUserId(id);
        return otherPerms;
    }

    /**
     * 查询用户的路由信息(权限菜单)
     *
     * @param userId
     * @return
     */
    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {

        MenuMapper menuMapper = getBaseMapper();

        List<Menu> menus = null;

        //判断是否是超级管理员，用户id为id代表超级管理员，如果是就返回所有符合要求的权限菜单
        if (userId.equals(1L)) {
            menus = menuMapper.selectAllRouterMenu();
        } else {
            //如果不是超级管理员，就查询对应用户所具有的路由信息(权限菜单)
            menus = menuMapper.selectOtherRouterMenuTreeByUserId(userId);
        }

        //构建成tree，也就是子父菜单树，有层级关系
        //思路:先找出第一层的菜单，然后再找子菜单(也就是第二层)，把子菜单的结果赋值给Menu类的children字段
        List<Menu> menuTree = xxbuildMenuTree(menus, 0L);

        return menuTree;
    }

    /**
     * 更新菜单表
     *
     * @param menu
     */
    @Override
    public void updateMenu(Menu menu) {
        MenuMapper menuMapper = getBaseMapper();

        menu.setUpdateTime(new Date());
        if (menu.getParentId() == null) menu.setParentId(0L);
        menuMapper.updateMenuById(menu);
    }

    @Override
    public List<Menu> queryMenu(String menuName, String status) {
        //根据menuName模糊查询，status=0来查询菜单
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("menu_name", menuName);
        queryWrapper.eq("status", status);
        queryWrapper.orderByDesc("parent_id","order_num");

        List<Menu> menus = this.list(queryWrapper); // 使用list方法获取所有符合条件的记录
        List<Menu> menuTree=xxbuildMenuTree(menus,0L);
        return menuTree;
    }

    @Override
    public void addMenu(Menu menu) {
        if(menu.getPath().isEmpty())menu.setPath("null");
        if(menu.getComponent().isEmpty())menu.setComponent("null");
        Long userId = SecurityUtils.getUserId();

        menu.setCreateBy(userId);
        menu.setDelFlag(SystemConstants.DELETE_FLG);
        menu.setOrderNum(SystemConstants.ORDER_NUM);
        menu.setVisible(SystemConstants.VISIBLE);
        menu.setCreateTime(new Date());

        MenuMapper baseMapper = getBaseMapper();
        baseMapper.insert(menu);
    }

    @Override
    public int deleteMenu(Long id) {
        MenuMapper baseMapper = getBaseMapper();
        return baseMapper.deleteById(id);
    }

    /**
     * 用于把List集合里面的数据构建成tree，也就是子父菜单树，有层级关系
     *
     * @param menus
     * @param parentId
     * @return
     */
    private List<Menu> xxbuildMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                //过滤找出父菜单树，也就是第一层
                .filter(menu -> menu.getParentId().equals(parentId))
                //xxgetChildren是我们在下面写的方法，用于获取子菜单的List集合
                .map(menu -> menu.setChildren(xxgetChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 用于获取传入参数的子菜单，并封装为List集合返回
     *
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> xxgetChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                //通过过滤得到子菜单
                .filter(m -> m.getParentId().equals(menu.getId()))
                //如果有三层菜单的话，也就是子菜单的子菜单，我们就用下面那行递归(自己调用自己)来处理
                .map(m -> m.setChildren(xxgetChildren(m, menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}
