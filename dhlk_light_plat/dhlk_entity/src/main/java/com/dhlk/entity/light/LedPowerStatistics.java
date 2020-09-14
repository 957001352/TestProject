package com.dhlk.entity.light;/**
 * @创建人 wangq
 * @创建时间 2020/6/8
 * @描述
 */

import io.swagger.annotations.ApiModel;
import lombok.Data;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @program: dhlk.multi.tenant
 *
 * @description: 灯能耗统计实体
 *
 * @author: wqiang
 *
 * @create: 2020-06-08 15:09
 **/

@Data
@ApiModel(value = "LedPowerStatistics", description = "灯能耗统计实体")
public class LedPowerStatistics implements Serializable {

    private String time;      //日期
    private String area;    //区域
    private String led_switch;   //开关
    private BigDecimal energy; //能耗


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLed_switch() {
        return led_switch;
    }

    public void setLed_switch(String led_switch) {
        this.led_switch = led_switch;
    }

    public BigDecimal getEnergy() {
        return energy;
    }

    public void setEnergy(BigDecimal energy) {
        this.energy = energy;
    }
}
