package com.xh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 权限表(Menu)表实体类
 *
 * @author makejava
 * @since 2024-01-25 13:39:27
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_menu")
@Accessors(chain = true)//让set()方法有返回值
public class Menu {
    @TableId(type = IdType.AUTO)
    private Long id;

    //菜单名
    private String menuName;
    //路由地址
    private String path;
    //组件路径
    private String component;
    //菜单状态（0显示 1隐藏）
    private String visible;
    //菜单状态（0正常 1停用）
    private String status;
    //权限标识
    private String perms;
    //菜单图标
    private String icon;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;
    //是否删除（0未删除 1已删除）
    private Integer delFlag;
    //备注
    private String remark;
    //父菜单id
    private Long parentId;
    //显示顺序
    private Integer orderNum;
    //菜单类型（M目录 C菜单 F按钮）
    private String menuType;


    @TableField(exist = false)
    private List<Menu> children;
}