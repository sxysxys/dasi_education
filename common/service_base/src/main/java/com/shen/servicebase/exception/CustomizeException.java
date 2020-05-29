package com.shen.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: shenge
 * @Date: 2020-05-06 14:56
 * 自定义异常
 */

@Data
@AllArgsConstructor  //生成有参数构造方法
@NoArgsConstructor  //生成无参数构造方法，也可以使用枚举来定义。
public class CustomizeException extends RuntimeException {
    private Integer code;
    private String message;
}
