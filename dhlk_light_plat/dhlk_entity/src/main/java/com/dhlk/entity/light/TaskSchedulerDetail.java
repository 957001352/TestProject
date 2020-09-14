package com.dhlk.entity.light;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 定时任务详情对象 dhlk_light_task_scheduler_detail
 * 
 * @author jlv
 * @date 2020-06-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskSchedulerDetail implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;

    /** 定时任务id */
    private Integer scheduleId;

    /** 时间 */
    private String time;

    /** 定时格式 */
    private String expression;

    /** 操作 */
    private Integer onOff;

    /** 亮度 */
    private Integer brightness;

}
