package com.oyyo.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    /**
     * 上传视频到阿里云 vod
     * @param file
     * @return
     */
    String uploadAliyunVod(MultipartFile file);

    /**
     * 根据 视频 id 删除视频
     * @param id
     */
    void deleteAliyunVideoByVideoId(String id);

    /**
     * 根据视频id 获取播放凭证
     * @param videoId
     * @return
     */
    String getPlayerAuth(String videoId);
}
