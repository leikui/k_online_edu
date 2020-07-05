package com.oyyo.eduservice.controller;


import com.oyyo.commonUtils.Resp;
import com.oyyo.eduservice.entity.EduVideo;
import com.oyyo.eduservice.feign.ServiceVodClient;
import com.oyyo.eduservice.service.EduVideoService;
import com.oyyo.serviceBase.handler.BaseException;
import com.oyyo.vod.api.EduVodApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Slf4j
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;
    @Autowired
    private ServiceVodClient vodClient;

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
        //根据 小节 id  查询视频id
        EduVideo eduVideo = videoService.getById(videoId);
        if (eduVideo == null) {
            return Resp.error();
        }

        String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            log.info("远程调用删除阿里云视频，视频id为：[{}]",videoSourceId);
            Resp resultResp = vodClient.deleteAliyunVideoByVideoId(videoSourceId);
            if (resultResp.getCode() != 20000){

                throw new BaseException(20001, "删除视频失败！熔断器...");
            }
        }
        log.info("删除表中小节信息，小节 id：[{}]",videoId);
        videoService.removeById(videoId);
        return Resp.ok();
    }


}

