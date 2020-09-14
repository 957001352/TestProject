package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.InspectionReportService;
import org.springframework.stereotype.Service;

/**
 * @author xkliu
 * @date 2020/6/12
 * <p>
 * 验收报告服务调用失败时的实现类
 */
@Service
public class InspectionReportServiceFbk implements InspectionReportService {
    @Override
    public Result executeInspection() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findLampList(Integer pageNum, Integer pageSize, String areaId, String sn, String onOffBri, String result) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }


    @Override
    public Result getInspection(String key) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result getQualifiedLed() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result exportExcel(String areaId,Integer tenantId, String sn, String onOffBri, String result) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

}
