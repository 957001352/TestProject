package com.dhlk.light.factory.dao;

import com.dhlk.entity.light.CloudPeopleFeelStatistics;
import com.dhlk.entity.light.LedOnline;
import com.dhlk.entity.light.LedPower;
import com.dhlk.entity.light.LocalPeopleFeelStatistics;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.LinkedHashMap;
import java.util.List;

/**
 * @创建人 wangq
 * @创建时间 2020/6/16
 * @描述
 */

@Repository
public interface LedPowerDao {

    public Integer insert(List<LedPower> list);

    /**
     * 将从本地MQTT中订阅到的人感上报数据存入数据库
     * @param list
     * @return
     */
    public Integer insertPeopleFeelStatistics(List<LocalPeopleFeelStatistics> list);


    /**
     * 将从本地MQTT中订阅到的人感上报数据存入数据库
     * @param
     * @return
     */
    public Integer savePeopleFeelStatistics(LocalPeopleFeelStatistics localPeopleFeelStatistics);
    /**
     * 统计每10分钟灯消耗的净能耗
     * @param startTime
     * @param endTime
     * @return
     */
    public List<LedPower> ledEnergyStatistics(String startTime, String endTime);

    /**
     * 删除当前一周前的数据
     * @param date
     * @return
     */
    public Integer delete(String date);

    /**
     * 删除上个月的灯能耗数据
     * @return
     */
    //public Integer delete();

    /**
     * 统计灯在线时长
     * @param startTime
     * @param endTime
     * @return
     */
    public List<LedOnline> ledOnLinTime(String startTime, String endTime);


    /**
     * 每天统计人感有人时上报的数据
     * @return
     */
    public List<CloudPeopleFeelStatistics> peopleFeelStatistics();

    /**
     * 本地每小时汇总灯能耗数据存入能耗汇总表
     */
    public Integer insertBySumEnergy(@Param(value = "list") List<LedPower> list,@Param(value = "tenantId") Integer tenantId);

    /**
     * 在线时长
     * @param list
     * @return
     */
    public Integer insertOnlineList(@Param(value = "list") List<LedOnline> list,@Param(value = "tenantId") Integer tenantId);


    /**
     * 查询未发送成功能耗的前100条数据
     * @return
     */
    public List<LedPower> findSendCloudErrorEnergyList();

    /**
     * 更新能耗状态
     */

    public Integer updateEnergyStatus(List<LedPower> list);

    /**
     * 查询未发送成功灯在线的前100条数据
     * @return
     */
    public List<LedOnline> findSendCloudErrorOnLineList();

    /**
     * 更新灯在线状态
     */
    public Integer updateOnLineStatus(List<LedOnline> list);

    /**
     * 更新灯人感发送云状态
     */
    public Integer updatePeopleFellStatus(List<CloudPeopleFeelStatistics> list);

}
