package com.oyyo.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oyyo.eduservice.entity.EduCourse;
import com.oyyo.eduservice.entity.EduVideo;
import com.oyyo.eduservice.feign.ServiceVodClient;
import com.oyyo.eduservice.mapper.EduVideoMapper;
import com.oyyo.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author oy
 * @since 2020-06-27
 */
@Service
@Slf4j
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private ServiceVodClient vodClient;

    /**
     * 根据 课程id 删除小节
     * TODO 视频删除
     * @param courseId
     * @return
     */
    @Override
    public boolean deleteVideoByCourseId(String courseId) {
        List<EduVideo> videos = this.list(new QueryWrapper<EduVideo>().eq("course_id", courseId).select("video_source_id"));

        List<String> videoIds = videos.stream().map(EduVideo::getVideoSourceId).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(videoIds)) {
            String ids = StringUtils.join(videoIds, ",");
            log.info("远程调用vod服务批量删除视频，ids：[{}]",ids);
            vodClient.deleteAliyunVideoByVideoId(ids);
        }
        return this.remove(new QueryWrapper<EduVideo>().eq("course_id", courseId));
    }
}
