package com.xh.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2024-01-22 11:20:12
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User {
    //用户ID
    @TableId
    private Long id;

    //用户账号
    private String username;
    //用户密码
    private String password;
    //用户昵称
    private String nickname;
    //用户头像
    private String avatar;
    //用户邮箱
    private String email;
    //用户电话
    private String phonenum;
    //用户性别
    private Integer gender;
    //登陆类型（1 邮箱 2账号密码 3QQ）
    private Integer loginType;
    //最后登陆时间
    private Date lastLoginTime;
    //登陆ip
    private String loginIp;
    //登陆地区
    private String loginAddress;
    //账号状态（0正常 1禁用）
    private Integer isDisable;
    //删除标识（0正常 1删除）
    private Integer delFlag;
    //创建者
    private String createBy;
    //创建时间
    private Date createTime;
    //修改者
    private String updateBy;
    //修改时间
    private Date updateTime;
    //备注
    private String remark;
    
}
