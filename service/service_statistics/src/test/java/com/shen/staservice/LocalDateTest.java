package com.shen.staservice;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

/**
 * @Author: shenge
 * @Date: 2020-05-25 13:43
 */
public class LocalDateTest {

    @Test
    public void test1(){
        String format1 = LocalDateTime.now().withDayOfMonth(LocalDateTime.now().getDayOfMonth()-1).format(ISO_LOCAL_DATE);
        System.out.println(format1);
    }
}
