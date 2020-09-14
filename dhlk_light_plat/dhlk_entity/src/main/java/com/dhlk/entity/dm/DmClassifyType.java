package com.dhlk.entity.dm;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 设备类型分类
 * @Author lpsong
 * @Date 2020/4/8
 */
@Data
public class DmClassifyType implements Serializable {
    private Integer id;
    private String name;
    private Integer status;
}