package com.lanya.dao.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanya.dao.entity.OauthAccount;
import org.apache.ibatis.annotations.Param;

/**
 * @author EalenXie create on 2020/11/24 15:16
 */
@DS("testMaster")
public interface OauthAccountMapper extends BaseMapper<OauthAccount> {

    /**
     * 获取客户端用户信息
     *
     * @param clientId 客户端Id
     * @param username 用户名
     * @return 用户对象
     */
    OauthAccount loadUserByUsername(@Param("clientId") String clientId, @Param("username") String username);

}
