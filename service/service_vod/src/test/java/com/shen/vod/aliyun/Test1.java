package com.shen.vod.aliyun;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: shenge
 * @Date: 2020-05-14 15:53
 * <p>
 * 这个类也会去加载相应的com.shen下的类，因为VodApplication配置了包扫描。
 */
@SpringBootTest
public class Test1 {
    @Test
    public void sum() {
        System.out.println("hah");
    }
}
