package com.dhlk.web.basicmodule.service.fbk;/**
 * @创建人 wangq
 * @创建时间 2020/7/1
 * @描述
 */

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.DeskTop;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.basicmodule.service.DeskTopService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: dhlk.light.plat
 *
 * @description:
 *
 * @author: wqiang
 *
 * @create: 2020-07-01 10:34
 **/
@Service
public class DeskTopServiceImplFbk implements DeskTopService {
    @Override
    public Result save(List<DeskTop> deskTopList) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList(Integer userId, Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
