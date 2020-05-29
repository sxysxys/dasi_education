package com.shen.staservice.client.ucenter;

import com.shen.commonutils.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: shenge
 * @Date: 2020-05-25 09:08
 */
@Component
public class UcenterFeign implements UcenterClient{

    @Override
    public Result countRegister(@PathVariable("date") String date){
        return Result.error().message("系统调用失败");
    }

}
