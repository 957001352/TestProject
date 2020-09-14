package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.PeopleFeel;
import com.dhlk.entity.light.PeopleFeelInfo;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.PeopleFeelService;
import org.springframework.stereotype.Service;

/**
 * @author xkliu
 * @date 2020/6/30
 */
@Service
public class PeopleFeelServiceFbk implements PeopleFeelService {
    @Override
    public Result save(PeopleFeel peopleFeel) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findOne() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result peopleFeelContro(InfoBox<PeopleFeelInfo> infoBox) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result memoryPeopleFeel() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

}
