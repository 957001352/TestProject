package com.dhlk.entity.light;

import lombok.Data;

/**
 * @Description 开关灯
 * @Author lpsong
 * @Date 2020/6/11
 */
@Data
public class LedRestart {
    private String sn;
    public LedRestart(String sn){
       this.sn=sn;
   }
}