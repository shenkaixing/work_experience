package com.lanya.dao.service;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.lanya.dao.entity.OauthAccount;
import com.lanya.dao.entity.User;
import com.lanya.dao.mapper.OauthAccountMapper;
import com.lanya.dao.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 多数据源测试
 *
 * @author 兰崖 shenkaixing.skx
 * @date 2023/8/7 11:18 下午
 */
@Service
@Slf4j
public class OauthUserDsService {

    @Resource
    private OauthAccountMapper oauthAccountMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * 使用事务时注意带上 DS，不然该事务会使用主数据源事务管理器
     * @param code
     * @return
     */
    @DS("test")
    @Transactional(rollbackFor = Exception.class)
    public User getUserInfo(String code) {
        User user = userMapper.loadUserByCode(code);
        log.info("OauthUserDsService.getUserInfo:{}", JSON.toJSONString(user));
        userMapper.deleteById(1);
        userMapper.insert(null);
        return user;
    }

    /**
     * @param clientId
     * @param userName
     * @return
     */
    @DS("testMaster")
    public OauthAccount getAccountInfo(String clientId, String userName) {
        OauthAccount oauthAccount = oauthAccountMapper.loadUserByUsername(clientId, userName);
        log.info("OauthUserDsService.getUserInfo:{}", JSON.toJSONString(oauthAccount));
        return oauthAccount;
    }

}
