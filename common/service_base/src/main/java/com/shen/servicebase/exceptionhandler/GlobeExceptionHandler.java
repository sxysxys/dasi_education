package com.shen.servicebase.exceptionhandler;

import com.shen.commonutils.Result;
import com.shen.servicebase.exception.CustomizeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: shenge
 * @Date: 2020-05-06 14:14
 * 统一异常处理类
 */

@ControllerAdvice
public class GlobeExceptionHandler {

    //统一异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message("执行了全局异常处理");
    }

    //特定异常处理
    @ExceptionHandler(CustomizeException.class)
    @ResponseBody
    public Result error(CustomizeException e){
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMessage());
    }

}
