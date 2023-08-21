package com.lanya.controller;

import java.util.concurrent.TimeUnit;

import com.lanya.dao.vo.DeptVO;
import com.pig4cloud.plugin.idempotent.annotation.Idempotent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 幂等接口尝试
 * 参考文档：https://www.cnblogs.com/guangwenyin/p/15689210.html
 * 参考链接：https://github.com/pig-mesh/idempotent-spring-boot-starter
 * @author 兰崖 shenkaixing.skx
 * @date 2023/8/17 5:51 下午
 */
@RestController
public class IdempotentController {
    /**
     * 幂等操作的唯一标识，使用spring el表达式 用#来引用方法参数 。 可为空则取 当前 url + args 做表示
     * @param deptVO
     * @return
     */
    @Idempotent(key = "#deptVO.name", expireTime = 30,timeUnit = TimeUnit.MINUTES ,info = "部门重复")
    @PostMapping("/ide/test")
    public String test(@RequestBody DeptVO deptVO) {
        return deptVO.getName();
    }
}
