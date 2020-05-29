package com.shen.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shen.eduservice.entity.EduSubject;
import com.shen.eduservice.entity.excel.SubjectData;
import com.shen.eduservice.entity.subject.OneSubject;
import com.shen.eduservice.entity.subject.TwoSubject;
import com.shen.eduservice.listen.DemoDataListener;
import com.shen.eduservice.mapper.EduSubjectMapper;
import com.shen.eduservice.service.IEduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author shen
 * @since 2020-05-10
 */
@Service
@Transactional
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements IEduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            DemoDataListener readListener = new DemoDataListener(this);
            EasyExcel.read(inputStream, SubjectData.class, readListener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //按照固定格式返回相应的树形结构数据。
    @Override
    public List<OneSubject> listOneAndTwoSubject() {
        //拿到所有的一级分类
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", "0");
        List<EduSubject> list = this.list(queryWrapper);

        List<OneSubject> oneSubjectList = new ArrayList<>();  //用来存储最终集合

        for (EduSubject eduSubject : list) {
            queryWrapper.clear();
            queryWrapper.eq("parent_id", eduSubject.getId());
            //拿到一个一级对应的所有二级
            List<EduSubject> listTwo = this.list(queryWrapper);
            List<TwoSubject> twoSubjects = new ArrayList<>();
            for (EduSubject subject : listTwo) {
                TwoSubject twoSubject = new TwoSubject();
                BeanUtils.copyProperties(subject, twoSubject);
                twoSubjects.add(twoSubject);
            }

            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject, oneSubject);
            oneSubject.setChildren(twoSubjects);

            oneSubjectList.add(oneSubject);
        }
        return oneSubjectList;
    }
}
