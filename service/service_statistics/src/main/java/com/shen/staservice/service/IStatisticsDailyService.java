package com.shen.staservice.service;

import com.shen.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shen.staservice.entity.vo.ShowChartVo;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author shen
 * @since 2020-05-24
 */
public interface IStatisticsDailyService extends IService<StatisticsDaily> {

    void insertCount(String day);

    Map<String, Object> searchChartInfo(ShowChartVo showChartVo);
}
