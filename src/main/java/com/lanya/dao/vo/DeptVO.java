package com.lanya.dao.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author shenkaixing create on 2020/11/24 14:45
 * 部门表
 */
@Data
public class DeptVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 部门ID
     */
    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 子部门
     */
    private List<DeptVO> deptList;



}
