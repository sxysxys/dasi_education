package com.shen.eduservice.constant;

/**
 * @Author: shenge
 * @Date: 2020-05-10 17:15
 */
public enum SubjectConstant {

    PARENT_ID("0");

    private String pid;

    public String getPid() {
        return pid;
    }

    SubjectConstant(String pid) {
        this.pid = pid;
    }
}
