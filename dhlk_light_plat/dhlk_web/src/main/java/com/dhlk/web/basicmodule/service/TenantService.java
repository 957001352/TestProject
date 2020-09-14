package com.dhlk.web.basicmodule.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Tenant;
import com.dhlk.web.basicmodule.service.fbk.TenantServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 租户管理Service
 */
@FeignClient(value = "basicmodule-service/tenant",fallback = TenantServiceFbk.class)
public interface TenantService {

    /**
     * 保存租户
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody Tenant tenant);

    /**
     * 列表查询
     * @return
     */
    @GetMapping("/findList")
    Result findList(@RequestParam(value = "name",required = false) String name,
                    @RequestParam(value = "startTime",required = false) String startTime,
                    @RequestParam(value = "endTime",required = false) String endTime,
                    @RequestParam(value = "expire",required = false) String expire,
                    @RequestParam(value = "pageNum", required = false,defaultValue = "1") Integer pageNum,
                    @RequestParam(value = "pageSize", required = false,defaultValue = "10") Integer pageSize);

    /**
     * 删除
     * @param id
     * @return result
     */
    @GetMapping(value = "/delete")
    Result delete(@RequestParam(value = "id") Integer id);

    /**
     * 导出租户列表
     */
    @GetMapping("/exportExcel")
    Result exportExcel(@RequestParam(value = "expire",required = false) String expire,
                          @RequestParam(value = "startTime",required = false) String startTime,
                          @RequestParam(value = "endTime",required = false) String endTime,
                          @RequestParam(value = "name",required = false) String name);

    /**
     * 租户管理员查询
     * @param tenantId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/findTenantAdminList")
    Result findTenantAdminList(@RequestParam(value = "tenantId") Integer tenantId,
                               @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);

    /**
     * 验证租户名称重复
     * @param name
     * @return
     */
    @GetMapping("/findTenantRepeat")
    Result findTenantRepeat(@RequestParam(value = "name") String name,@RequestParam(value = "id",required = false) Integer id);

    /**
     * 根据租户code查询数据
     * @param code
     * @return
     */
    @GetMapping("/findTenantByCode")
    Result findTenantByCode(@RequestParam(value = "code", required = false) String code);
}
