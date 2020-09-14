package com.dhlk.basicmodule.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Tenant;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 租户管理Service
 */
public interface TenantService {

    /**
     * 保存租户
     *
     * @param
     * @return
     */
    Result save(@RequestBody Tenant tenant);

    /**
     * 分页查询租户列表
     *
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result findList(String name, String startTime, String endTime, String expire, Integer pageNum, Integer pageSize);

    /**
     * 逻辑删除修改status状态
     *
     * @param id
     * @return
     */
    Result delete(Integer id);

    /**
     * 导出租户列表
     *
     * @return
     */
    Result exportExcel(String expire, String startTime, String endTime, String name);

    /**
     * 租户管理员列表查询
     *
     * @param tenantId
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result findTenantAdminList(Integer tenantId, Integer pageNum, Integer pageSize);

    /**
     * 验证租户名称重复
     *
     * @param name
     * @return
     */
    Result findTenantRepeat(String name,Integer id);

    /**
     * 根据租户code查询数据
     * @param code
     * @return
     */
    Result findTenantByCode(String code);
}
