package com.oyyo.eduservice.controller;


import com.oyyo.commonUtils.Resp;
import com.oyyo.eduservice.service.EduChapterService;
import com.oyyo.eduservice.vo.ChapterAndVideoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author oy
 * @since 2020-06-27
 */
@RestController
@RequestMapping("/eduService/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    /**
     * 根据课程id课程大纲列表
     * @param courseId
     * @return
     */
    @GetMapping("getChapterVideo/{courseId}")
    public Resp queryChapterVideo(@PathVariable("courseId")String courseId){
        List<ChapterAndVideoVO> result =  chapterService.queryChapterVideo(courseId);
        return Resp.ok().data("chapter",result);
    }



}

