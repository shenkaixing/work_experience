package com.wudong.diy.spirng.mvc.globalhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorEnum {

    //系统相关 start
    SYSTEM_BUSY(-1, "系统繁忙"),
    UNKNOWN_HOST(-2, "域名请求异常"),
    NO_HTTP_RESPONSE(-3, "请求响应超时");

    private Integer code;
    private String msg;
    }
