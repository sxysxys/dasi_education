package com.shen.cmsservice.service;

import com.shen.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author shen
 * @since 2020-05-16
 */
public interface ICrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectAllBanner();
}
