package com.shen.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shen.commonutils.RandomUtil;
import com.shen.commonutils.Result;
import com.shen.staservice.client.ucenter.UcenterClient;
import com.shen.staservice.entity.StatisticsDaily;
import com.shen.staservice.entity.vo.ShowChartVo;
import com.shen.staservice.mapper.StatisticsDailyMapper;
import com.shen.staservice.service.IStatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author shen
 * @since 2020-05-24
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements IStatisticsDailyService {

    @Autowired
    UcenterClient ucenterClient;

    @Override
    public void insertCount(String day) {
        //远程调用查询注册人数
        Result result = ucenterClient.countRegister(day);
        Integer registerCount = (Integer) result.getData().get("count");


        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(registerCount);
        sta.setDateCalculated(day);
        sta.setVideoViewNum(RandomUtils.nextInt(100,200));
        sta.setCourseNum(RandomUtils.nextInt(100,200));
        sta.setLoginNum(RandomUtils.nextInt(100,200));

        UpdateWrapper<StatisticsDaily> staDaily = new UpdateWrapper<>();
        staDaily.eq("date_calculated",day);
        this.saveOrUpdate(sta,staDaily);


    }

    /**
     * 查询出图表信息
     * @param showChartVo
     * @return
     */
    @Override
    public Map<String, Object> searchChartInfo(ShowChartVo showChartVo) {
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date_calculated",showChartVo.getBegin(),showChartVo.getEnd());
        String type = showChartVo.getType();
        queryWrapper.select(type,"date_calculated");  //只拿到个别属性
        queryWrapper.orderByAsc("date_calculated");
        List<StatisticsDaily> staList = this.list(queryWrapper);

        //封装到map中
        Map<String, Object> map = new HashMap<>();
        List<Integer> nums = new ArrayList<>();
        List<String> date = new ArrayList<>();
        map.put("num",nums);
        map.put("date",date);
        for (StatisticsDaily daily : staList) {
            date.add(daily.getDateCalculated());
            switch (type){
                case "login_num":
                    nums.add(daily.getLoginNum());
                    break;
                case "register_num":
                    nums.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    nums.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    nums.add(daily.getCourseNum());
            }
        }
        return map;
    }
}
