package com.oyyo.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oyyo.commonUtils.Resp;
import com.oyyo.eduservice.entity.EduCourse;
import com.oyyo.eduservice.entity.EduTeacher;
import com.oyyo.eduservice.service.EduCourseService;
import com.oyyo.eduservice.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    private EduCourseService courseService;

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
     * 发布课程
     * @param id
     * @return
     */
    @PostMapping("publishCourse/{id}")
    public Resp publishCourse(@PathVariable("id")String id){

        courseService.publishCourse(id);
        return Resp.ok();
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

    /**
     * 查询 前 8 条热门 课程，4条名师
     * @return
     */
    @GetMapping("hotIndex")
    public Resp queryHotTeacherAndCourse(){

        Map<String,Object> map = courseService.queryHotTeacherAndCourse();
        return Resp.ok().data("hotCourse",map.get("hotCourse")).data("hotTeacher",map.get("hotTeacher"));
    }

    /**
     * 条件查询课程
     * @return
     */
    @PostMapping("queryCourseByProtal/{current}/{limit}")
    public Resp queryCourseByProtal(@PathVariable("current") Long current,
                                    @PathVariable("limit")Long limit,
                                    @RequestBody(required = false) CourseInfoQueryVO courseQueryVO){

        Map<String,Object> map = courseService.queryCourseByProtal(current, limit, courseQueryVO);

        return Resp.ok().data(map);
    }


    /**
     * 查询课程详情
     * @param courseId
     * @return
     */
    @GetMapping("getCourseInfoAndTeacher/{courseId}")
    public Resp queryCourseInfoAndTeacher(@PathVariable("courseId")String courseId){
        Map<String,Object> result = courseService.queryCourseInfoAndTeacher(courseId);

        return Resp.ok().data(result);
    }
}

