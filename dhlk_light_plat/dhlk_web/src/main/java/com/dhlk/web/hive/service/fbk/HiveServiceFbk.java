package com.dhlk.web.hive.service.fbk;

import com.dhlk.entity.hive.MetaTable;
import com.dhlk.web.hive.service.HiveService;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import org.springframework.stereotype.Service;
import com.dhlk.utils.ResultUtils;

import java.util.List;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/19
 */
@Service
public class HiveServiceFbk implements HiveService {
    @Override
    public Result findTableList() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result createTable(List<MetaTable> tableList) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result dropTable(List<MetaTable> tableList) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }


}