package com.dhlk.light.entity;

import lombok.Data;

import javax.print.DocFlavor;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/26
 * Time:8:52
 * @Description:
 */
@Data
public class LightLocation {

    /**
     * ID
     */
    private int id;

    /**
     * sn
     */
    private String sn;

    /**
     * ip
     */
    private String ip;

    /**
     * x坐标
     */
    private String xaxis;

    /**
     * y坐标
     */
    private String yaxis;

    /**
     * 亮度
     */
    private int brightness;

    /**
     * 人感感应亮度
     */
    private int indBright;

    /**
     * 人感未感应亮度
     */
    private int unindBright;

    /**
     * 人感感应时间
     */
    private int indTime;

    /**
     * 人感感应开启状态0-未开启1-开启
     */
    private int indStatus;

    /**
     * 添加时间
     */
    private int createTime;

    /**
     * 租户
     */
    private int tenantId;

    /**
     * 区域
     */
    private String areaId;

    /**
     * 别名
     */
    private String alias;

    /**
     * 灯状态 0未删除 1已删除
     */
    private int status;

    private String area;

}
