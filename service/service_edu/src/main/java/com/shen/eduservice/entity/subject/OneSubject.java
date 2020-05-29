package com.shen.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: shenge
 * @Date: 2020-05-11 09:46
 */
@Data
public class OneSubject {

    private String title;

    private String id;

    private List<TwoSubject> children = new ArrayList<>();
}
