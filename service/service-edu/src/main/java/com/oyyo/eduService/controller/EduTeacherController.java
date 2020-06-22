package com.oyyo.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oyyo.commonUtils.Resp;
import com.oyyo.commonUtils.ResultCodeEnum;
import com.oyyo.eduService.entity.EduTeacher;
import com.oyyo.eduService.service.EduTeacherService;
import com.oyyo.eduService.vo.EduTeacherVO;
import com.oyyo.serviceBse.handler.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author oy
 * @since 2020-06-20
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduService/edu-teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;


    /**
     * 查询所有讲师
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public Resp queryAllTeacher(){
        List<EduTeacher> eduTeachers = teacherService.list();
        try {
            int i = 1/0;
        } catch (Exception e) {
            throw new BaseException(ResultCodeEnum.UNKNOWN_REASON.getCode(),ResultCodeEnum.UNKNOWN_REASON.getMessage());
        }
        return Resp.ok().data("teacherList",eduTeachers);
    }


    /**
     * 分页查询讲师列表
     * @param current
     * @param limit
     * @return
     */
    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("page/{current}/{limit}")
    public Resp queryTeacherByPage(@PathVariable("current") Long current,@PathVariable("limit")Long limit){

        Page<EduTeacher> page = new Page<>(current,limit);
        Page<EduTeacher> teacherPage = teacherService.page(page, null);
        long total = teacherPage.getTotal();
        List<EduTeacher> teachers = teacherPage.getRecords();
        Map map = new HashMap();
        map.put("total", total);
        map.put("teachers", teachers);

        return Resp.ok().data(map);
    }

    /**
     * 多条件查询
     * @param current
     * @param limit
     * @param teacherVO
     * @return
     */
    @ApiOperation(value = "多条件查询")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public Resp queryTeacherByTeacherVO(@PathVariable("current") Long current,
                                        @PathVariable("limit")Long limit,
                                        @RequestBody(required = false) EduTeacherVO teacherVO){
        Map result = teacherService.queryTeacherByTeacherVO(current,limit,teacherVO);
        return Resp.ok().data(result);
    }

    /**
     * 根据id讲师信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id讲师信息")
    @GetMapping("queryTeacher/{id}")
    public Resp queryTeacherById(@PathVariable("id") Long id){
        EduTeacher teacher = teacherService.getById(id);
        return Resp.ok().data("teacher",teacher);
    }

    /**
     * 添加讲师信息
     * @param teacher
     * @return
     */
    @ApiOperation(value = "添加讲师信息")
    @PostMapping("addTeacher")
    public Resp addTeacher(@RequestBody EduTeacher teacher){
        boolean saveResult = teacherService.save(teacher);
        return saveResult ? Resp.ok() : Resp.error();
    }

    /**
     * 修改讲师信息
     * @param teacher
     * @return
     */
    @ApiOperation(value = "修改讲师信息")
    @PostMapping("updateTeacher")
    public Resp updateTeacher(@RequestBody EduTeacher teacher){
        boolean updateFlag = teacherService.updateById(teacher);
        return updateFlag ? Resp.ok() : Resp.error();
    }

    /**
     * 根据id逻辑删除 讲师信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id逻辑删除讲师")
    @DeleteMapping("{id}")
    public Resp removeTeacher(@ApiParam(name = "id",value = "讲师id",required = true) @PathVariable("id") String id){
        boolean flag = teacherService.removeById(id);
        return flag ? Resp.ok() : Resp.error();
    }

}

