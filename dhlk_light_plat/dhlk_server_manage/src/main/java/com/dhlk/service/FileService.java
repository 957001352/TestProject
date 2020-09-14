package com.dhlk.service;


import com.dhlk.domain.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件上传接口
 */
public interface FileService {
    Result upload(MultipartFile file, String key, String pid) throws IOException;
}
