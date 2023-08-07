package com.lanya.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author EalenXie create on 2020/11/24 14:45
 * 自定义认证中心账号表
 */
@Data
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private int id;

    /**
     * 客户端id
     */
    @TableField("code")
    private String code;

    /**
     * 账号名
     */
    @TableField("name")
    private String name;



}
