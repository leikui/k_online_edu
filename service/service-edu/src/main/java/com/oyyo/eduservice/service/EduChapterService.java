package com.oyyo.eduservice.service;

import com.oyyo.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oyyo.eduservice.vo.ChapterAndVideoVO;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author oy
 * @since 2020-06-27
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据课程id课程大纲列表
     * @param courseId
     * @return
     */
    List<ChapterAndVideoVO> queryChapterVideo(String courseId);
}
