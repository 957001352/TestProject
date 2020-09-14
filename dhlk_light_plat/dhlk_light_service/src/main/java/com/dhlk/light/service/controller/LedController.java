package com.dhlk.light.service.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.*;
import com.dhlk.light.service.service.LedParamInfoService;
import com.dhlk.light.service.service.LedService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 灯管理
 * @Author lpsong
 * @Date 2020/6/4
 */
@RestController
@RequestMapping(value = "/led")
public class LedController {

    @Autowired
    private LedService ledService;
    @Autowired
    private LedParamInfoService ledParamInfoService;

    /**
     * 保存
     * @param led
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresAuthentication
    public Result save(@RequestBody Led led) {
        return ledService.save(led);
    }

    /**
     * 增加别名
     * @param led
     * @return
     */
    @PostMapping(value = "/update")
    @RequiresAuthentication
    public Result update(@RequestBody Led led) {
        return ledService.update(led);
    }

    /**
     * 灯闪一闪
     * @param infoBox
     * @return
     */
    @PostMapping(value = "/flashingLed")
    @RequiresAuthentication
    public Result flashingLed(@RequestBody InfoBox<String> infoBox) {
//            @RequestParam("sns") String sns) {
        return  ledService.flashingLed(infoBox.getSns());
    }

    /**
     * 删除
     * @param id
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresAuthentication
    public Result delete(@RequestParam(value = "id") String id) {
        return ledService.delete(id);
    }
    /**
     * 列表查询
     * @param sn
     * @param areaId
     * @return
     */
    @GetMapping("/findList")
    @RequiresAuthentication
    public Result findList(@RequestParam(value = "sn", required = false) String sn,
                           @RequestParam(value = "areaId", required = false) String areaId,
                           @RequestParam(value = "switchId", required = false) String switchId){
        return ledService.findList(sn, areaId,switchId);
    }




    /**
     * 新增灯能耗信息 (能耗统计)
     * @param list
     * @return
     */
    @PostMapping(value = "/savePower")
    //@RequiresAuthentication
    public Result savePower(@RequestBody List<LedPower> list) {
        return ledService.savePower(list);
    }

    /**
     * 新增灯在线时长
     * @param ledOnline
     * @return
     */
    @PostMapping(value = "/saveOnline")
    @RequiresAuthentication
    public Result saveOnline(@RequestBody LedOnline ledOnline) {
        return ledService.saveOnline(ledOnline);
    }

    @PostMapping(value = "/saveOnlineList")
    //@RequiresAuthentication
    public Result saveOnlineList(@RequestBody List<LedOnline> list) {
        return ledService.saveOnlineList(list);
    }

    /**
     * 增加开关与灯绑定关系
     * @param swich
     * @return
     */
    @PostMapping(value = "/saveSwitchBoundLed")
    @RequiresAuthentication
    public Result saveSwitchBoundLed(@RequestBody Switch swich) {
        return ledService.saveSwitchBoundLed(swich);
    }

    /**
     * 设置灯亮度
     * @param infoBox
     * @return
     */
    @PostMapping(value = "/setLedBrightness")
    @RequiresAuthentication
    public Result setLedBrightness(@RequestBody InfoBox<String> infoBox) {
//            @RequestParam("sns") String sns,
//                                   @RequestParam("brightness") String brightness) {
        return ledService.setLedBrightness(infoBox.getSns(),infoBox.getT());
    }

    /**
     * 查询有灯的区域
     * @return
     */
    @GetMapping(value = "/findAreasByLed")
    @RequiresAuthentication
    public Result findAreasByLed() {
        return ledService.findAreasByLed();
    }

    /**
     * 开关灯
     * @return
     */
    @PostMapping(value = "/openOrCloseLed")
    @RequiresAuthentication
    public Result openOrCloseLed(@RequestBody InfoBox<String> infoBox) {
//            @RequestParam("sns") String sns,
//                                 @RequestParam("status") String status) {
        return ledService.openOrCloseLed(infoBox.getSns(),infoBox.getT());
    }


    /**
     * 灯重启
     * @return
     */
    @PostMapping(value = "/ledRestart")
    @RequiresAuthentication
    public Result ledRestart(@RequestBody InfoBox<String> infoBox) {
//            @RequestParam("sns") String sns) {
        return ledService.ledRestart(infoBox.getSns());
    }
    /**
     * 开关重启
     * @return
     */
    @PostMapping(value = "/switchRestart")
    @RequiresAuthentication
    public Result switchRestart(@RequestBody InfoBox<String> infoBox) {
//            @RequestParam("sns") String sns) {
        return ledService.switchRestart(infoBox.getSns());
    }

    /**
     * 获取照明设备故障信息
     * @return
     */
    @GetMapping(value = "/findLedFault")
    @RequiresAuthentication
    public Result findLedFault(@RequestParam("ledSn") String ledSn) {
        return ledService.findLedFault(ledSn);
    }

    /**
     * 导出列表查询
     */
    @GetMapping("/findExportList")
    @RequiresAuthentication
    public Result findExportList(@RequestParam("ledSn") String ledSn) {
        return  ledService.findExportList(ledSn);
    }


    /**
     * 根据租户Id获取灯信息
     * @param tenantId
     * @return
     */
    @GetMapping("/findListByTenantId")
    @RequiresAuthentication
    public Result findListByTenantId(@RequestParam(value = "tenantId", required = false) Integer tenantId) {
        return ledService.findListByTenantId(tenantId);
    }

    /**
     * 获取灯的配置参数
     * @param infoBox
     */
    @PostMapping("/findLedParamInfos")
    @RequiresAuthentication
    public Result findLedParamInfos(@RequestBody InfoBox<String> infoBox) {
//            @RequestParam(value = "sns", required = false) String sns) {
        return ledService.findLedParamInfos(infoBox.getSns());
    }

    @GetMapping(value = "/findLedRecord")
    @RequiresAuthentication
    public Result findLedRecord(@RequestParam(value ="sn", required = false) String sn,
                                @RequestParam(value ="commond", required = false) String commond,
                                @RequestParam(value ="sendResult", required = false) String sendResult,
                                @RequestParam(value ="backResult", required = false) String backResult,
                                @RequestParam(value ="startTime", required = false) String startTime,
                                @RequestParam(value ="endTime", required = false) String endTime,
                                @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return ledService.findLedRecord(sn,commond,sendResult,backResult,startTime,endTime,pageNum,pageSize);
    }

    /**
     * 未添加灯具
     */
    @GetMapping("/notAddLed")
    @RequiresAuthentication
    public Result notAddLed() {
        return ledService.notAddLed();
    }


    /**
     * 预设亮度显示
     */
    @GetMapping("/brightnessShow")
    public Result brightnessShow() {
        return ledService.brightnessShow();
    }

    /**
     * 查询区域里的所有灯
     */
    @GetMapping("/findLedsByArea")
    public Result findLedsByArea(@RequestParam("areaId")String areaId) {
        return ledService.findLedsByArea(areaId);
    }

    /**
     * 设置灯的默认参数
     */
    @PostMapping("/ledDefaultParam")
    public Result ledDefaultParam(@RequestBody LedParamInfo ledParamInfo) {
        return ledParamInfoService.update(ledParamInfo);
    }


    /**
     * 设置灯的默认参数
     */
    @PostMapping("/refreshParam")
    public Result refreshParam(@RequestBody InfoBox<String> infoBox) {
        return ledParamInfoService.refreshParam(infoBox);
    }
}
