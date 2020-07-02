package com.oyyo.vod.controller;

import com.oyyo.commonUtils.Resp;
import com.oyyo.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}
