package com.lanya.excel.sxssf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lanya.excel.ExcelThread;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MultiThreadExcelExportService {

    @Autowired
    private ApplicationContext context;

    private ExecutorService executors = new ThreadPoolExecutor(8, 10,
        0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
        new ThreadFactoryBuilder().setNameFormat("export-thread").build());

    /**
     *
     * @throws IOException
     */
    public void exportExcel(HttpServletResponse response) throws IOException, InterruptedException {
        List<List<String>> data = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(i));
            row.add("Name " + i);
            row.add("Address " + i);
            data.add(row);
        }
        ServletOutputStream outputStream = response.getOutputStream();
        //默认100行，超100行将写入临时文件
        SXSSFWorkbook workbook = null;
        try {
            workbook = new SXSSFWorkbook();
            //压缩临时文件，很重要，否则磁盘很快就会被写满
            workbook.setCompressTempFiles(true);

            // 每10000条记录开启一个线程进行写入
            int batchSize = 100000;
            int total = data.size();
            int batchCount = (total + batchSize - 1) / batchSize;
            // 线程同步计数器
            CountDownLatch downLatch = new CountDownLatch(batchCount);

            for (int i = 0; i < batchCount; i++) {
                int startIndex = i * batchSize;
                int endIndex = Math.min(startIndex + batchSize, total);
                SXSSFSheet workbookSheet = workbook.createSheet("Sheet_"+i);
                // 创建ExcelThread对象
                ExcelThread excelThread = new ExcelThread();
                excelThread.setData(data.subList(startIndex, endIndex));
                excelThread.setSxssfSheet(workbookSheet);
                excelThread.setTitle("i"+i);
                excelThread.setCountDownLatch(downLatch);
                // 启动线程
                executors.submit(excelThread);
            }
            log.info("等待所有线程写完数据....");
            downLatch.await(2,TimeUnit.MINUTES);
            workbook.write(outputStream);
        } catch (InterruptedException e) {
            log.error("exportExcel.err",e);
        } finally {
            workbook.dispose();
            workbook.close();
        }

    }
}
