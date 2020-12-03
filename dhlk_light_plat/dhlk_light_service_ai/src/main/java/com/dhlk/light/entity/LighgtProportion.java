package com.dhlk.light.entity;

import lombok.Data;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/26
 * Time:17:06
 * @Description:
 */
@Data
public class LighgtProportion {

    private double good;
    private double general;
    private double dnager;

    private Integer goodCount;
    private Integer generalCount;
    private Integer dangerCount;


}
