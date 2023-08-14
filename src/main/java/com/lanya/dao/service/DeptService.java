package com.lanya.dao.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.lanya.dao.entity.Dept;
import com.lanya.dao.entity.OauthAccount;
import com.lanya.dao.entity.User;
import com.lanya.dao.mapper.DeptMapper;
import com.lanya.dao.mapper.OauthAccountMapper;
import com.lanya.dao.mapper.UserMapper;
import com.lanya.dao.vo.DeptVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 部门树测试
 *
 * @author 兰崖 shenkaixing.skx
 * @date 2023/8/7 11:18 下午
 */
@Service
@Slf4j
public class DeptService {

    @Resource
    private DeptMapper deptMapper;

    /**
     * 使用主数据源指定的数据库,test
     * @return
     */
    public List<DeptVO> buildDeptTree() {
        // 第一层级
        List<Dept> oneLevelList = deptMapper.selectByParentId(0L);
        List<DeptVO> oneDeptVOList = convert2DeptVO(oneLevelList);
        log.info("DeptService.getDeptTree.oneLevelList:{}", JSON.toJSONString(oneLevelList));
        // 查询所有数据
        List<Dept> deptList = deptMapper.selectList(null);
        List<DeptVO> deptVOList = convert2DeptVO(deptList);


        List<DeptVO> result = new ArrayList<>();
        for (DeptVO deptVO : oneDeptVOList) {
            List<DeptVO> subTree = getSubTree(deptVO.getId(), deptVOList);
            deptVO.setDeptList(subTree);
            result.add(deptVO);
        }
        return result;
    }


    private List<DeptVO> getSubTree(long deptId,List<DeptVO> allDeptVO){
        List<DeptVO> deptVOList = new ArrayList<>();
        for (DeptVO deptVO : allDeptVO) {
            if (deptId == deptVO.getParentId()) {
                deptVOList.add(deptVO);
            }
        }
        // 叶子节点
        if (CollectionUtils.isEmpty(deptVOList)) {
            return null;
        }

        for (DeptVO deptVO : deptVOList) {
            // 对后续节点做递归
            List<DeptVO> subTree = getSubTree(deptVO.getId(), allDeptVO);
            deptVO.setDeptList(subTree);
        }
        return deptVOList;
    }


    /**
     * 将DO转为VO
     * @param deptList
     * @return
     */
    private List<DeptVO> convert2DeptVO(List<Dept> deptList){
        List<DeptVO> result = new ArrayList<>();
        for (Dept dept : deptList) {
            DeptVO deptVO = new DeptVO();
            BeanUtils.copyProperties(dept,deptVO);
            result.add(deptVO);
        }
        return result;
    }



}
