package com.lanya.controller;

import javax.annotation.Resource;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.lanya.dao.entity.OauthAccount;
import com.lanya.dao.entity.User;
import com.lanya.dao.mapper.OauthAccountMapper;
import com.lanya.dao.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 多数据源测试
 *
 * @author 兰崖 shenkaixing.skx
 * @date 2023/8/7 11:18 下午
 */
@RestController
public class DsController {

    @Resource
    private OauthAccountMapper oauthAccountMapper;

    @Resource
    private UserMapper userMapper;

    @GetMapping("/db/test/userInfo")
    //@DS("test")
    public User getUserInfo(@RequestParam(value = "code") String code) {
        User user = userMapper.loadUserByCode(code);
        return user;
    }

    @GetMapping("/db/testMaster/accountInfo")
    //@DS("testMaster")
    public OauthAccount getAccountInfo(@RequestParam(value = "clientId") String clientId,
        @RequestParam(value = "userName") String userName) {
        OauthAccount oauthAccount = oauthAccountMapper.loadUserByUsername(clientId, userName);
        return oauthAccount;
    }

}
