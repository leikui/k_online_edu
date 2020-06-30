package com.oyyo.eduservice.controller;


import com.oyyo.commonUtils.Resp;
import com.oyyo.eduservice.entity.EduChapter;
import com.oyyo.eduservice.service.EduChapterService;
import com.oyyo.eduservice.vo.ChapterAndVideoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    /**
     * 添加章节
     * @param chapter
     * @return
     */
    @PostMapping("addChapter")
    public Resp addChapter(@RequestBody EduChapter chapter){
        chapterService.save(chapter);
        return Resp.ok();
    }

    /**
     * 根据id查询章节
     * @return
     */
    @GetMapping("getChapterInfo/{chapterId}")
    public Resp getChapterInfo(@PathVariable("chapterId") String chapterId){
        EduChapter eduChapter = chapterService.getById(chapterId);
        return Resp.ok().data("chapter",eduChapter);
    }

    /**
     * 修改章节
     * @param chapter
     * @return
     */
    @PostMapping("updateChapter")
    public Resp updateChapter(@RequestBody EduChapter chapter){
        chapterService.updateById(chapter);
        return Resp.ok();
    }

    /**
     * 根据id删除章节
     * @return
     */
    @DeleteMapping("{chapterId}")
    public Resp deleteChapterInfo(@PathVariable("chapterId") String chapterId){
        Boolean flag = chapterService.deleteChapterInfo(chapterId);
        return flag ? Resp.ok() : Resp.error();
    }


}

