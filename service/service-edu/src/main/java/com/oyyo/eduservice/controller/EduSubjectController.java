package com.oyyo.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oyyo.commonUtils.Resp;
import com.oyyo.eduservice.entity.EduSubject;
import com.oyyo.eduservice.service.EduSubjectService;
import com.oyyo.eduservice.vo.SubjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author oy
 * @since 2020-06-25
 */
@RestController
@RequestMapping("/eduService/subject")
public class EduSubjectController {

    @Autowired
    EduSubjectService subjectService;

    /**
     * 添加课程分类
     * @param file
     * @return
     */
    @PostMapping("addSubject")
    public Resp addSubject(MultipartFile file){
        subjectService.saveSubject(file,subjectService);
        return Resp.ok();
    }

    /**
     * 获取所有课程分类列表  树形结构
     * @return
     */
    @GetMapping("getSubject")
    public Resp querySubject(){

        List<SubjectVO> subjectVOS = subjectService.querySubject();

        return Resp.ok().data("tree",subjectVOS);
    }
    @GetMapping("getAllSubject")
    public Resp queryOneSubject(){

        List<EduSubject> oneSubject = subjectService.list(new QueryWrapper<EduSubject>().eq("parent_id", "0"));
        List<EduSubject> twoSubject = subjectService.list(new QueryWrapper<EduSubject>().ne("parent_id", "0"));

        return Resp.ok().data("oneSubject",oneSubject).data("twoSubject",twoSubject);
    }

}

