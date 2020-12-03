package com.dhlk.light.entity;

import lombok.Data;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/23
 * Time:12:29
 * @Description: 包装聚类之后返回的数据，split之后放入数组返回
 */
@Data
public class InfoBox {

    String [] clusterOne;
    String [] clusterTwo;
    String [] clusterThree;

}
