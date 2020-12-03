package com.dhlk.light.service.service;

import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.Result;
import com.dhlk.entity.api.ApiList;
import com.dhlk.entity.light.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description 灯管理接口
 * @Author lpsong
 * @Date 2020/6/4
 */
public interface LedService {
    /**
     * 新增/修改
     */
    Result save(Led led);

    /**
     * 灯闪一闪
     * @param sns
     * @return
     */
    Result flashingLed(String sns);
    /**
     * 物理删除
     * @param id
     */
    Result delete(String id);
    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     */
    Result findPageList(String sn,String areaId,String computerId, Integer pageNum, Integer pageSize);

    /**
     * 列表查询
     */
    Result findList(String sn,String areaId,String computerId);


    /**
     * 能耗统计
     */
    Result savePower(List<LedPower> list);

    /**
     * 在线时长统计
     */
    Result saveOnline(LedOnline ledOnline);

    /**
     * 人感统计
     */
    Result savePeopleList(String json);


    /**
     * 批量插入在线时长
     * @param list
     * @return
     */
    Result saveOnlineList(List<LedOnline> list);

    /**
     * 增加开关与灯绑定关系
     */
    Result saveSwitchBoundLed(Switch swich);

    /**
     * 设置灯亮度
     */
    Result setLedBrightness(String sns,String brightness);


    /**
     * 查询有灯的区域
     */
    Result findAreasByLed();

    /**
     * 开关灯
     */
    Result openOrCloseLed(String sns, String status);
    /**
     * 灯重启
     */
    Result ledRestart(String sns);

    /**
     * 开关重启
     */
    Result switchRestart(String sns);



    /**
     * 获取照明设备故障信息
     */
    Result findLedFault(String ledSn);

    /**
     * 导出列表查询
     */
    Result findExportList(String ledSn);

    /**
     * 根据租户Id获取灯信息
     * @param tenantId
     * @return
     */
    Result findListByTenantId(Integer tenantId);

    /**
     * 修改
     * @param led
     * @return
     */
    Result update(Led led);
    /**
     * 修改位置
     * @param leds
     * @return
     */
    Result updateLocation(List<Led> leds);





    /**
     * 获取灯的配置参数
     * @param sns
     */
    Result findLedParamInfos(String sns);

    Result findLedRecord( String sn,String commond,String sendResult,String backResult,String startTime,String endTime,Integer pageNum,Integer pageSize);

    /**
     * 未添加灯具
     * @return
     */
    Result notAddLed();

    /**
     *
     * 预设亮度显示
     * @return
     */
    Result brightnessShow();

    /**
     * 根据区域找灯
     * @param areaId
     * @return
     */
    Result findLedsByArea(String areaId);

    /**
     * 显示图标大小
     * @return
     */
    Result showIconSize();

    Result syncLedBrightness(String brightness);
}
