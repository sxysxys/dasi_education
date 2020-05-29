package com.shen.cmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shen.cmsservice.entity.CrmBanner;
import com.shen.cmsservice.mapper.CrmBannerMapper;
import com.shen.cmsservice.service.ICrmBannerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author shen
 * @since 2020-05-16
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements ICrmBannerService {


    @Cacheable(key = "'selectAllBanner'", value = "banner")
    @Override
    public List<CrmBanner> selectAllBanner() {
        QueryWrapper<CrmBanner> crmBannerQueryWrapper = new QueryWrapper<>();
        crmBannerQueryWrapper.orderByDesc("id");
        crmBannerQueryWrapper.last("limit 2");
        List<CrmBanner> list = this.list(crmBannerQueryWrapper);
        return list;
    }
}
