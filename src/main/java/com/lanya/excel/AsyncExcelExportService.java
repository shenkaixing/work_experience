package com.lanya.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncExcelExportService {

    /**
     * 在Spring Boot中，可以使用Java的多线程或异步方式来处理大数据量的Excel导出，以减少响应时间。
     *
     * 下面是一个使用Spring Boot异步方式处理大数据量Excel导出的示例：
     *
     * 首先，创建一个异步的Service类，用于导出Excel文件
     *
     * 我们使用了Spring Boot的异步注解@Async，将exportExcel方法标记为异步方法。我们首先创建数据列表，然后使用EasyExcel库将数据写入Excel文件。
     * 最后，我们使用Java 8的CompletableFuture将结果包裹起来，并返回一个异步结果。
     * @return
     */
    @Async
    public CompletableFuture<Boolean> exportExcel() {
        List<List<Object>> data = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            List<Object> row = new ArrayList<>();
            row.add(i);
            row.add("Name " + i);
            row.add("Address " + i);
            data.add(row);
        }
        String fileName = "example.xlsx";
        String sheetName = "Sheet1";
        ExcelWriterBuilder writerBuilder = EasyExcel.write(fileName);
        ExcelWriter excelWriter = writerBuilder.build();
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
        excelWriter.write(data, writeSheet);
        excelWriter.finish();

        return CompletableFuture.completedFuture(true);
    }
}
