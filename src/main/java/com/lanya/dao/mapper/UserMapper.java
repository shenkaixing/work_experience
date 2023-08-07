package com.lanya.dao.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanya.dao.entity.OauthAccount;
import com.lanya.dao.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author EalenXie create on 2020/11/24 15:16
 */
@DS("test")
public interface UserMapper extends BaseMapper<User> {

    /**
     *
     * @param code
     * @return 用户对象
     */
    User loadUserByCode(@Param("code") String code);

}
