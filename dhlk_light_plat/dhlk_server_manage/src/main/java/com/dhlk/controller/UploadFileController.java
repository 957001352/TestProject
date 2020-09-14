package com.dhlk.controller;


import com.dhlk.domain.Result;
import com.dhlk.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件上传
 */
@RestController
@RequestMapping("/fileUpload")
public class UploadFileController {

    @Autowired
    private FileService fileService;


    @CrossOrigin
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result upload(@RequestPart(value = "file") MultipartFile file, @RequestParam String key, @RequestParam String pid) throws IOException {
        return fileService.upload(file, key,pid);
    }
}
