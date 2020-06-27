package com.oyyo.eduService.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oyyo.eduService.entity.EduSubject;
import com.oyyo.eduService.listener.SubjectExcelListener;
import com.oyyo.eduService.mapper.EduSubjectMapper;
import com.oyyo.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oyyo.eduService.utils.MenuTree;
import com.oyyo.eduService.vo.SubjectExcelVO;
import com.oyyo.eduService.vo.SubjectVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

        //查询所有一级分类
        List<EduSubject> subjects = list();

        List<SubjectVO> menuList;

        List<SubjectVO> subjectVOS = subjects.stream().map(subject -> {
            SubjectVO subjectVO = new SubjectVO();
            BeanUtils.copyProperties(subject, subjectVO);
            return subjectVO;
        }).collect(Collectors.toList());

        MenuTree menuTree =new MenuTree(subjectVOS);
        menuList=menuTree.builTree();
        //查询二级分类

        String jsonOutput= JSONUtil.toJsonStr(menuList);
        System.out.println(jsonOutput);

        return menuList;
    }

}
