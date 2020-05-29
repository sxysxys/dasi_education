package com.shen.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.shen.servicebase.exception.CustomizeException;
import com.shen.vod.componet.InitVideo;
import com.shen.vod.service.VodService;
import com.shen.vod.utils.KeyAndSecret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: shenge
 * @Date: 2020-05-14 20:06
 */
@Service
public class VodServiceImpl implements VodService {

    /**
     * 上传视频小节
     *
     * @param file
     * @return
     */
    @Override
    public String uploadVideo(MultipartFile file) {

        String filename = file.getOriginalFilename();
        String title = filename.substring(0, filename.lastIndexOf("."));
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UploadStreamRequest request = new UploadStreamRequest(KeyAndSecret.KEY_ID, KeyAndSecret.KEY_SECRET, title, filename, inputStream);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
//        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        String videoId = response.getVideoId();
        if (!response.isSuccess() && videoId == null) {
            throw new CustomizeException(20001, response.getMessage());
        }
        return videoId;
    }


    /**
     * 删除视频小节
     *
     * @param videoId
     */
    @Override
    public void deleteVideo(String videoId) {
        DefaultAcsClient client = null;
        try {
            client = initVodClient(KeyAndSecret.KEY_ID, KeyAndSecret.KEY_SECRET);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        DeleteVideoResponse response = new DeleteVideoResponse();
        try {
            response = deleteVideo(client, videoId);
        } catch (Exception e) {
            throw new CustomizeException(20001, e.getLocalizedMessage());
        }
//        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    //通过id拿到auth
    @Override
    public String getVideoAuth(String videoSourceId) {
        DefaultAcsClient client = null;
        try {
            client = initVodClient(KeyAndSecret.KEY_ID, KeyAndSecret.KEY_SECRET);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        GetVideoPlayAuthResponse response;
        try {
            response = getVideoPlayAuth(client,videoSourceId);
            //播放凭证
            return response.getPlayAuth();
        } catch (Exception e) {
            throw new CustomizeException(20001,"获取凭证失败");
        }
    }


    //初始化客户端
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    //返回删除response
    public static DeleteVideoResponse deleteVideo(DefaultAcsClient client, String videoId) throws Exception {
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(videoId);
        return client.getAcsResponse(request);
    }

    //获取视频播放凭证信息。
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client,String videoId) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }
}
