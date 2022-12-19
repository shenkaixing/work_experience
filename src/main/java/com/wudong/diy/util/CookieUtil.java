package com.wudong.diy.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 * @author lanya 2022/12/12  1:44 PM
 */
public class CookieUtil {
    /**
     * 默认cookie路径
     */
    private static final String PATH = "/";

    private static final Boolean HTTP_ONLY = true;

    /**
     * 设置cookie
     *
     * @param response
     * @param name     cookie名字
     * @param value    cookie值
     * @param maxAge   cookie生命周期 以秒为单位
     */
    public static void addCookie(HttpServletResponse response, String domain, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setPath(PATH);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(HTTP_ONLY);
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     * @param response
     * @param cookieName
     * @param domain
     */
    public static void deleteCookie(HttpServletResponse response, String cookieName, String domain) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setMaxAge(0);
        cookie.setPath(PATH);
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }
}