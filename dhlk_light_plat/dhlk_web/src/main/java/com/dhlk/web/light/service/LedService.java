package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.*;
import com.dhlk.web.light.service.fbk.ConstructionServiceFbk;
import com.dhlk.web.light.service.fbk.LedServiceFbk;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description 灯管理接口
 * @Author lpsong
 * @Date 2020/6/4
 */
@FeignClient(value = "light-service/led", fallback = LedServiceFbk.class)
public interface LedService {
    /**
     * 新增/修改
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody Led led);

    /**
    * 闪一闪
     * @param sns
    */
    @PostMapping(value = "/flashingLed")
    Result flashingLed(@RequestBody InfoBox<String> infoBox);

    /**
     * 物理删除
     * @param id
     */
    @GetMapping(value = "/delete")
    Result delete(@RequestParam(value = "id") String id);

    /**
     * 列表查询
     */
    @GetMapping("/findList")
    Result findList(@RequestParam(value = "sn", required = false) String sn,
                    @RequestParam(value = "areaId", required = false) String areaId,
                    @RequestParam(value = "switchId", required = false) String switchId);


    @PostMapping(value = "/savePower")
    Result savePower(@RequestBody List<LedPower> list);


    @PostMapping(value = "/saveOnlineList")
    Result saveOnlineList(List<LedOnline> list);


    /**
     * 新增灯在线时长
     * @param ledOnline
     * @return
     */
    @PostMapping(value = "/saveOnline")
    Result saveOnline(@RequestBody LedOnline ledOnline);

    /**
     * 增加开关与灯绑定关系
     * @param swich
     * @return
     */
    @PostMapping(value = "/saveSwitchBoundLed")
    Result saveSwitchBoundLed(@RequestBody Switch swich);

    /**
     * 设置灯亮度
     * @param infoBox
     * @return
     */
    @PostMapping(value = "/setLedBrightness")
    Result setLedBrightness(@RequestBody InfoBox<String> infoBox);

    /**
     * 查询有灯的区域
     * @return
     */
    @GetMapping(value = "/findAreasByLed")
    Result findAreasByLed();

    /**
     * 开关灯
     * @return
     */
    @PostMapping(value = "/openOrCloseLed")
    Result openOrCloseLed(@RequestBody InfoBox<String> infoBox);

    /**
     * 灯重启
     * @return
     */
    @PostMapping(value = "/ledRestart")
    Result ledRestart(@RequestBody InfoBox<String> infoBox);

    @PostMapping(value = "/switchRestart")
    Result switchRestart(@RequestBody InfoBox<String> infoBox);
    /**
     * 获取照明设备故障信息
     * @return
     */
    @GetMapping(value = "/findLedFault")
    Result findLedFault(@RequestParam("ledSn") String ledSn);

    /**
     * 导出列表查询
     */
    @GetMapping("/findExportList")
    Result<Object> findExportList(@RequestParam("ledSn") String ledSn);

    /**
     * 根据租户Id获取灯信息
     * @param tenantId
     * @return
     */
    @GetMapping("/findListByTenantId")
    Result findListByTenantId(@RequestParam(value = "tenantId", required = false) Integer tenantId);

    /**
     * 修改
     * @param led
     * @return
     */
    @PostMapping("/update")
    Result update(@RequestBody Led led);

    /**
     * 获取灯的配置参数
     * @param infoBox
     */
    @PostMapping("/findLedParamInfos")
    Result findLedParamInfos(@RequestBody InfoBox<String> infoBox);


    @GetMapping(value = "/findLedRecord")
    Result findLedRecord(@RequestParam(value = "sn", required = false) String sn,
                         @RequestParam(value = "commond", required = false) String commond,
                         @RequestParam(value = "sendResult", required = false) String sendResult,
                         @RequestParam(value = "backResult", required = false) String backResult,
                         @RequestParam(value = "startTime", required = false) String startTime,
                         @RequestParam(value = "endTime", required = false) String endTime,
                         @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);

    /**
     * 未添加灯具
     * @return
     */
    @GetMapping(value = "/notAddLed")
    Result notAddLed();

    /**
     * 预设亮度显示
     * @return
     */
    @GetMapping(value = "/brightnessShow")
    Result brightnessShow();

    /**
     * 查询区域里的所有灯
     */
    @GetMapping("/findLedsByArea")
    Result findLedsByArea(@RequestParam("areaId")String areaId);

    /**
     * 设置灯的默认参数
     */
    @PostMapping("/refreshParam")
    Result refreshParam(@RequestBody InfoBox<String> infoBox);
}
