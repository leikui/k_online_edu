package com.oyyo.eduService.controller;


import com.oyyo.commonUtils.Resp;
import com.oyyo.eduService.service.EduCourseService;
import com.oyyo.eduService.vo.CourseInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author oy
 * @since 2020-06-27
 */
@RestController
@RequestMapping("eduService/course")
public class EduCourseController {

    @Autowired
    EduCourseService courseService;

    /**
     * 添加课程
     * @param courseInfoVO
     * @return
     */
    @PostMapping("addCourseInfo")
    public Resp addCourseInfo(@RequestBody CourseInfoVO courseInfoVO){
        String courseId = courseService.addCourseInfo(courseInfoVO);
        return Resp.ok().data("courseId",courseId);
    }

}

