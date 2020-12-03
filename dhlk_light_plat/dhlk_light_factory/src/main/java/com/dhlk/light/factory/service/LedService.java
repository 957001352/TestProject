package com.dhlk.light.factory.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.IntensityInfo;
import com.dhlk.entity.light.PeopleFeelInfo;
import com.dhlk.entity.light.LedWifiInfo;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/17
 */
public interface LedService {

   /**
   *  灯开关
    * @param sns
    * @param status
   * @return
   */
   Result openOrCloseLed(String sns, Integer status);



   /**
   *  灯亮度设置
    * @param sns
    * @param brightness
   * @return
   */
   Result setLedBrightness(String sns,String brightness);

   /**
    * 灯闪一闪
    * @param sns
    * @return
    */
   Result flashingLed(String sns);


   /**
    * 灯重启
    * @param sns
    * @return
    */
   Result ledRestart(String sns);


   /**
   * 设置组
    * @param sns
    * @param grpId
   * @return
   */
   Result setLedGrpId(String sns,Integer grpId);


   /**
   * 查询所有有灯的区域
    * @param
   * @return
   */
   Result findAreasByLed();

   /**
    * 列表查询
    */
   Result findList(String sn,String areaId,String computerId);

   /**
    * 光感控制
    */
    Result lightSensationContro(String sns, IntensityInfo intensityInfo);

   /**
    * 人感控制
    */
   Result peopleFeelContro(String sns, PeopleFeelInfo peopleFeelInfo);

   /**
    * wifi设置
    */
    Result wifiContro(String sns, LedWifiInfo ledWifiInfo);


   /**
   * 获取wifi配置
    * @param sns
   * @return 
   */
   Result  loadWifiConfig(String sns);

   Result findLedRecord( String sn,String commond,String sendResult,String backResult,String startTime,String endTime,Integer pageNum,Integer pageSize);

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
}
