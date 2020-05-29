package com.shen.staservice.controller;


import com.shen.commonutils.Result;
import com.shen.staservice.client.ucenter.UcenterClient;
import com.shen.staservice.entity.vo.ShowChartVo;
import com.shen.staservice.service.IStatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author shen
 * @since 2020-05-24
 */
@RestController
@RequestMapping("/staservice/statistics")
//@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    IStatisticsDailyService statisticsDailyService;


    @PostMapping("registerCount/{day}")
    public Result registerCount(@PathVariable("day") String day){
        statisticsDailyService.insertCount(day);
        return Result.ok();
    }

    @PostMapping("showChart")
    public Result showChart(@RequestBody ShowChartVo showChartVo){
        Map<String,Object> map=statisticsDailyService.searchChartInfo(showChartVo);
        return Result.ok().data(map);
    }


}

