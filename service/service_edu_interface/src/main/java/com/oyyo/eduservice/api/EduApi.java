package com.oyyo.eduservice.api;

import com.oyyo.commonUtils.Resp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface EduApi {
    /**
     * 查询课程详情
     *
     * @param courseId
     * @return
     */
    @GetMapping("eduService/course/getCourseInfoAndTeacher/{courseId}")
    Resp queryCourseInfoAndTeacher(@PathVariable("courseId") String courseId);
}
