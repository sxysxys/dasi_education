package com.shen.eduservice.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Author: shenge
 * @Date: 2020-05-10 13:36
 * <p>
 * 读操作监听器
 */
// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class DemoDataListener extends AnalysisEventListener<DataExcel> {

    private List<DataExcel> list = new ArrayList<>();

    private Map<Integer, CellData> map;

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as
     * @param context
     */
    @Override
    public void invoke(DataExcel data, AnalysisContext context) {
        System.out.println(data);
        list.add(data);
    }

    //在所有数据都传入后调用这个。
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    //读取表头
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        System.out.println(headMap);
        map = headMap;
    }

    public List<DataExcel> getList() {
        return list;
    }

    public Map<Integer, CellData> getMap() {
        return map;
    }
}