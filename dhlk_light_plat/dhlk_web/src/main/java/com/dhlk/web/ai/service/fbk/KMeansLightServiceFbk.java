package com.dhlk.web.ai.service.fbk;


import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.ai.service.KMeansLightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/23
 * Time:10:18
 * @Description:
 */
@Service
public class KMeansLightServiceFbk implements KMeansLightService {

    @Override
    public Result findAll(String factoryCode) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result selectFactoryCode() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }


    public Result lightProportion(String factoryCode) {

        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
