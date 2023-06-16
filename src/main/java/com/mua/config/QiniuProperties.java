package com.mua.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: ASUS XuWei
 * @Since: 2023-06-15 下午 3:44
 * @Comment:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "upload.qiniu")
public class QiniuProperties {

    /**
     * qiniu域名
     */
    private String url;

    /**
     * AK
     */
    private String accessKey;

    /**
     * SK
     */
    private String secretKey;

    /**
     * bucket名称
     */
    private String bucketName;
}
