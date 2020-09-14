package com.dhlk.entity.basicmodule;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: jzhao
 * @Date: 2020/4/13 16:09
 * @Description: 文件信息
 */
@Data
public class FileInfo implements Serializable {
    private String fileName; //文件名称
    private String filePath; //文件路径
    private String fileSize; //文件大小
    private Boolean isFile;
}
