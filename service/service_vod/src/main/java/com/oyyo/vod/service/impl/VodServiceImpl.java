package com.oyyo.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.oyyo.serviceBase.handler.BaseException;
import com.oyyo.vod.service.VodService;
import com.oyyo.vod.utils.ConstantVodPropertiesUtil;
import com.oyyo.vod.utils.InitVodClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
public class VodServiceImpl implements VodService {


    /**
     * 上传视频到阿里云 vod
     * @param file
     * @return
     */
    @Override
    public String uploadAliyunVod(MultipartFile file) {
        String videoId = null;
        try (InputStream inputStream = file.getInputStream();){
            String fileName = UUID.randomUUID().toString() + file.getOriginalFilename() ;
            String title = fileName.substring(0,fileName.lastIndexOf("."));

            videoId = uploadByStream(ConstantVodPropertiesUtil.ACCESS_ID, ConstantVodPropertiesUtil.ACCESS_KEY, title, fileName, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return videoId;
    }

    /**
     * 根据 视频 id 删除视频
     * @param id
     */
    @Override
    public void deleteAliyunVideoByVideoId(String id) {
        //初始化 客户端 client
        DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodPropertiesUtil.ACCESS_ID, ConstantVodPropertiesUtil.ACCESS_KEY);
        //创建删除视频 请求对象
        DeleteVideoRequest request = new DeleteVideoRequest();
        //设置删除视频 id
        log.info("删除的视频 id 为：{}",id);
        request.setVideoIds(id);
        //调用client方法删除
        try {
            client.getAcsResponse(request);
            log.info("删除成功，视频 id 为：{}",id);
        } catch (ClientException e) {
            log.info("删除失败，视频 id 为：{}",id);
            e.printStackTrace();
            throw new BaseException(20001, "删除视频失败");
        }
    }

    /**
     * 根据视频id 获取播放凭证
     * @param videoId
     * @return
     */
    @Override
    public String getPlayerAuth(String videoId) {
        try {
            //初始化 客户端 client
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodPropertiesUtil.ACCESS_ID, ConstantVodPropertiesUtil.ACCESS_KEY);
            GetVideoPlayAuthRequest authRequest = new GetVideoPlayAuthRequest();
            authRequest.setVideoId(videoId);
            GetVideoPlayAuthResponse acsResponse = client.getAcsResponse(authRequest);
            log.info("获取视频 id 为：[{}] 的播放凭证",videoId);
            String auth = acsResponse.getPlayAuth();
            log.info("视频id 为：[{}] 的播放凭证为：[{}]",videoId,auth);
            return auth;
        } catch (ClientException e) {
            throw new BaseException(20001, "获取视频播放凭证失败");
        }

    }

    /**
     * 流式上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     * @param inputStream
     */
    private static String uploadByStream(String accessKeyId, String accessKeySecret, String title, String fileName, InputStream inputStream) {
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
        /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
        //request.setShowWaterMark(true);
        /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
        //request.setCallback("http://callback.sample.com");
        /* 自定义消息回调设置，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
        //request.setUserData(""{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}"");
        /* 视频分类ID(可选) */
        //request.setCateId(0);
        /* 视频标签,多个用逗号分隔(可选) */
        //request.setTags("标签1,标签2");
        /* 视频描述(可选) */
        //request.setDescription("视频描述");
        /* 封面图片(可选) */
        //request.setCoverURL("http://cover.sample.com/sample.jpg");
        /* 模板组ID(可选) */
        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
        /* 工作流ID(可选) */
        //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
        /* 存储区域(可选) */
        //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
        /* 开启默认上传进度回调 */
        // request.setPrintProgress(true);
        /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
        // request.setProgressListener(new PutObjectProgressListener());
        /* 设置应用ID*/
        //request.setAppId("app-1000000");
        /* 点播服务接入点 */
        //request.setApiRegionId("cn-shanghai");
        /* ECS部署区域*/
        // request.setEcsRegionId("cn-shanghai");
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        log.info("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        String videoId;
        if (response.isSuccess()) {
            videoId = response.getVideoId();
            log.info("VideoId=" + response.getVideoId() + "\n");
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            log.info("VideoId=" + response.getVideoId() + "\n");
            log.info("ErrorCode=" + response.getCode() + "\n");
            log.info("ErrorMessage=" + response.getMessage() + "\n");
            videoId = response.getVideoId();
        }
        return videoId;
    }
}
