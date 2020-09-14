package com.dhlk.web.basicmodule.service.fbk;


import com.dhlk.entity.hive.MetaTable;
import com.dhlk.web.basicmodule.service.MetaTableService;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import org.springframework.stereotype.Service;
import com.dhlk.utils.ResultUtils;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/19
 */
@Service
public class MetaTableServiceFbk implements MetaTableService {
    @Override
    public Result save(MetaTable metaTable) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findPageList(Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}