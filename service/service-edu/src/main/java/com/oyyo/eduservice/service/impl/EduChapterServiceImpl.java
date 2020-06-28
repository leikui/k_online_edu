package com.oyyo.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oyyo.eduservice.entity.EduChapter;
import com.oyyo.eduservice.entity.EduVideo;
import com.oyyo.eduservice.mapper.EduChapterMapper;
import com.oyyo.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oyyo.eduservice.service.EduVideoService;
import com.oyyo.eduservice.vo.ChapterAndVideoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author oy
 * @since 2020-06-27
 */
@Service
@Slf4j
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;


    /**
     * 根据课程id课程大纲列表
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterAndVideoVO> queryChapterVideo(String courseId) {

        log.info("课程id为[{}]",courseId);
        //根据课程id查询所有章节
        List<EduChapter> chapters = this.list(new QueryWrapper<EduChapter>().eq("course_id", courseId));
        log.info("根据课程id查询所有章节完成，课程id为[{}]",courseId);
        //根据章节id查询所有小节
        List<EduVideo> videos = videoService.list(new QueryWrapper<EduVideo>().eq("course_id", courseId).orderByAsc("sort"));
        log.info("根据章节id查询所有小节完成，课程id为[{}]",courseId);
        //遍历章节
        List<ChapterAndVideoVO> chapterAndVideoVOS = chapters.stream().map(eduChapter -> {
            ChapterAndVideoVO chapterVO = new ChapterAndVideoVO();
            BeanUtils.copyProperties(eduChapter, chapterVO);
            //遍历小节
            List<ChapterAndVideoVO> videoVOS = videos.stream().map(eduVideo -> {
                //判断属于章节的所有小节
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    ChapterAndVideoVO videoVO = new ChapterAndVideoVO();
                    BeanUtils.copyProperties(eduVideo, videoVO);
                    return videoVO;
                }
                return null;
                //过滤 null
            }).filter(Objects::nonNull).collect(Collectors.toList());
            //设置章节中的小节
            chapterVO.setChildren(videoVOS);
            return chapterVO;
        }).collect(Collectors.toList());
        log.info("封装数据完成，课程id为[{}]",courseId);
        return chapterAndVideoVOS;
    }
}
