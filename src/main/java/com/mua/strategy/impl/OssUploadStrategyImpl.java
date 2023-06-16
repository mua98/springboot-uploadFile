package com.mua.strategy.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.mua.config.OssProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * oss上传策略
 *
 * @Author: ASUS XuWei
 */
@Slf4j
@Service("ossUploadStrategyImpl")
public class OssUploadStrategyImpl extends AbstractUploadStrategyImpl {

    @Autowired
    private OssProperties ossProperties;

    @Override
    public Boolean exists(String filePath) {
        return getOssClient().doesObjectExist(ossProperties.getBucketName(), filePath);
    }

    @Override
    public void upload(String path, String fileName, InputStream inputStream) {
        OSS ossClient = getOssClient();
        try {
            //调用oss方法上传到阿里云
            //第一个参数：Bucket名称
            //第二个参数：上传到oss文件路径和文件名称
            //第三个参数：上传文件输入流
            ossClient.putObject(ossProperties.getBucketName(), path + fileName, inputStream);
        } catch (OSSException oe) {
            log.error("Error Message:" + oe.getErrorMessage());
            log.error("Error Code:" + oe.getErrorCode());
            log.info("Request ID:" + oe.getRequestId());
            log.info("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            log.error("Caught an ClientException, Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    @Override
    public String getFileAccessUrl(String filePath) {
        String url = ossProperties.getUrl().isEmpty() ? ossProperties.getEndpoint() : ossProperties.getUrl();
        return url + filePath;
    }

    /**
     * 获取ossClient
     *
     * @return {@link OSS} ossClient
     */
    private OSS getOssClient() {
        return new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret());
    }
}
