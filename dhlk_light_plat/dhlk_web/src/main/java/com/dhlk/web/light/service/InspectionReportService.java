package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.web.light.service.fbk.InspectionReportServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xkliu
 * @date 2020/6/12
 * <p>
 * 验收报告service
 */
@FeignClient(value = "light-service/inspectionReport", fallback = InspectionReportServiceFbk.class)
public interface InspectionReportService {

    /**
     * 执行验收
     *
     * @return
     */
    @GetMapping("/executeInspection")
    Result executeInspection();

    /**
     * 灯具列表查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/findLampList")
    Result findLampList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                        @RequestParam(value = "areaId", required = false) String areaId,
                        @RequestParam(value = "sn", required = false) String sn,
                        @RequestParam(value = "onOffBri", required = false) String onOffBri,
                        @RequestParam(value = "result", required = false) String result);


    /**
     * 获取验收报告
     * @return
     */
    @GetMapping("/getInspection")
    Result getInspection(@RequestParam(value = "key", required = false) String key);

    /**
     * 获取合格的灯具
     * @return
     */
    @GetMapping("/getQualifiedLed")
    Result getQualifiedLed();

    /**
     * 导出结果
     */
    @GetMapping("/exportExcel")
    Result exportExcel(@RequestParam(value = "areaId", required = false) String areaId,
                       @RequestParam(value = "tenantId") Integer tenantId,
                       @RequestParam(value = "sn", required = false) String sn,
                       @RequestParam(value = "onOffBri", required = false) String onOffBri,
                       @RequestParam(value = "result", required = false) String result);

}
