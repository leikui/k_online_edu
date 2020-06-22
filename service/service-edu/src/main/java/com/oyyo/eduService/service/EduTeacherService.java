package com.oyyo.eduService.service;

import com.oyyo.eduService.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oyyo.eduService.vo.EduTeacherVO;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author oy
 * @since 2020-06-20
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 条件查询teacher信息
     * @param current
     * @param limit
     * @param teacherVO
     * @return
     */
    Map queryTeacherByTeacherVO(Long current, Long limit, EduTeacherVO teacherVO);
}
