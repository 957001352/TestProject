package com.dhlk.domain;

import lombok.Data;

/**
 * @Description 上传附件
 * @Author lpsong
 * @Date 2020/3/31
 */
@Data
public class BaseFile {
    private String id;
    private String name;
    private String saveName;
    private String path;
    private String webPath;
    private String suffix;
    private String dataId;
}
