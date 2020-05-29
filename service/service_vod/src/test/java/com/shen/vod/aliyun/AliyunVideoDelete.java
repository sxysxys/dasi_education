package com.shen.vod.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.shen.vod.utils.KeyAndSecret;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: shenge
 * @Date: 2020-05-14 19:25
 */
@SpringBootTest
public class AliyunVideoDelete {
    /**
     * 删除视频
     *
     * @param client 发送请求客户端
     * @return DeleteVideoResponse 删除视频响应数据
     * @throws Exception
     */
    public static DeleteVideoResponse deleteVideo(DefaultAcsClient client) throws Exception {
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds("cc4ac89c923043c1a4d5203db9cce047");
        return client.getAcsResponse(request);
    }

    /*请求示例*/
    @Test
    public void deleteVideo() {
        DefaultAcsClient client = null;
        try {
            client = InitVideo.initVodClient(KeyAndSecret.KEY_ID, KeyAndSecret.KEY_SECRET);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        DeleteVideoResponse response = new DeleteVideoResponse();
        try {
            response = deleteVideo(client);
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
}
