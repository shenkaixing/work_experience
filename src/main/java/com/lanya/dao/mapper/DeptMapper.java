package com.lanya.dao.mapper;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanya.dao.entity.Dept;
import com.lanya.dao.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author EalenXie create on 2020/11/24 15:16
 */
@DS("test")
public interface DeptMapper extends BaseMapper<Dept> {


    List<Dept> selectByParentId(@Param("parentId") Long parentId);

}
