package com.dhlk.entity.light;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.io.Serializable;

/**
 * 定时任务详情对象 dhlk_light_task_scheduler_log
 * 
 * @author ruoyi
 * @date 2020-06-04
 */
@Data
public class TaskSchedulerLog implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;

    /** 执行结果 */
    private String result;

    /** 定时任务id */
    private Integer scheduleId;

    /** 定时任务id */
    private String createTime;

    /** 定时详情id */
    private Integer schedulerDetailId;

}
