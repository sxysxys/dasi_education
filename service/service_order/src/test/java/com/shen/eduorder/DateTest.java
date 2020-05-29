package com.shen.eduorder;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: shenge
 * @Date: 2020-05-23 20:43
 */
public class DateTest {
    @Test
    public void testDate(){
        String dateString="20141030133525";
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            Date parse= sd.parse(dateString);
            System.out.println(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
