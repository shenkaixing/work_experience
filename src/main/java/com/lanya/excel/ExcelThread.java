package com.lanya.excel;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;

/**
 * 我们在ExcelThread的run方法中调用ExcelWriter的write方法，将数据写入Excel文件。
 *
 * 最后，在Controller中调用ExcelExportService的exportExcel方法来导出Excel文件：
 */
@Slf4j
public class ExcelThread implements Runnable {

    private List<List<String>> data;
    private String title;
    private CountDownLatch countDownLatch;

    private SXSSFSheet sxssfSheet;

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setSxssfSheet(SXSSFSheet sxssfSheet) {
        this.sxssfSheet = sxssfSheet;
    }

    @Override
    public void run() {
        try {
            log.info("当前线程:{}，写入sheet:{}共{}条数据", Thread.currentThread().getName(), title, data.size());
            for (int j = 0; j < data.size(); j++) {
                SXSSFRow row = sxssfSheet.createRow(j);
                for (int k = 0; k < 3; k++) {
                    row.createCell(k).setCellValue(data.get(j).get(k));
                }
            }

        } catch (Exception e) {
            log.error("ExcelThread.err", e);
        } finally {
            countDownLatch.countDown();
        }
    }

}
