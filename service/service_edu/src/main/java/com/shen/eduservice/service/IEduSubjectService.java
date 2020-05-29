package com.shen.eduservice.service;

import com.shen.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shen.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author shen
 * @since 2020-05-10
 */
public interface IEduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file);

    List<OneSubject> listOneAndTwoSubject();
}
