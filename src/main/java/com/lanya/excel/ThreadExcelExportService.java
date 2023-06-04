package com.lanya.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ThreadExcelExportService {

    @Autowired
    private ApplicationContext context;

    private ExecutorService executors = new ThreadPoolExecutor(8, 10,
        0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
        new ThreadFactoryBuilder().setNameFormat("export-thread").build());

    /**
     * 我们使用了Java的ExecutorService来创建线程池，并将数据分成多个批次，每个批次分配一个线程进行写入。同时，我们使用了Spring
     * Boot的applicationContext来获取ExcelThread对象，并将数据、ExcelWriter和WriteSheet对象传递给ExcelThread的构造函数。
     * 万万没想到 EasyExcel 不支持多线程write
     * @param response
     * @throws IOException
     */
    public void exportExcel(HttpServletResponse response) throws IOException, InterruptedException {
        List<List<Object>> data = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            List<Object> row = new ArrayList<>();
            row.add(i);
            row.add("Name " + i);
            row.add("Address " + i);
            data.add(row);
        }
        ServletOutputStream outputStream = response.getOutputStream();
        // 创建ExcelWriter对象
        ExcelWriter excelWriter = EasyExcel.write().excelType(ExcelTypeEnum.XLSX).autoCloseStream(true).file(outputStream).build();


        // 每10000条记录开启一个线程进行写入
        int batchSize = 1000;
        int total = data.size();
        int batchCount = (total + batchSize - 1) / batchSize;
        // 线程同步计数器
        CountDownLatch downLatch = new CountDownLatch(batchCount);

        for (int i = 0; i < batchCount; i++) {
            int startIndex = i * batchSize;
            int endIndex = Math.min(startIndex + batchSize, total);

            // 创建ExcelThread对象
            //ExcelThread excelThread = new ExcelThread();
            //excelThread.setData(data.subList(startIndex, endIndex));
            // 创建WriteSheet对象
            //WriteSheet writeSheet = EasyExcel.writerSheet(i,"Sheet_"+i).build();
            WriteSheet writeSheet = new WriteSheet();
            writeSheet.setSheetName("i"+i);
            writeSheet.setSheetNo(i);
            //
            //excelThread.setWriteSheet(writeSheet);
            //excelThread.setExcelWriter(excelWriter);
            //excelThread.setCountDownLatch(downLatch);

            excelWriter.write(data.subList(startIndex, endIndex), writeSheet);
            // 启动线程
            //executors.submit(excelThread);
        }
        log.info("等待所有线程写完数据....");
        downLatch.await(2,TimeUnit.MINUTES);

        // 写入Excel文件
        excelWriter.finish();


    }
}
