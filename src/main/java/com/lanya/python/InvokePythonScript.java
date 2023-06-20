package com.lanya.python;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 在java中调用python脚本
 *
 * @author 兰崖 shenkaixing.skx
 * @date 2023/6/19 7:09 下午
 */
public class InvokePythonScript {

    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        int c = 3;

        try {
            String[] my_args = new String[] {"python3", "src/main/java/com/lanya/python/test.py", String.valueOf(a), String.valueOf(b), String.valueOf(
                c)};
            //执行脚本
            Process proc = Runtime.getRuntime().exec(my_args);

            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
            proc.waitFor();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
