package com.shen.eduorder.client.course;

import com.shen.commonutils.ordervo.CourseWebInfoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: shenge
 * @Date: 2020-05-23 05:48
 */
@FeignClient(name = "service-edu", fallback = CourseInfoFeign.class)
@Component
public interface CourseInfoClient {

    @GetMapping("/eduservice/course/getCourseOrder/{courseId}")
    public CourseWebInfoOrder getCourseOrder(@PathVariable("courseId")String courseId);

}
