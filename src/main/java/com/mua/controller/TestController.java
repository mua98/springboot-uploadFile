package com.mua.controller;

import com.mua.strategy.context.UploadStrategyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.mua.enums.FilePathEnum.PHOTO;

/**
 * @Author: ASUS XuWei
 * @Since: 2023-06-15 上午 11:37
 * @Comment: oss测试
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private UploadStrategyContext uploadStrategyContext;


    /**
     * 上传文件
     * @param file  文件
     * @return 文件访问路径
     */
    @PostMapping("uploadFile")
    public String uploadFile(MultipartFile file) {
        return uploadStrategyContext.executeUploadStrategy(file, PHOTO.getPath());
    }
}
