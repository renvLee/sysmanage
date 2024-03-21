package com.xh.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.entity.Menu;
import com.xh.system.vo.MenuVO;

import java.util.List;


/**
 * 权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2024-01-25 13:39:28
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    void updateMenu(Menu menu);

    List<Menu> queryMenu(String menuName, String status);

    void addMenu(Menu menu);

    int deleteMenu(Long id);
}
