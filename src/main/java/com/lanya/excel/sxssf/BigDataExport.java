package com.lanya.excel.sxssf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import com.lanya.util.ZipUtils;

public class BigDataExport {

    public static void main(String[] args) {
        int size = 70__0000;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(1);
        }
        //生成 excel 文件名
        String title = "GLOOX-SCATTER-CHART_SXSSFW_";
        //生成 ZIP 目录
        String zipFilePath = "/Users/shenkaixing/Downloads/";
        //生成 ZIP 文件名
        String zipFileName = "ZIP";
        long timeMillis = System.currentTimeMillis();
        System.out.println("开始 [导出Excel+打包ZIP] 时间为：" + timeMillis);
        bigDataExport(list, title, zipFilePath, zipFileName);
        System.out.println("结束 [导出Excel+打包ZIP] 时间为：" + (System.currentTimeMillis() - timeMillis));

    }

    /**
     * 默认一个文件 2W
     */
    public static final int BIG_EXCEL_ROWS = 2__0000;

    public static <E> void bigDataExport(List<E> list, String titleSerialN, String zipFilePath, String zipFileName) {
        long timeMillis = System.currentTimeMillis();
        System.out.println("开始 导出excel 时间为：" + timeMillis);
        Map<String, byte[]> byteList = new HashMap<>();
        //大于多少行 进行多线程操作
        if (list.size() > BIG_EXCEL_ROWS) {
            //页数
            int pageNum = list.size() / BIG_EXCEL_ROWS;
            //取余
            int lastCount = list.size() % BIG_EXCEL_ROWS;
            // 计算几页
            int page = lastCount == 0 ? pageNum : pageNum + 1;
            //倒计时锁
            CountDownLatch downLatch = new CountDownLatch(page);
            //定义线程池 按 page 设置线程池量
            int processor = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(page);
            List<E> subList;
            for (int c = 0; c <= pageNum; c++) {
                int rowNum = BIG_EXCEL_ROWS;
                String title = titleSerialN + "_" + (c + 1) + ".xlsx";
                if (c == pageNum) {
                    if (lastCount == 0) {
                        continue;
                    }
                    subList = list.subList(c * rowNum, c * rowNum + lastCount);
                } else {
                    subList = list.subList(c * rowNum, (c + 1) * rowNum);
                }
                //动态生成文件名：
                executor.execute(new PageThreadPool(downLatch, title, subList, byteList));
            }
            try {
                downLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executor.shutdown();
        }
        System.out.println("结束 导出excel 时间为：" + (System.currentTimeMillis() - timeMillis));
        if (byteList != null) {
            ZipUtils.zipFileSteam(byteList, zipFilePath, zipFileName);
        }
    }
}
