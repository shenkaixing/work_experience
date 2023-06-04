package com.lanya.util;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 *
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
     *
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

    public static void main(String[] args) throws InterruptedException {
        PrintTask printTask = new PrintTask(0, 300);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(printTask);
        forkJoinPool.awaitTermination(2, TimeUnit.SECONDS);
        forkJoinPool.shutdown();
    }

    static class PrintTask extends RecursiveAction {

        private int start = 0;
        private int end = 300;
        private static final int threadhold = 50;

        public PrintTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start < threadhold) {
                for (int i = start; i < end; i++) {
                    System.out.println(i + "");
                }
            } else {
                int mid = (end + start) / 2;
                PrintTask left = new PrintTask(start, mid);
                PrintTask right = new PrintTask(mid, end);
                left.fork();
                right.fork();
            }
        }
    }
}