package com.shen.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.shen.oss.service.OssService;
import com.shen.oss.utils.ConstantPropertiesUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @Author: shenge
 * @Date: 2020-05-09 11:26
 * <p>
 * 上传业务。
 */

@Service
public class OssServiceImpl implements OssService {

    //上传头像到阿里云
    @Override
    public String uploadAvatar(MultipartFile file) {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String buckName = ConstantPropertiesUtils.BUCKET_NAME;

        try {

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            InputStream inputStream = file.getInputStream();
            //由于不同用户都要上传头像，头像名称可能相同，所以我们需要确保头像图片的唯一性，加一个UUID随机数。
            String filename = UUID.randomUUID().toString().replaceAll("-", "") + file.getOriginalFilename();

            //获取当前格式化日期
            String timeNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            filename = timeNow + "/" + filename;

            // 创建PutObjectRequest对象。
            // 第一个参数：包名
            // 第二个参数：获取真实名称，也就是头像的图片名称，用于访问阿里云的路径拼接，可以拼上'/aa/bb/1.jpg'的形式来进行文件夹分类。
            // 第三个参数：获取输入流
            PutObjectRequest putObjectRequest = new PutObjectRequest(buckName, filename, inputStream);

            // 上传文件。
            ossClient.putObject(putObjectRequest);

            // 关闭OSSClient。
            ossClient.shutdown();

            // 此时需要将访问资源的阿里云路径获取过来
            //https://dasi-edu.oss-cn-beijing.aliyuncs.com/%E6%88%AA%E5%B1%8F2020-03-2016.09.18.png   这是路径的一般形式，我们手动给它拼接上。
            String url = "https://" + buckName + "." + endpoint + "/" + filename;

            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
