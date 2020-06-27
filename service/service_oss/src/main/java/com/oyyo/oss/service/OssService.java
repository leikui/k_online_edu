package com.oyyo.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface OssService {
    /**
     * 上传头像
     * @param file
     * @return
     */
    String uploadAvatar(MultipartFile file) throws FileNotFoundException;
}
