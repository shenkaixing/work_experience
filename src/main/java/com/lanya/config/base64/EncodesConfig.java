package com.lanya.config.base64;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 加密配置中心
 *
 * @author 兰崖 shenkaixing.skx
 * @date 2022/8/1 1:38 下午
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EncodesConfig {
    /**
     * 需要经过keycenter加密的属性
     */
    private List<String> cryptoProperties;

}
