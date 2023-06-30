package com.lanya;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DiyLearnApplicationTests {

    @Test
    void contextLoads() {
    }

    private void load(int age, String name, long birthday, boolean sex) {
        System.out.println(age + name + birthday + sex);
    }


}
