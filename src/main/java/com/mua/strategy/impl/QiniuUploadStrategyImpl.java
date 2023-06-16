package com.mua.strategy.impl;

import com.mua.config.QiniuProperties;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: ASUS XuWei
 * @Since: 2023-06-15 下午 3:41
 * @Comment: qiniu上传策略
 */
@Slf4j
@Service("qiniuUploadStrategyImpl")
public class QiniuUploadStrategyImpl extends AbstractUploadStrategyImpl {

    @Autowired
    private QiniuProperties qiniuProperties;

    @Override
    public Boolean exists(String filePath) {
        //七牛云没找到 文件是否存在的api 所以一直返回false不影响
        return false;
    }

    @Override
    public void upload(String path, String fileName, InputStream inputStream) throws IOException {
        UploadManager qiniuClient = getQiniuClient();
        Auth auth = Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        String upToken = auth.uploadToken(qiniuProperties.getBucketName());
        qiniuClient.put(inputStream, path + fileName, upToken, null, null);
    }

    @Override
    public String getFileAccessUrl(String filePath) {
        return qiniuProperties.getUrl() + filePath;
    }

    /**
     * 获取qiniuToken
     *
     * @return {@link UploadManager} qiniuClient
     */
    public UploadManager getQiniuClient() {
        //构造一个带指定Region对象的配置类，可不配置
        // 存储区域 Region: https://developer.qiniu.com/kodo/1671/region-endpoint-fq
/*        Region region = new Region.Builder()
                //区域 Region ID
                .region("cn-east-2")
                //加速上传
                .accUpHost("upload-cn-east-2.qiniup.com")
                //源站上传
                .srcUpHost("up-cn-east-2.qiniup.com")
                .build();
        Configuration cfg = new Configuration(region);*/
        Configuration cfg = new Configuration();
        //设置请求协议为http
        cfg.useHttpsDomains = false;
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本

        //...其他参数参考类注释
        return new UploadManager(cfg);
    }

}
