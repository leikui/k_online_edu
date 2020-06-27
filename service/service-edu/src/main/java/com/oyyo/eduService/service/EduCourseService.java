package com.oyyo.eduService.service;

import com.oyyo.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oyyo.eduService.vo.CourseInfoVO;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author oy
 * @since 2020-06-27
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程
     * @param courseInfoVO
     * @return
     */
    String addCourseInfo(CourseInfoVO courseInfoVO);
}
