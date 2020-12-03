package com.dhlk.light.factory.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.IntensityInfo;
import com.dhlk.entity.light.LedWifiInfo;
import com.dhlk.entity.light.PeopleFeelInfo;
import com.dhlk.light.factory.service.LedService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/led")
public class LedController {
    @Autowired
    private LedService ledService;


    /**
     * 开关灯
     *
     * @return
     */
    @PostMapping(value = "/openOrCloseLed")
    //@RequiresAuthentication
    public Result openOrCloseLed(@RequestBody InfoBox<Integer> infoBox) {
//            @RequestParam("sns") String sns,@RequestParam("status") Integer status
        return ledService.openOrCloseLed(infoBox.getSns(), infoBox.getT());
    }

    /**
     * 设置灯亮度
     *
     * @param infoBox brightness
     * @return
     */
    @PostMapping(value = "/setLedBrightness")
//    @RequiresAuthentication
    public Result setLedBrightness(@RequestBody InfoBox<String> infoBox) {

//            @RequestParam("sns") String sns,
//                                   @RequestParam("brightness") String brightness) {
        return ledService.setLedBrightness(infoBox.getSns(), infoBox.getT());
    }

    @PostMapping(value = "/ledRestart")
    @RequiresAuthentication
    public Result ledRestart(@RequestBody InfoBox<String> infoBox) {
//            @RequestParam("sns") String sns) {
        return ledService.ledRestart(infoBox.getSns());
    }

    @PostMapping(value = "/setLedGrpId")
    @RequiresAuthentication
    public Result setLedGrpId(@RequestBody InfoBox<Integer> infoBox) {
//            @RequestParam("sns") String sns, @RequestParam("grpId") Integer grpId) {
        return ledService.setLedGrpId(infoBox.getSns(), infoBox.getT());
    }

    /**
     * 灯闪一闪
     *
     * @param sns
     * @return
     */
    @PostMapping(value = "/flashingLed")
    @RequiresAuthentication
    public Result flashingLed(@RequestBody InfoBox<String> infoBox) {
//           @RequestParam("sns") String sns) {
        return ledService.flashingLed(infoBox.getSns());
    }


    /**
     * 查询有灯的区域
     *
     * @return
     */
    @GetMapping(value = "/findAreasByLed")
    @RequiresAuthentication
    public Result findAreasByLed() {
        return ledService.findAreasByLed();
    }


    /**
     * 光感控制
     *
     * @return
     */
    @PostMapping(value = "/lightSensationContro")
    @RequiresAuthentication
    public Result lightSensationContro(@RequestBody InfoBox<IntensityInfo> infoBox) {
//            @RequestParam("sns") String sns, @RequestBody IntensityInfo intensityInfo) {
        return ledService.lightSensationContro(infoBox.getSns(), infoBox.getT());
    }

    /**
     * 人感控制
     *
     * @return
     */
    @PostMapping(value = "/peopleFeelingContro")
    @RequiresAuthentication
    public Result peopleFeelContro(@RequestBody InfoBox<PeopleFeelInfo> infoBox) {
//            @RequestParam("sns") String sns, @RequestBody PeopleFeelInfo peopleFeelInfo) {
        return ledService.peopleFeelContro(infoBox.getSns(), infoBox.getT());
    }

    /**
     * wifi设置
     *
     * @return
     */
    @PostMapping(value = "/wifiContro")
    @RequiresAuthentication
    public Result wifiContro(@RequestBody InfoBox<LedWifiInfo> infoBox) {
//            @RequestParam("sns") String sns, @RequestBody LedWifiInfo ledWifiInfo) {
        return ledService.wifiContro(infoBox.getSns(), infoBox.getT());
    }

    /**
     * 获取wifi配置
     *
     * @return
     */
    @PostMapping(value = "/loadWifiConfig")
    @RequiresAuthentication
    public Result loadWifiConfig(@RequestBody InfoBox<String> infoBox) {
 //           @RequestParam("sns") String sns) {
        return ledService.loadWifiConfig(infoBox.getSns());
    }

    /**
     * 操作日志查询
     *
     * @param commond
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping(value = "/findLedRecord")
    @RequiresAuthentication
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
     * 预设亮度显示
     */
    @GetMapping("/brightnessShow")
    @RequiresAuthentication
    public Result brightnessShow() {
        return ledService.brightnessShow();
    }

    /**
     * 查询区域里的所有灯
     */
    @GetMapping("/findLedsByArea")
    @RequiresAuthentication
    public Result findLedsByArea(@RequestParam("areaId")String areaId) {
        return ledService.findLedsByArea(areaId);
    }

    /**
     * 显示图标大小
     */
    @GetMapping("/showIconSize")
    public Result showIconSize() {
        return ledService.showIconSize();
    }
}
