package com.dhlk.light.entity;

import lombok.Data;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/23
 * Time:10:14
 * @Description:
 */
@Data
public class KMeansLight {

    /**
     * 第一类
     */
    private String clusterOne;
    /**
     * 二类
     */
    private String clusterTwo;
    /**
     * 第三类
     */
    private String clusterThree;
    /**
     * 各类作比较
     */
    private String clusterValue;
    /**
     * 租户
     */
    private String factoryCode;
}
