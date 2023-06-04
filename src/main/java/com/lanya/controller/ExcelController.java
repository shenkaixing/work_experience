package com.lanya.controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletResponse;

import com.lanya.excel.AsyncExcelExportService;
import com.lanya.excel.ThreadExcelExportService;
import com.lanya.excel.sxssf.MultiThreadExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * excel导出controller
 * @author 兰崖 shenkaixing.skx
 * @date 2023/6/1 8:32 下午
 */
@RestController
public class ExcelController {

    @Autowired
    private AsyncExcelExportService asyncExcelExportService;

    @Autowired
    private ThreadExcelExportService threadExcelExportService;

    @Autowired
    private MultiThreadExcelExportService multiThreadExcelExportService;

    /**
     * 在上面的代码中，我们调用异步的Service方法，并使用ResponseEntity将响应返回给客户端。这样做可以让客户端立即收到响应，而Excel文件的导出将在后台异步进行，以减少响应时间。
     *
     * 需要注意的是，在使用异步方式导出Excel文件时，需要使用ExcelWriter和WriteSheet等类来手动控制Excel文件的写入。同时，需要在主配置类上添加@EnableAsync注解，以启用Spring
     * Boot的异步支持。
     * @return
     */
    @GetMapping("/export")
    public ResponseEntity<String> exportExcel() {
        CompletableFuture<Boolean> result = asyncExcelExportService.exportExcel();
        return new ResponseEntity<>("Excel export started...", HttpStatus.OK);
    }

    /**
     * 我们设置响应的Content-Type、字符集和文件名，并调用ExcelExportService的exportExcel方法来导出Excel文件。
     *
     * 这样就可以使用多线程方式在Spring Boot中处理大数据量Excel导出了。需要注意的是，由于线程数不能无限制增多，
     * 因此需要根据数据量和计算机性能等因素来选择合适的线程数量。
     * @param response
     * @throws IOException
     */
    @GetMapping("/threadExport")
    public void threadExport(HttpServletResponse response) throws IOException, InterruptedException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=example.xlsx");
        threadExcelExportService.exportExcel(response);
    }

    @GetMapping("/multiThreadExport")
    public String multiThreadExport(HttpServletResponse response) throws IOException, InterruptedException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=example.xlsx");
        multiThreadExcelExportService.exportExcel(response);
        return "ok";
    }

}
