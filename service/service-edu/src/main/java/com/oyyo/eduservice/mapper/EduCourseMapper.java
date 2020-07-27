package com.oyyo.eduservice.mapper;

import com.oyyo.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oyyo.eduservice.vo.CoursePublishVO;
import com.oyyo.eduservice.vo.CourseWebVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author oy
 * @since 2020-06-27
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {


    /**
     * 查询课程发布信息
     * @param courseId
     * @return
     */
    CoursePublishVO queryPublishVO(String courseId);


    /**
     * 查询课程详情
     * @param courseId
     * @return
     */
    CourseWebVo queryCourseInfoAndTeacher(@Param("courseId") String courseId);
}
