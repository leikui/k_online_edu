package com.oyyo.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oyyo.eduservice.entity.EduTeacher;
import com.oyyo.eduservice.mapper.EduTeacherMapper;
import com.oyyo.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oyyo.eduservice.vo.EduTeacherVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author oy
 * @since 2020-06-20
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map queryTeacherByTeacherVO(Long current, Long limit, EduTeacherVO teacherVO) {

        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(teacherVO.getName())) {
            wrapper.like("name", teacherVO.getName());
        }
        if (teacherVO.getLevel() != null){
            wrapper.eq("level", teacherVO.getLevel());
        }
        if (!StringUtils.isEmpty(teacherVO.getBegin())) {
            wrapper.ge("gmt_create", teacherVO.getBegin());
        }
        if (!StringUtils.isEmpty(teacherVO.getEnd())) {
            wrapper.le("gmt_create", teacherVO.getEnd());
        }
        wrapper.orderByDesc("gmt_modified");
        Page<EduTeacher> teacherPage = page(pageTeacher, wrapper);
        long total = teacherPage.getTotal();
        List<EduTeacher> teachers = teacherPage.getRecords();
        Map map = new HashMap(2);
        map.put("total", total);
        map.put("teachers", teachers);

        return map;
    }
}
