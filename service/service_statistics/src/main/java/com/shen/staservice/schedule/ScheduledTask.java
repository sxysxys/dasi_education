package com.shen.staservice.schedule;

import com.shen.staservice.service.IStatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

/**
 * @Author: shenge
 * @Date: 2020-05-25 13:29
 */
@Component
public class ScheduledTask {

    @Autowired
    IStatisticsDailyService statisticsDailyService;

    /**
     * 每天凌晨一点将前一天的数据送入，进行数据的更新操作。
     */
    @Scheduled(cron = "0 0 1 * * ? ")
    public void updateSta(){
        String beforeDay = LocalDateTime.now().withDayOfMonth(LocalDateTime.now().getDayOfMonth()-1).format(ISO_LOCAL_DATE);
        statisticsDailyService.insertCount(beforeDay);
    }

}
