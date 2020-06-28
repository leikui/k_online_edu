package com.oyyo.eduservice.controller;


import com.oyyo.commonUtils.Resp;
import com.oyyo.eduservice.service.EduCourseService;
import com.oyyo.eduservice.vo.CourseInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 根据id查询课程信息
     * @param courseId
     * @return
     */
    @GetMapping("getCourseInfo/{courseId}")
    public Resp queryCourseInfoByCourseId(@PathVariable("courseId") String courseId){
        CourseInfoVO courseInfoVO = courseService.queryCourseInfoByCourseId(courseId);
        return Resp.ok();
    }



}

