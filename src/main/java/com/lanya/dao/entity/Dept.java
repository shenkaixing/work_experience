package com.lanya.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author shenkaixing create on 2020/11/24 14:45
 * 部门表
 */
@Data
@TableName("dept")
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 部门ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 部门名称
     */
    @TableField("name")
    private String name;

    /**
     * 父id
     */
    @TableField("parent_id")
    private Long parentId;



}
