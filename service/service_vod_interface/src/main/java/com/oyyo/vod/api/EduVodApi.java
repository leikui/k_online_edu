package com.oyyo.vod.api;


import com.oyyo.commonUtils.Resp;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * eduVod 相关 api 接口
 */
public interface EduVodApi {

    /**
     * 根据 视频 id 删除视频
     *
     * @param id
     * @return
     */
    @DeleteMapping("eduvod/video/deleteVideo/{id}")
    Resp deleteAliyunVideoByVideoId(@PathVariable("id") String id);

}
