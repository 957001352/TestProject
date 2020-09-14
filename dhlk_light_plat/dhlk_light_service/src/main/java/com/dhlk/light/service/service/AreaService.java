package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Area;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xkliu
 * @date 2020/6/5
 * <p>
 * 施工区域service
 */
public interface AreaService {

    /**
     * 保存施工信息
     *
     * @param
     * @return
     */
    Result save(@RequestBody Area area);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    Result delete(String ids);

    /**
     * 获取当前租户区域列表
     *
     * @return
     */
    Result findList();

    /**
     * 根据租户Id获取施工区域
     *
     * @param tenantId
     * @return
     */
    Result findListByTenantId(Integer tenantId);

    /**
     * 验证区域名称重复
     * @param name
     * @return
     */
    Result findAreaRepeat(String name);
}
