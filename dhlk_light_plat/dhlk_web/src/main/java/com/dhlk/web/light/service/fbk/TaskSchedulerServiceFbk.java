package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.TaskScheduler;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.CompanyListService;
import com.dhlk.web.light.service.TaskSchedulerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xkliu
 * @date 2020/6/4
 * <p>
 * 企业列表服务调用失败时的实现类
 */
@Service
public class TaskSchedulerServiceFbk implements TaskSchedulerService {

    @Override
    public Result selectTaskSchedulerList(String name, Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result saveTaskScheduler(TaskScheduler taskScheduler) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result deleteTaskSchedulerByIds(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result startTaskScheduler(Integer taskSchedulerId) throws Exception {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result stopTaskScheduler(Integer taskSchedulerId) throws Exception {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result selectTaskSchedulerLogList(Integer taskSchedulerId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result updateSchedulerStatus(String schedulerId,String status ) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
