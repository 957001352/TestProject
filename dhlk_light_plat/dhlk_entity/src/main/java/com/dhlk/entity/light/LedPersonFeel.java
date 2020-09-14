package com.dhlk.entity.light;

import lombok.Data;

/**
 * @Description 灯人感
 * @Author lpsong
 * @Date 2020/6/9
 */
@Data
public class LedPersonFeel {
    private String sn;//灯sn
    private Integer status;//开启-0，关闭-1
    private Integer timelong;//时间
    private Integer value;//人感触发阈值
    private Integer minvalue;//人感目标最低亮度
    private Integer maxvalue;//人感目标最高亮度

}