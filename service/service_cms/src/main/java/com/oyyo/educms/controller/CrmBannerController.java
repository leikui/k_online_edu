package com.oyyo.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oyyo.commonUtils.Resp;
import com.oyyo.educms.entity.CrmBanner;
import com.oyyo.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author oy
 * @since 2020-07-05
 */
@RestController
@RequestMapping("/educms/banner")
public class CrmBannerController {

    @Autowired
    private CrmBannerService bannerService;


    /**
     * 查询首页banner
     * @return
     */
    @GetMapping
    public Resp queryAllBanner(){

        List<CrmBanner> bannerList = bannerService.queryAllBanner();
        return Resp.ok().data("list",bannerList);
    }


    /**
     * 分页查询 banner
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("pageBanner/{page}/{limit}")
    public Resp queryBannerByPage(@PathVariable("page") Long page,@PathVariable("limit")Long limit){
        Page<CrmBanner> pageBanner = bannerService.queryBannerByPage(page,limit);

        return Resp.ok().data("banners",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    /**
     * 添加 banner
     * @param banner
     * @return
     */
    @PostMapping("addBanner")
    public Resp addBanner(@RequestBody CrmBanner banner){

        bannerService.save(banner);
        return Resp.ok();

    }

    @GetMapping("{id}")
    public Resp queryBannerById(@PathVariable("id") String id){
        CrmBanner banner = bannerService.getById(id);
        return Resp.ok().data("banner", banner);
    }

    /**
     * 修改 banner
     * @param banner
     * @return
     */
    @PostMapping("updateBanner")
    public Resp updateBanner(@RequestBody CrmBanner banner){

        bannerService.updateById(banner);
        return Resp.ok();

    }

    /**
     * 删除 banner
     * @param id
     * @return
     */
    @DeleteMapping("deleteBanner/{id}")
    public Resp deleteBanner(@PathVariable("id") String id){
        bannerService.removeById(id);
        return Resp.ok();
    }

}

