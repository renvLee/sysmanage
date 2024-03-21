package com.xh.system.controller;

import com.xh.entity.Menu;
import com.xh.security.result.ResponseResult;
import com.xh.system.service.MenuService;
import com.xh.system.vo.MenuVO;
import com.xh.system.vo.RoutersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    MenuService menuService;

    @GetMapping("/query")
    public ResponseResult<RoutersVO> queryMenu(
            @RequestParam(name ="name" ) String menuName,
            @RequestParam("status") String status) {
        System.out.println(menuName+"   "+status);
       ;
        return ResponseResult.success("菜单查询成功",new RoutersVO(menuService.queryMenu(menuName,status)));
    }
    @PutMapping("/update")
    public ResponseResult<Void> updateMenu(@RequestBody Menu menu) {
        menuService.updateMenu(menu);
        System.out.println(menu);
        return ResponseResult.success();
    }
    @PostMapping("/add")
    public ResponseResult<Void> addMenu(@RequestBody Menu menu) {
        menuService.addMenu(menu);
        return ResponseResult.success();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseResult<Void> deleteMenu(@PathVariable Long id) {
       int count=menuService.deleteMenu(id);
        return count==1?ResponseResult.success():ResponseResult.fail();
    }
}
