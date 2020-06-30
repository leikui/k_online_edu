package com.oyyo.eduservice.controller;


import com.oyyo.commonUtils.Resp;
import com.oyyo.eduservice.entity.EduVideo;
import com.oyyo.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author oy
 * @since 2020-06-27
 */
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    /**
     * 添加小节
     * @param video
     * @return
     */
    @PostMapping("addVideo")
    public Resp addVideo(@RequestBody EduVideo video){
        videoService.save(video);
        return Resp.ok();
    }

    /**
     * 根据id查询小节
     * @param videoId
     * @return
     */
    @GetMapping("{videoId}")
    public Resp getVideoInfo(@PathVariable("videoId")String videoId){
        EduVideo video = videoService.getById(videoId);

        return Resp.ok().data("video", video);
    }

    /**
     * 修改小节
     * @param video
     * @return
     */
    @PostMapping("updateVideo")
    public Resp updateVideo(@RequestBody EduVideo video){
        videoService.updateById(video);
        return Resp.ok();
    }

    /**
     * 删除小节
     * TODO
     * @param videoId
     * @return
     */
    @DeleteMapping("{videoId}")
    public Resp deleteVideo(@PathVariable("videoId") String videoId){
        videoService.removeById(videoId);
        return Resp.ok();
    }


}

