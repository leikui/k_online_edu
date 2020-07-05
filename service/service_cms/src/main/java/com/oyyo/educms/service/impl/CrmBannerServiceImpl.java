package com.oyyo.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oyyo.educms.entity.CrmBanner;
import com.oyyo.educms.mapper.CrmBannerMapper;
import com.oyyo.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author oy
 * @since 2020-07-05
 */
@Service
@Slf4j
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * 分页查询 banner
     * @param page
     * @param limit
     * @return
     */
    @Override
    public Page<CrmBanner> queryBannerByPage(Long page, Long limit) {

        log.info("分页查询banner");
        return this.page(new Page<>(page, limit));

    }

    /**
     * 查询所有 banner
     * @return
     */
    @Override
    public List<CrmBanner> queryAllBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        // 根据排序自动 排序
        wrapper.orderByDesc("sort");

        // 获取前 4 条记录
        wrapper.last("limit 4");
        List<CrmBanner> banners = this.list(wrapper);
        return banners;
    }
}
