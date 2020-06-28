package com.oyyo.eduservice.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.oyyo.eduservice.entity.EduSubject;
import com.oyyo.eduservice.listener.SubjectExcelListener;
import com.oyyo.eduservice.mapper.EduSubjectMapper;
import com.oyyo.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oyyo.eduservice.utils.MenuTree;
import com.oyyo.eduservice.vo.SubjectExcelVO;
import com.oyyo.eduservice.vo.SubjectVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author oy
 * @since 2020-06-25
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 添加课程分类
     * @param file
     * @return
     */
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {

        try (InputStream inputStream = file.getInputStream()){
            // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
            EasyExcel.read(inputStream, SubjectExcelVO.class, new SubjectExcelListener(subjectService)).sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有课程分类列表  树形结构
     * @return
     */
    @Override
    public List<SubjectVO> querySubject() {

        //查询所有分类
        List<EduSubject> subjects = list();

        List<SubjectVO> subjectVOS = subjects.stream().map(subject -> {
            SubjectVO subjectVO = new SubjectVO();
            BeanUtils.copyProperties(subject, subjectVO);
            return subjectVO;
        }).collect(Collectors.toList());

        MenuTree menuTree =new MenuTree(subjectVOS);
        List<SubjectVO> menuList=menuTree.builTree();

        String jsonOutput= JSONUtil.toJsonStr(menuList);
        System.out.println(jsonOutput);

        return menuList;
    }

}
