package com.oyyo.vod.controller;

import com.oyyo.commonUtils.Resp;
import com.oyyo.vod.service.VodService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    /**
     * 上传视频到阿里云 vod
     * @param file
     * @return
     */
    @PostMapping("uploadAliyunVideo")
    public Resp uploadAliyunVod(MultipartFile file){
        String videoId = vodService.uploadAliyunVod(file);

        return Resp.ok().data("videoId",videoId);
    }

    /**
     * 根据 视频 id 删除视频
     * @param id
     * @return
     */
    @DeleteMapping("deleteVideo/{id}")
    public Resp deleteAliyunVideoByVideoId(@PathVariable("id")String id){
        vodService.deleteAliyunVideoByVideoId(id);
        return  Resp.ok();
    }

    /**
     * 根据 视频 id 批量删除视频
     * @param ids
     * @return
     */
    @DeleteMapping("deleteVideo")
    public Resp deleteAliyunVideoByBatch(List<String> ids){

        vodService.deleteAliyunVideoByVideoId(StringUtils.join(ids,","));
        return  Resp.ok();
    }

    /**
     * 根据视频id获取播放凭证
     * @param videoId
     * @return
     */
        @GetMapping("getPlayerAuth/{videoId}")
    public Resp getPlayerAuth(@PathVariable("videoId")String videoId){
        String auth = vodService.getPlayerAuth(videoId);
        return Resp.ok().data("auth", auth);
    }

}
