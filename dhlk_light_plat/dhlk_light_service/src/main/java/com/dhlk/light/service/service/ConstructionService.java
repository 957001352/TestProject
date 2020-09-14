package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Construction;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xkliu
 * @date 2020/6/4
 * <p>
 * 施工信息service
 */
public interface ConstructionService {

    /**
     * 保存施工信息
     *
     * @param
     * @return
     */
    Result save(@RequestBody Construction construction);

    /**
     * 列表查询
     *
     * @param implPeople
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result findList(String implPeople, String startDate, String endDate, Integer pageNum, Integer pageSize);

    /**
     * 物理删除
     *
     * @param ids
     * @return
     */
    Result delete(String ids);
}
