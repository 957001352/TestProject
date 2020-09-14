package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.TenantService;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Tenant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * 租户控制器
 */
@RestController
@RequestMapping(value = "/tenant")
@Api(value = "TenantController", description = "租户管理")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    /**
     * 保存
     *
     * @param tenant
     * @return
     * @throws ParseException
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("tenant:save")
    public Result save(@RequestBody Tenant tenant) {
        return tenantService.save(tenant);
    }

    /**
     * 列表查询
     *
     * @param
     * @return
     */
    @GetMapping("/findList")
    @RequiresAuthentication
    public Result findList(@RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "startTime", required = false) String startTime,
                           @RequestParam(value = "endTime", required = false) String endTime,
                           @RequestParam(value = "expire", required = false) String expire,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return tenantService.findList(name, startTime, endTime, expire, pageNum, pageSize);
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/delete")
    @RequiresPermissions("tenant:delete")
    Result delete(@RequestParam(value = "id") Integer id) {
        return tenantService.delete(id);
    }

    /**
     * 导出租户列表
     */
    @GetMapping("/exportExcel")
    //@RequiresAuthentication
    public Result exportExcel(@RequestParam(value = "expire", required = false) String expire,
                              @RequestParam(value = "startTime", required = false) String startTime,
                              @RequestParam(value = "endTime", required = false) String endTime,
                              @RequestParam(value = "name", required = false) String name) {
        return tenantService.exportExcel(expire, startTime, endTime, name);
    }

    /**
     * 租户管理员查询
     *
     * @param
     * @return
     */
    @GetMapping("/findTenantAdminList")
    @RequiresAuthentication
    public Result findTenantAdminList(@RequestParam(value = "tenantId") Integer tenantId,
                                      @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return tenantService.findTenantAdminList(tenantId, pageNum, pageSize);
    }

    /**
     * 验证租户名称存在
     * @param name
     * @return
     */
    @GetMapping("/findTenantRepeat")
    public Result findTenantRepeat(@RequestParam(value = "name") String name,@RequestParam(value = "id",required = false) Integer id){
        return tenantService.findTenantRepeat(name,id);
    }

    /**
     * 根据租户code查询数据
     *
     * @param
     * @return
     */
    @GetMapping("/findTenantByCode")
    @RequiresAuthentication
    public Result findList(@RequestParam(value = "code", required = false) String code) {
        return tenantService.findTenantByCode(code);
    }
}
