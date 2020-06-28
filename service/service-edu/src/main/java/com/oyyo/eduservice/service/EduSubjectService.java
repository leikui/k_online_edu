package com.oyyo.eduservice.service;

import com.oyyo.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oyyo.eduservice.vo.SubjectVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author oy
 * @since 2020-06-25
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     * @param file
     * @return
     */
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    /**
     * 获取所有课程分类列表  树形结构
     * @return
     */
    List<SubjectVO> querySubject();
}
