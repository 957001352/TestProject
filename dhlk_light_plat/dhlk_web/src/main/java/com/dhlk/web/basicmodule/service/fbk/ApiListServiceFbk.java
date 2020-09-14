package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.entity.api.ApiList;
import com.dhlk.web.basicmodule.service.ApiListService;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import org.springframework.stereotype.Service;
import com.dhlk.utils.ResultUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 生产设备管理
 **/
@Service
public class ApiListServiceFbk implements ApiListService {


    @Override
    public Result save(ApiList apiList) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findPageList(Integer parentId, Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
    @Override
    public Result importExcel(MultipartFile file) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }


    @Override
    public Result findExportList() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}