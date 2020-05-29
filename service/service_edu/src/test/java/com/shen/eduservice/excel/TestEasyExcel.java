package com.shen.eduservice.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: shenge
 * @Date: 2020-05-10 11:17
 */
public class TestEasyExcel {

    @Test
    public void write() {
        String fileName = "/Users/sxy/java_project/1.xlsx";

        //调用easyExcel的方法实现写操作。
        EasyExcel.write(fileName, DataExcel.class).sheet("模板").doWrite(getList());
    }

    private List<DataExcel> getList() {
        List<DataExcel> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DataExcel dataExcel = new DataExcel();
            dataExcel.setId(i);
            dataExcel.setName(UUID.randomUUID().toString().substring(5, 10));
            list.add(dataExcel);
        }
        return list;
    }

    @Test
    public void read() {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
        String fileName = "/Users/sxy/java_project/1.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        DemoDataListener readListener = new DemoDataListener();
        EasyExcel.read(fileName, DataExcel.class, readListener).sheet().doRead();
        System.out.println(readListener.getMap());
        System.out.println(readListener.getList());
    }

}
