package com.dhlk.entity.light;

import lombok.Data;

/**
 * @Description  亮度设置
 * @Author lpsong
 * @Date 2020/6/11
 */
@Data
public class LedBrightness {
    private Integer shine;
    public LedBrightness(Integer shine){
       this.shine=shine;
   }
}