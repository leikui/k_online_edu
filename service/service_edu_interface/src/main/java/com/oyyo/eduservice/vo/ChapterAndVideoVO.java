package com.oyyo.eduservice.vo;

import lombok.Data;

import java.util.List;

/**
 * 章节小节实体
 * @ClassName: ChapterAndVideoVO
 * @Description:
 * @Author: LiKui
 * @Date: 2020-6-28 15:18
 * @Version: 1.0
 */
@Data
public class ChapterAndVideoVO {
    private String id;
    private String title;
    private String videoSourceId;
    private List<ChapterAndVideoVO> children;

}
