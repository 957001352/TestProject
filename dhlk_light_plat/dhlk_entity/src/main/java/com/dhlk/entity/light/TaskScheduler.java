package com.dhlk.entity.light;

import com.dhlk.enums.SystemEnums;
import com.dhlk.utils.CheckUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 定时任务对象 dhlk_light_task_scheduler
 * 
 * @author ruoyi
 * @date 2020-06-04
 */
@Data
public class TaskScheduler implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;

    /** 任务名称 */
    private String name;

    /** 任务描述 */
    private String note;

    /** 任务状态 */
    private Integer status;

    /** 区域id */
    private String areaId;

    /** 区域名称 */
    private String areaName;

    /** 开关id */
    private Integer switchId;

    /** 开关名称 */
    private String switchName;

    /** 定时周期 */
    private String scheduleCycle;

    /** 定时周期 */
    private String scheduleCycleWeek;

    private Integer tenantId;//租户id

    public String getScheduleCycle() {
        return scheduleCycle;
    }

    public void setScheduleCycle(String scheduleCycle) {
        this.scheduleCycle = scheduleCycle;
    }

    public String getScheduleCycleWeek() {
        if(!CheckUtils.isNull(getScheduleCycle())){
            String cycle[] = getScheduleCycle().split(",");
            StringBuilder builder=new StringBuilder();
            for(String str:cycle){
                if(str.equals(SystemEnums.MONDAY.getState().toString())){
                    builder.append(SystemEnums.MONDAY.getStateInfo()+",");
                }else  if(str.equals(SystemEnums.TUESDAY.getState().toString())){
                    builder.append(SystemEnums.TUESDAY.getStateInfo()+",");
                }else  if(str.equals(SystemEnums.WEDNESDAY.getState().toString())){
                    builder.append(SystemEnums.WEDNESDAY.getStateInfo()+",");
                }else  if(str.equals(SystemEnums.THURSDAY.getState().toString())){
                    builder.append(SystemEnums.THURSDAY.getStateInfo()+",");
                }else  if(str.equals(SystemEnums.FRIDAY.getState().toString())){
                    builder.append(SystemEnums.FRIDAY.getStateInfo()+",");
                }else  if(str.equals(SystemEnums.SATURDAY.getState().toString())){
                    builder.append(SystemEnums.SATURDAY.getStateInfo()+",");
                }else  if(str.equals(SystemEnums.SUNDAY.getState().toString())){
                    builder.append(SystemEnums.SUNDAY.getStateInfo()+",");
                }
            }
            return builder.substring(0,builder.length()-1).toString();
        }
        return null;
    }

    private List<TaskSchedulerDetail> taskSchedulerDetailList;

}
