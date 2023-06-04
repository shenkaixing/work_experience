package com.lanya.util;

import java.io.*;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 将 Map<K,V> 压缩成ZIP 流并生成本地文件
 */
public class ZipUtils {

    /**
     * 压缩工具类
     *
     * @param byteList 文件字节码Map，k:fileName，v：byte[]
     * @return 返回压缩流
     */

    /**
     *
     * @param byteList     文件字节码Map，k:fileName，v：byte[]
     * @param zipFilePath  ZIP 生成目录
     * @param zipFileName  ZIP 文件名
     */
    public static void zipFileSteam(Map<String, byte[]> byteList,String zipFilePath,String zipFileName) {
        long timeMillis = System.currentTimeMillis();
        System.out.println("开始生成 ZIP 开始时间为：" + timeMillis);
        //如果文件夹不存在就创建文件夹，防止报错
        File file = new File(zipFilePath);
        if (!file.exists() && !file.isDirectory()) {
            System.out.println("文件夹不存在,创建新文件夹!");
            file.mkdirs();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(String.format("%s%s%s", zipFilePath, zipFileName, ".zip"));
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            byteList.forEach((k, v) -> {
                //写入一个条目，我们需要给这个条目起个名字，相当于起一个文件名称
                try {
                    zipOutputStream.putNextEntry(new ZipEntry(k));
                    zipOutputStream.write(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            //关闭条目
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            System.out.println("结束生成 ZIP 结束时间为：" + (System.currentTimeMillis() - timeMillis));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将文件转换成byte数组
     *
     * @param filePath 文件File类 通过new File(文件路径)
     * @return byte数组
     */
    public static byte[] File2byte(File filePath) {
        byte[] buffer = null;
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            fis = new FileInputStream(filePath);
            bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer;
    }


}
