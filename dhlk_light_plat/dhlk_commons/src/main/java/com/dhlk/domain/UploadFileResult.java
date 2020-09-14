package com.dhlk.domain;

import lombok.Data;

/**
 * Content:
 * Author:jpdong
 * Date:2020/3/5
 */
@Data
public class UploadFileResult {
    /**
     * 上传状态 0-失败，1-成功
     */
    private Integer status;
    /**
     * 文件保存路径
     */
    private String fileSavePath;
    /**
     * 上传失败后的异常
     */
    private Throwable throwable;
}
