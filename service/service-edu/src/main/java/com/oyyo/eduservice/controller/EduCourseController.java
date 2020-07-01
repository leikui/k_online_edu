package com.oyyo.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oyyo.commonUtils.Resp;
import com.oyyo.eduservice.entity.EduCourse;
import com.oyyo.eduservice.entity.EduTeacher;
import com.oyyo.eduservice.service.EduCourseService;
import com.oyyo.eduservice.vo.CourseInfoVO;
import com.oyyo.eduservice.vo.CoursePublishVO;
import com.oyyo.eduservice.vo.CourseQueryVO;
import com.oyyo.eduservice.vo.EduTeacherVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return Resp.ok().data("courseInfo",courseInfoVO);
    }

    /**
     * 修改课程
     * @param courseInfoVO
     * @return
     */
    @PostMapping("updateCourseInfo")
    public Resp updateCourseInfo(@RequestBody CourseInfoVO courseInfoVO){
        courseService.updateCourseInfo(courseInfoVO);
        return Resp.ok();
    }

    /**
     * 根据课程id查询 最终发布信息
     * @param id
     * @return
     */
    @GetMapping("getPublishCourseInfo/{id}")
    public Resp queryPublishCourseInfo(@PathVariable("id") String id){
        CoursePublishVO publishVO = courseService.queryPublishCourseInfo(id);

        return Resp.ok().data("publishCourse",publishVO);
    }

    /**
     * 分页查询讲师列表
     * @param current
     * @param limit
     * @return
     */
    @ApiOperation(value = "分页查询讲师列表")
    @PostMapping("page/{current}/{limit}")
    public Resp queryCourseByPage(@PathVariable("current") Long current,
                                  @PathVariable("limit")Long limit,
                                  @RequestBody(required = false) CourseQueryVO courseQuery){

        Map map = courseService.queryCourseByPage(current, limit, courseQuery);

        return Resp.ok().data(map);
    }

    /**
     * 根据id删除课程信息
     * @return
     */
    @DeleteMapping("{courseId}")
    public Resp deleteCourseInfo(@PathVariable("courseId") String courseId){
        boolean delFlag = courseService.deleteCourseInfo(courseId);
        return delFlag ? Resp.ok() : Resp.error();
    }
}

