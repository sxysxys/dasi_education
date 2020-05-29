package com.shen.eduservice.constant;

/**
 * @Author: shenge
 * @Date: 2020-05-13 20:15
 * 发布状态
 */
public enum PublishStatus {

    PUBLISH("Normal");

    private String status;

    PublishStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
