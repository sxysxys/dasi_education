package com.shen.msmservice.service;

/**
 * @Author: shenge
 * @Date: 2020-05-19 14:48
 */
public interface MsmService {

    Boolean sendMessage(String phone, String code);
}
