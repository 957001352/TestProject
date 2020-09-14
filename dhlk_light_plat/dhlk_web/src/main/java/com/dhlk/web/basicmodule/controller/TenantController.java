package com.dhlk.web.basicmodule.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Tenant;
import com.dhlk.utils.ExcelUtil;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.basicmodule.service.TenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 租户控制器
 */
@RestController
@RequestMapping(value = "/tenant")
@Api(description = "租户管理", value = "TenantController")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    /**
     * 保存
     *
     * @param tenant
     * @param bindingResult
     * @return
     */
    @ApiOperation("新增/修改租户")
    @PostMapping("/save")
    public Result save(@Valid @RequestBody Tenant tenant, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if (result == null) {
            return tenantService.save(tenant);
        }
        return result;
    }

    /**
     * 租户信息列表查询
     *
     * @param
     * @return
     */
    @ApiOperation("列表查询")
    @GetMapping("/findList")
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
     * @return result
     */
    @ApiOperation("逻辑删除")
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "id") Integer id) {
        return tenantService.delete(id);
    }

    @ApiOperation("文件导出")
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response,
                            @RequestParam(value = "expire", required = false) String expire,
                            @RequestParam(value = "startTime", required = false) String startTime,
                            @RequestParam(value = "endTime", required = false) String endTime,
                            @RequestParam(value = "name", required = false) String name) {
        try {
            List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>) tenantService.exportExcel(expire, startTime, endTime, name).getData();
            ExcelUtil.exportExcel(response, Arrays.asList(new String[]{"ID", "租户名称","租户编号", "添加日期", "授权开始日期", "授权结束日期", "授权设备数"}), list, "excel");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 租户管理员查询
     *
     * @param
     * @return
     */
    @ApiOperation("租户管理员查询")
    @GetMapping("/findTenantAdminList")
    public Result findTenantAdminList(@RequestParam(value = "tenantId") Integer tenantId,
                                      @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return tenantService.findTenantAdminList(tenantId, pageNum, pageSize);
    }

    /**
     * 验证租户名称重复
     *
     * @param
     * @return
     */
    @ApiOperation("验证租户名称重复")
    @GetMapping("/findTenantRepeat")
    public Result findTenantRepeat(@RequestParam(value = "name") String name,@RequestParam(value = "id",required = false) Integer id) {
        return tenantService.findTenantRepeat(name,id);
    }

    /**
     * 根据租户code查询数据
     *
     * @param
     * @return
     */
    @ApiOperation("根据租户code查询数据")
    @GetMapping("/findTenantByCode")
    public Result findList(@RequestParam(value = "code", required = false) String code) {
        return tenantService.findTenantByCode(code);
    }
}
