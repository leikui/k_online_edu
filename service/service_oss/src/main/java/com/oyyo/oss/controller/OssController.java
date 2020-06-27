package com.oyyo.oss.controller;


import com.oyyo.commonUtils.Resp;
import com.oyyo.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("edu/oss")
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 上传头像
     * @param file
     * @return
     */
    @PostMapping("upload")
    public Resp uploadAvatarOss(MultipartFile file) throws FileNotFoundException {
        String avatarUrl = ossService.uploadAvatar(file);
        return Resp.ok().data("url",avatarUrl);
    }
}
