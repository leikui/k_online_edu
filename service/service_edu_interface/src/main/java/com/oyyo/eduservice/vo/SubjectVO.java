package com.oyyo.eduservice.vo;

import lombok.Data;

import java.util.List;


@Data
public class SubjectVO {

    private String id;
    private String parentId;
    private String title;
    private List<SubjectVO> children;
}
