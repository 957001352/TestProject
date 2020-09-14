package com.dhlk.entity.basicmodule;

import lombok.Data;

import java.io.Serializable;

/**
 * 网络和生产设备关系配置
 */
@Data
public class ProductNet implements Serializable {
    private Integer id;
    private Integer netId;//网络设备Id
    private Integer productId;//生产设备Id
    private Integer type;//1大数据一体机，2,3,4BI控制器
    private Integer tenantId;//租户
}
