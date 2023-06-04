package com.lanya.util;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;

public class ExcelUtils {
    public static void writeExcel(HttpServletResponse response, List<List<Object>> data) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        EasyExcel.write(outputStream).head(Collections.emptyList()).sheet().doWrite(data);
    }
}
