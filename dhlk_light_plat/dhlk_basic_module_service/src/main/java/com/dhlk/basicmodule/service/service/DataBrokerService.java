package com.dhlk.basicmodule.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.DataBroker;

/**
 * 消息订阅mosquito管理
 */
public interface DataBrokerService {
        /**
         * 新增/修改
         */
        Result save(DataBroker dataBroker);
        /**
         * 物理删除
         * @param ids
         */
        Result delete(String ids);

        /**
        * 分页查询
         * @param pageNum
         * @param pageSize
        * @return
        */
        Result findPageList(Integer pageNum, Integer pageSize);
        

}
