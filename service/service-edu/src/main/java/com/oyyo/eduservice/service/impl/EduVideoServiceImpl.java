package com.oyyo.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oyyo.eduservice.entity.EduVideo;
import com.oyyo.eduservice.mapper.EduVideoMapper;
import com.oyyo.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author oy
 * @since 2020-06-27
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    /**
     * 根据 课程id 删除小节
     * TODO 视频删除
     * @param courseId
     * @return
     */
    @Override
    public boolean deleteVideoByCourseId(String courseId) {
        return this.remove(new QueryWrapper<EduVideo>().eq("course_id", courseId));
    }
}
