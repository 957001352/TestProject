package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.LedVersionInfo;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.LedVersionInfoService;
import org.springframework.stereotype.Service;

/**
 * @program: dhlk.light.plat
 *
 * @description: 灯版本信息
 *
 * @author: wqiang
 *
 * @create: 2020-06-30 11:40
 **/
@Service
public class LedVersionInfoServiceFbk implements LedVersionInfoService {
    @Override
    public Result save(LedVersionInfo ledVersionInfo) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList(String name, String creator, String createTime, Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result firmwareUpgrade(InfoBox<LedVersionInfo> infoBox) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

}
