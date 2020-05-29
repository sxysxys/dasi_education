package com.shen.eduorder.client.course;


import com.shen.commonutils.ordervo.CourseWebInfoOrder;
import com.shen.eduorder.client.course.CourseInfoClient;
import org.springframework.stereotype.Component;

/**
 * @Author: shenge
 * @Date: 2020-05-23 05:51
 */
@Component
public class CourseInfoFeign implements CourseInfoClient {

    @Override
    public CourseWebInfoOrder getCourseOrder(String courseId) {
        return null;
    }
}
