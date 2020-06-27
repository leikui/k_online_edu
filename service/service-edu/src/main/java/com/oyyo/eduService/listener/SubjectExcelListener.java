package com.oyyo.eduService.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oyyo.commonUtils.ResultCodeEnum;
import com.oyyo.eduService.entity.EduSubject;
import com.oyyo.eduService.service.EduSubjectService;
import com.oyyo.eduService.vo.SubjectExcelVO;
import com.oyyo.serviceBase.handler.BaseException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectExcelVO> {

    public EduSubjectService subjectService;

    public SubjectExcelListener() {}
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }


    /**
     * 读取excel 数据保存入 数据库
     * @param subjectExcelVO
     * @param analysisContext
     */
    @Override
    public void invoke(SubjectExcelVO subjectExcelVO, AnalysisContext analysisContext) {
        if (subjectExcelVO == null) {
            //excel 导入错误
            throw new BaseException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR.getCode(),ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR.getMessage());
        }

        //添加一级分类
        EduSubject oneSubject = this.existSubject(subjectService, subjectExcelVO.getOneLevelSubjectName(), "0");
        if (oneSubject == null) {
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectExcelVO.getOneLevelSubjectName());

            subjectService.save(oneSubject);
        }

        //添加二级分类

        String pid = oneSubject.getId();
        EduSubject twoSubject = this.existSubject(subjectService, subjectExcelVO.getTwoLevelSubjectName(), pid);
        if (twoSubject == null) {
            twoSubject = new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectExcelVO.getTwoLevelSubjectName());

            subjectService.save(twoSubject);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    /**
     * 判断 1/2 级分类名称是否重复
     * @param subjectService
     * @param name
     * @return
     */
    private EduSubject existSubject(EduSubjectService subjectService,String name,String pid){

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;

    }

}
