package com.shen.cmsservice.controller;

import com.shen.cmsservice.entity.CrmBanner;
import com.shen.cmsservice.service.ICrmBannerService;
import com.shen.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: shenge
 * @Date: 2020-05-16 16:02
 * <p>
 * 前台用户看到的接口
 */

@RestController
@RequestMapping("/cmsservice/bannerfront")
//@CrossOrigin
public class BannerFrontController {

    @Autowired
    ICrmBannerService bannerService;

    @GetMapping("getAllBanner")
    public Result getAllBanner() {
        List<CrmBanner> crmBanners = bannerService.selectAllBanner();
        return Result.ok().data("list", crmBanners);
    }
}
