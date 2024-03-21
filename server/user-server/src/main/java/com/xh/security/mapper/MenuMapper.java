package com.xh.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.entity.Menu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//BaseMapper是mybatisplus官方提供的接口，里面提供了很多单表查询的方法
public interface MenuMapper extends BaseMapper<Menu> {
    //由于是多表联查，mybatisplus的BaseMapper接口没有提供，我们需要自定义方法，所以需要创建对应的mapper文件，定义对应的sql语句
    List<String> selectPermsByUserId(Long userid);

    List<String> selectPermsByOtherUserId(Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectOtherRouterMenuTreeByUserId(Long userId);

    int updateMenuById(Menu menu);
}