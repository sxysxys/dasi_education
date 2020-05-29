package com.shen.eduservice.listen;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shen.eduservice.constant.SubjectConstant;
import com.shen.eduservice.entity.EduSubject;
import com.shen.eduservice.entity.excel.SubjectData;
import com.shen.eduservice.service.IEduSubjectService;
import com.shen.servicebase.exception.CustomizeException;

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
public class DemoDataListener extends AnalysisEventListener<SubjectData> {

//    private static Integer sort = 1 ;

    private IEduSubjectService eduSubjectService;

    //使用这种方式和数据库进行交互
    public DemoDataListener(IEduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as
     * @param context
     */
    @Override
    public void invoke(SubjectData data, AnalysisContext context) {
        if (data == null) {
            throw new CustomizeException(20001, "文件无数据");
        } else {
            //判断一级分类是否重复
            EduSubject eduSubject1 = existOneSubject(data.getOneSubjectName());
            if (eduSubject1 == null) {
                eduSubject1 = new EduSubject(data.getOneSubjectName(), SubjectConstant.PARENT_ID.getPid());
                eduSubjectService.save(eduSubject1); //此时edusubject有所有值了。
            }
            String pid = eduSubject1.getId();
            EduSubject eduSubject2 = existTwoSubject(data.getTwoSubjectName(), pid);
            if (eduSubject2 == null) {
                eduSubject2 = new EduSubject(data.getTwoSubjectName(), pid);
                eduSubjectService.save(eduSubject2);
            }
        }
    }


    //在所有数据都传入后调用这个。
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    //读取表头
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {

    }

    /**
     * 判断一级是否存在
     *
     * @param name 送入一级
     * @return
     */
    private EduSubject existOneSubject(String name) {
        //设置条件
        QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("parent_id", "0").eq("title", name);
        EduSubject one = eduSubjectService.getOne(eduSubjectQueryWrapper);
        return one;
    }

    /**
     * 判断二级是否存在
     *
     * @param name 送入二级
     * @return
     */
    private EduSubject existTwoSubject(String name, String pid) {
        //设置条件
        QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("parent_id", pid).eq("title", name);
        EduSubject two = eduSubjectService.getOne(eduSubjectQueryWrapper);
        return two;
    }

}