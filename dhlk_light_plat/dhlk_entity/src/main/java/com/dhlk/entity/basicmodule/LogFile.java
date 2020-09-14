package com.dhlk.entity.basicmodule;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: jzhao
 * @Date: 2020/4/13 16:09
 * @Description: 日志文件
 */
@Data
public class LogFile implements Serializable {
    private String fileName; //文件名称
    private String filePath; //文件路径
    private Integer fileSize; //文件大小
}
