package com.oyyo.educms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oyyo.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author oy
 * @since 2020-07-05
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 分页查询 banner
     * @param page
     * @param limit
     * @return
     */
    Page<CrmBanner> queryBannerByPage(Long page, Long limit);

    /**
     * 查询所有 banner
     * @return
     */
    List<CrmBanner> queryAllBanner();
}
