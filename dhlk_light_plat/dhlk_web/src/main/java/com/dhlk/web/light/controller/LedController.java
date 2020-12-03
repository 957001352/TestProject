package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.*;
import com.dhlk.utils.ExcelUtil;
import com.dhlk.web.light.service.LedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Description 灯管理
 * @Author lpsong
 * @Date 2020/6/4
 */
@RestController
@RequestMapping(value = "/led")
@Api(description = "灯管理", value = "led")
public class LedController {

    @Autowired
    private LedService ledService;

    /**
     * 保存
     *
     * @param led
     * @return
     */
    @ApiOperation("新增/修改")
    @PostMapping(value = "/save")
    public Result save(@RequestBody Led led) {
        return ledService.save(led);
    }

    /**
     * 修改
     *
     * @param led
     * @return
     */
    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public Result update(@RequestBody Led led) {
        return ledService.update(led);
    }


    @PostMapping(value = "/updateLocation")
    public Result updateLocation(@RequestBody List<Led> leds) {
        return ledService.updateLocation(leds);
    }

    /**
     * 灯闪一闪
     *
     * @param infoBox
     * @return
     */
    @ApiOperation("闪一闪")
    @PostMapping(value = "/flashingLed")
    public Result flashingLed(@RequestBody InfoBox<String> infoBox) {
        return ledService.flashingLed(infoBox);
    }

    /**
     * 删除
     *
     * @param id
     * @return result
     */
    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "id") String id) {
        return ledService.delete(id);
    }

    /**
     * 列表查询
     *
     * @param sn
     * @param areaId
     * @return
     */
    @ApiOperation("列表查询")
    @GetMapping("/findList")
    public Result findList(@RequestParam(value = "sn", required = false) String sn,
                           @RequestParam(value = "areaId", required = false) String areaId,
                           @RequestParam(value = "switchId", required = false) String switchId) {
        return ledService.findList(sn, areaId, switchId);
    }


    /**
     * 新增灯能耗信息
     *
     * @param list
     * @return
     */
    @ApiOperation("新增灯能耗信息")
    @PostMapping(value = "/savePower")
    public Result savePower(@RequestBody List<LedPower> list) {
        return ledService.savePower(list);
    }

    @PostMapping(value = "/saveOnlineList")
    @ApiOperation("本地灯在线时长汇总到云")
    public Result saveOnlineList(@RequestBody List<LedOnline> list) {
        return ledService.saveOnlineList(list);
    }

    /**
     * 保存人感统计
     *
     * @param list
     * @return
     */
    @PostMapping(value = "/savePeopleList")
    public Result savePeopleList(@RequestBody String json) {
        return ledService.savePeopleList(json);
    }


    /**
     * 新增灯在线时长
     *
     * @param ledOnline
     * @return
     */
    @ApiOperation("新增灯在线时长")
    @PostMapping(value = "/saveOnline")
    public Result saveOnline(@RequestBody LedOnline ledOnline) {
        return ledService.saveOnline(ledOnline);
    }

    /**
     * 增加开关与灯绑定关系
     *
     * @param swich
     * @return
     */
    @ApiOperation("增加开关与灯绑定关系")
    @PostMapping(value = "/saveSwitchBoundLed")
    public Result saveSwitchBoundLed(@RequestBody Switch swich) {
        return ledService.saveSwitchBoundLed(swich);
    }

    /**
     * 设置灯亮度
     *
     * @param infoBox
     * @return
     */
    @ApiOperation("设置灯亮度")
    @PostMapping(value = "/setLedBrightness")
    public Result setLedBrightness(@RequestBody InfoBox<String> infoBox) {

//            @RequestParam("sns") String sns,
//                                   @RequestParam("brightness") String brightness) {
        return ledService.setLedBrightness(infoBox);
    }

    /**
     * 查询有灯的区域
     *
     * @return
     */
    @ApiOperation("查询有灯的区域")
    @GetMapping(value = "/findAreasByLed")
    public Result findAreasByLed() {
        return ledService.findAreasByLed();
    }

    /**
     * 开关灯
     *
     * @return
     */
    @PostMapping(value = "/openOrCloseLed")
    public Result openOrCloseLed(@RequestBody InfoBox<String> infoBox) {
//            @RequestParam("sns") String sns,
//                                 @RequestParam("status") String status) {
        return ledService.openOrCloseLed(infoBox);
    }

    /**
     * 灯重启
     *
     * @return
     */
    @PostMapping(value = "/ledRestart")
    //@RequiresPermissions("led:ledRestart")
    public Result ledRestart(@RequestBody InfoBox<String> infoBox) {
        return ledService.ledRestart(infoBox);
    }

    /**
     * 开关重启
     *
     * @return
     */
    @PostMapping(value = "/switchRestart")
    public Result switchRestart(@RequestBody InfoBox<String> infoBox) {
        return ledService.switchRestart(infoBox);
    }

    /**
     * 获取照明设备故障信息
     *
     * @return
     */
    @GetMapping(value = "/findLedFault")
    public Result findLedFault(@RequestParam("ledSn") String ledSn) {
        return ledService.findLedFault(ledSn);
    }


    @ApiOperation("文件导出")
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response, @RequestParam("ledSn") String ledSn) {
        try {
            List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>) ledService.findExportList(ledSn).getData();
            ExcelUtil.exportExcel(response, Arrays.asList(new String[]{"SN", "故障代码"}), list, "测试excel导出");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据租户Id获取灯信息
     *
     * @param tenantId
     * @return
     */
    @ApiOperation("根据租户Id获取灯信息")
    @GetMapping("/findListByTenantId")
    public Result findListByTenantId(@RequestParam(value = "tenantId", required = false) Integer tenantId) {
        return ledService.findListByTenantId(tenantId);
    }

    /**
     * 获取灯的配置参数
     *
     * @param infoBox
     */
    @PostMapping("/findLedParamInfos")
    public Result findLedParamInfos(@RequestBody InfoBox<String> infoBox) {
        return ledService.findLedParamInfos(infoBox);
    }

    @ApiOperation("操作日志查询")
    @GetMapping(value = "/findLedRecord")
    public Result findLedRecord(@RequestParam(value = "sn", required = false) String sn,
                                @RequestParam(value = "commond", required = false) String commond,
                                @RequestParam(value = "sendResult", required = false) String sendResult,
                                @RequestParam(value = "backResult", required = false) String backResult,
                                @RequestParam(value = "startTime", required = false) String startTime,
                                @RequestParam(value = "endTime", required = false) String endTime,
                                @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return ledService.findLedRecord(sn, commond, sendResult, backResult, startTime, endTime, pageNum, pageSize);
    }

    /**
     * 未添加灯具
     */
    @ApiOperation("未添加灯具")
    @GetMapping("/notAddLed")
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
    public Result findLedsByArea(@RequestParam("areaId") String areaId) {
        return ledService.findLedsByArea(areaId);
    }

    /**
     * 设置灯的默认参数
     */
    @PostMapping("/refreshParam")
    public Result refreshParam(@RequestBody InfoBox<String> infoBox) {
        return ledService.refreshParam(infoBox);
    }

    /**
     * 显示图标大小
     */
    @ApiOperation("显示图标大小")
    @GetMapping("/showIconSize")
    public Result showIconSize() {
        return ledService.showIconSize();
    }

    /**
     * 接收本地亮度设置
     * @param power
     * @return
     */
    @PostMapping(value = "/syncLedBrightness")
    @ApiOperation("接收本地亮度设置")
    public Result syncLedBrightness(@RequestBody String power) {
        return ledService.syncLedBrightness(power);
    }
}
