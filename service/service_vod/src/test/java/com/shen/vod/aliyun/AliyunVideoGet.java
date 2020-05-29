package com.shen.vod.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.shen.vod.utils.KeyAndSecret;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author: shenge
 * @Date: 2020-05-14 13:36
 * <p>
 * 拿到阿里云视频信息
 */

@SpringBootTest
public class AliyunVideoGet {

    //获取视频播放地址信息。
    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("edc24aff799e4b6f9dca5dbded5f6718");
        return client.getAcsResponse(request);
    }

    /**
     * 通过id拿到播放地址，但是这个视频如果加密了的话不能使用播放地址进行播放，所以并没有什么意义。
     */
    @Test
    public void getPlayVideo() {
        DefaultAcsClient client = null;
        try {
            client = InitVideo.initVodClient(KeyAndSecret.KEY_ID, KeyAndSecret.KEY_SECRET);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            response = getPlayInfo(client);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //得到视频的播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息，得到视频的名字
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    //获取视频播放凭证信息。
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("a9e03a8488004a868adbb8de4632e3a1");
        return client.getAcsResponse(request);
    }

    /**
     * 通过id拿到视频的播放凭证信息，一般我们调用这个
     */
    @Test
    public void getOath() {
        DefaultAcsClient client = null;
        try {
            client = InitVideo.initVodClient(KeyAndSecret.KEY_ID, KeyAndSecret.KEY_SECRET);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            response = getVideoPlayAuth(client);
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
}
