package com.shen.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Author: shenge
 * @Date: 2020-05-13 16:43
 * <p>
 * 最终发布时返回的数据封装
 */

@ApiModel(value = "课程发布信息")
@Data
public class CoursePublishVo {

    private static final long serialVersionUID = 1L;

    private String id;

    private String title;

    private String cover;  //封面

    private Integer lessonNum;

    private String subjectLevelOne;

    private String subjectLevelTwo;

    private String teacherName;

    private String price;//只用于显示

}
