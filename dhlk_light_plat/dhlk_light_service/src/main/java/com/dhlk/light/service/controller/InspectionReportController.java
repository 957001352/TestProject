package com.dhlk.light.service.controller;

import com.dhlk.domain.Result;
import com.dhlk.light.service.service.InspectionReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xkliu
 * @date 2020/6/12
 * <p>
 * 验收报告控制器
 */
@RestController
@RequestMapping(value = "/inspectionReport")
@Api(value = "InspectionReportController", description = "验收报告")
public class InspectionReportController {

    @Autowired
    private InspectionReportService inspectionReportService;

    /**
     * 执行验收
     *
     * @return
     */
    @GetMapping("/executeInspection")
    @RequiresPermissions("inspectionReport:executeInspection")
    public Result executeInspection() {
        try {
            return inspectionReportService.executeInspection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 灯具列表查询
     *
     * @return
     */
    @GetMapping("/findLampList")
    @RequiresAuthentication
    public Result findLampList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                               @RequestParam(value = "areaId", required = false) String areaId,
                               @RequestParam(value = "sn", required = false) String sn,
                               @RequestParam(value = "onOffBri", required = false) String onOffBri,
                               @RequestParam(value = "result", required = false) String result) {
        return inspectionReportService.findLampList(pageNum, pageSize,areaId,sn,onOffBri,result);
    }

    /**
     * 导出结果
     */
    @GetMapping("/exportExcel")
    public Result exportExcel(@RequestParam(value = "areaId", required = false) String areaId,
                              @RequestParam(value = "tenantId") Integer tenantId,
                               @RequestParam(value = "sn", required = false) String sn,
                               @RequestParam(value = "onOffBri", required = false) String onOffBri,
                               @RequestParam(value = "result", required = false) String result) {
        return inspectionReportService.exportExcel(areaId,tenantId, sn, onOffBri, result);
    }

    /**
     * 获取验收报告结果
     *
     * @return
     */
    @GetMapping("/getInspection")
    public Result getInspection(@RequestParam(value = "key") String key) {
        return inspectionReportService.getInspection(key);
    }

    /**
     * 获取合格灯具
     *
     * @return
     */
    @GetMapping("/getQualifiedLed")
    public Result getQualifiedLed() {
        return inspectionReportService.getQualifiedLed();
    }
}
