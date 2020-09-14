package com.dhlk.light.service.service;

import com.dhlk.domain.Result;

/**
 * @author xkliu
 * @date 2020/6/12
 * <p>
 * 验收报告service
 */
public interface InspectionReportService {

    /**
     * 执行验收
     *
     * @return
     */
    Result executeInspection() throws Exception;

    /**
     * 灯具列表查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result findLampList(Integer pageNum, Integer pageSize, String areaId, String sn, String onOffBri, String result);


    /**
     * 获取验收报告
     * @return
     */
    Result getInspection(String key);

    /**
     * 获取合格灯具
     * @return
     */
    Result getQualifiedLed();


    /**
     * 导出结果
     * @param areaId
     * @param sn
     * @param onOffBri
     * @param result
     * @return
     */
    Result exportExcel(String areaId, Integer tenantId,String sn, String onOffBri, String result);
}
