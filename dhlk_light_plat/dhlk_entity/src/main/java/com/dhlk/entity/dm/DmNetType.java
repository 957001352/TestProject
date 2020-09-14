package com.dhlk.entity.dm;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 网络设备类型
 * @Author lpsong
 * @Date 2020/4/8
 */
@Data
public class DmNetType implements Serializable {
    private Integer id;
    private String name;
    private Integer status;
}