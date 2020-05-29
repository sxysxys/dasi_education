package com.shen.oss;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @Author: shenge
 * @Date: 2020-05-09 15:09
 */
public class UUIDTest {

    @Test
    public void UUIDGenerator() {
        System.out.println(UUID.randomUUID().toString());
    }

    @Test
    public void TimeTest() {

        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
    }
}
