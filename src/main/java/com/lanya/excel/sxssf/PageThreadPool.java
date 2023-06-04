package com.lanya.excel.sxssf;

import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;


/**
 * 导出new线程池工具类
 */
public class PageThreadPool<E> implements Runnable {

    private CountDownLatch countDownLatch;
    private String title;
    private Map<String, byte[]> byteList;
    private List<E> list;

    public PageThreadPool(CountDownLatch countDownLatch, String title, List<E> list, Map<String, byte[]> byteList) {
        this.countDownLatch = countDownLatch;
        this.title = title;
        this.list = list;
        this.byteList = byteList;
    }

    @Override
    public void run() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        SXSSFWorkbook workbook = null;
        try {
            //默认100行，超100行将写入临时文件
            workbook = new SXSSFWorkbook();
            //压缩临时文件，很重要，否则磁盘很快就会被写满
            workbook.setCompressTempFiles(true);
            SXSSFSheet sheet = workbook.createSheet("sheet");
            for (int j = 0; j < list.size(); j++) {
                SXSSFRow row = sheet.createRow(j);
                for (int k = 0; k < 10; k++) {
                    row.createCell(k).setCellValue(new Random().nextInt(10));
                }
            }
            workbook.write(bos);
            //put 文件名和文件字节数组
            byteList.put(this.title, bos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                // 删除临时文件，很重要，否则磁盘可能会被写满
                if (workbook != null) {
                    workbook.dispose();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }
}
