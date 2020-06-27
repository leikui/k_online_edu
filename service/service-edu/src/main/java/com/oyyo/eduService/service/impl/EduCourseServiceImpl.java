package com.oyyo.eduService.service.impl;

import com.oyyo.eduService.entity.EduCourse;
import com.oyyo.eduService.entity.EduCourseDescription;
import com.oyyo.eduService.mapper.EduCourseMapper;
import com.oyyo.eduService.service.EduCourseDescriptionService;
import com.oyyo.eduService.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oyyo.eduService.vo.CourseInfoVO;
import com.oyyo.serviceBase.handler.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionService descriptionService;

    /**
     * 添加课程
     * @param courseInfoVO
     * @return
     */
    @Override
    @Transactional
    public String addCourseInfo(CourseInfoVO courseInfoVO) {

        //添加课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO,eduCourse);

        boolean saveCourseFlag = this.save(eduCourse);
        if (!saveCourseFlag) {
            log.error("添加课程失败");
            throw new BaseException(20002,"添加课程失败！");
        }
        log.info("添加课程成功");
        //添加课程简介表
        //课程id
        String courseId = eduCourse.getId();

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVO.getDescription());
        eduCourseDescription.setId(courseId);
        boolean saveDescFlag = descriptionService.save(eduCourseDescription);
        if (!saveDescFlag) {
            log.error("添加课程描述失败");
            throw new BaseException(20002,"添加课程描述失败！");
        }
        log.info("添加课程描述成功");
        return courseId;
    }
}
