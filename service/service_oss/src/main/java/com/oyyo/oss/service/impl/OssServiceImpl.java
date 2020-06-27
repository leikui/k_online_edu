package com.oyyo.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.oyyo.oss.service.OssService;
import com.oyyo.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    /**
     * 上传头像
     * @param file
     * @return
     */
    @Override
    public String uploadAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        //文件名称
        String fileName = UUID.randomUUID().toString().replaceAll("-","")
               + file.getOriginalFilename();

        //文件夹
        String dir = new DateTime().toString("yyyy/MM/dd");

        fileName = dir + "/" +fileName;

        // 上传文件流。
        try (InputStream inputStream = file.getInputStream()) {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient。
            String fileUrl = "https://" + bucketName +"." + endpoint + "/" + fileName;
            ossClient.shutdown();
            return fileUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
