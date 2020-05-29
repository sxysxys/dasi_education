package com.shen.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: shenge
 * @Date: 2020-05-13 22:28
 * 课程查询
 */
@Data
@ApiModel(value = "EduCourse对象", description = "课程查询")
public class CourseQuery {

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程状态 Draft未发布  Normal已发布")
    private String status;

}
