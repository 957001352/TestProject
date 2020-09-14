package com.dhlk.entity.light;

import lombok.Data;

/**
 * @Description 开关灯
 * @Author lpsong
 * @Date 2020/6/11
 */
@Data
public class LedOnOff {
    private Integer on_off;
    public LedOnOff(Integer on_off){
       this.on_off=on_off;
   }
}