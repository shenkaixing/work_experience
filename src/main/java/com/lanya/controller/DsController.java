package com.lanya.controller;

import com.alibaba.fastjson.JSON;

import com.lanya.dao.entity.OauthAccount;
import com.lanya.dao.entity.User;
import com.lanya.dao.service.OauthUserDsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class DsController {

    @Autowired
    private OauthUserDsService oauthUserDsService;
    /**
     * http://localhost:8089/db/test/userInfo?code=lanya
     * @param code
     * @return
     */
    @GetMapping("/db/test/userInfo")
    public User getUserInfo(@RequestParam(value = "code") String code) {
        User user = oauthUserDsService.getUserInfo(code);
        log.info("DsController.getUserInfo:{}", JSON.toJSONString(user));
        return user;
    }

    /**
     * http://localhost:8089/db/testMaster/accountInfo?userName=lanya&clientId=ABC
     * @param clientId
     * @param userName
     * @return
     */
    @GetMapping("/db/testMaster/accountInfo")
    public OauthAccount getAccountInfo(@RequestParam(value = "clientId") String clientId,
        @RequestParam(value = "userName") String userName) {
        OauthAccount oauthAccount = oauthUserDsService.getAccountInfo(clientId, userName);
        log.info("DsController.getUserInfo:{}", JSON.toJSONString(oauthAccount));
        return oauthAccount;
    }

}
