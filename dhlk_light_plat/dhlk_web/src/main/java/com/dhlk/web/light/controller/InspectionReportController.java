package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.utils.ExcelUtil;
import com.dhlk.web.light.service.InspectionReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/12
 * <p>
 * 验收报告控制器
 */
@RestController
@RequestMapping(value = "/inspectionReport")
@Api(description = "验收报告", value = "InspectionReportController")
public class InspectionReportController {

    @Autowired
    private InspectionReportService inspectionReportService;

    /**
     * 执行验收
     *
     * @return
     */
    @ApiOperation("执行验收")
    @GetMapping("/executeInspection")
    public Result executeInspection() {
        return inspectionReportService.executeInspection();
    }

    /**
     * 灯具列表查询
     *
     * @return
     */
    @ApiOperation("灯具")
    @GetMapping("/findLampList")
    public Result findLampList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                               @RequestParam(value = "areaId", required = false) String areaId,
                               @RequestParam(value = "sn", required = false) String sn,
                               @RequestParam(value = "onOffBri", required = false) String onOffBri,
                               @RequestParam(value = "result", required = false) String result) {
        return inspectionReportService.findLampList(pageNum, pageSize, areaId, sn, onOffBri, result);
    }


    @ApiOperation("导出结果")
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response,
                            @RequestParam(value = "areaId", required = false) String areaId,
                            @RequestParam(value = "tenantId") Integer tenantId,
                            @RequestParam(value = "sn", required = false) String sn,
                            @RequestParam(value = "onOffBri", required = false) String onOffBri,
                            @RequestParam(value = "result", required = false) String result) {
        try {
            List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>) inspectionReportService.exportExcel(areaId, tenantId,sn, onOffBri, result).getData();
            ExcelUtil.exportExcel(response, Arrays.asList(new String[]{"行号", "sn", "区域", "开灯", "调光", "关灯"}), list, "excel");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取验收报告结果
     *
     * @return
     */
    @ApiOperation("获取验收报告结果")
    @GetMapping("/getInspection")
    public Result getInspection(@RequestParam(value = "key") String key) {
        return inspectionReportService.getInspection(key);
    }

    /**
     * 获取合格灯具
     *
     * @return
     */
    @ApiOperation("获取合格灯具")
    @GetMapping("/getQualifiedLed")
    public Result getQualifiedLed() {
        return inspectionReportService.getQualifiedLed();
    }

}
