package com.dhlk.service;

import com.dhlk.domain.AlarmInfo;
import com.dhlk.domain.AlarmRule;
import com.dhlk.domain.Result;

public interface AlarmService {

    /**
     * 设置报警规则
     * @return
     */
    Result setAlarmRule(AlarmRule alarmRule);


    /**
     * 获取所有报警信息
     * @return
     */
    Result getAlarmInfo(String type, Long createTime, Integer pageNow, Integer limit);


    /**
     * 删除报警信息
     * @return
     */
    Result deleteAlarmInfo(AlarmInfo alarmInfo);

    /**
     * 获取报警规则
     * @return
     */
    Result getAlarmRule();
}
