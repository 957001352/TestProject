package com.dhlk.light.service.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Area;
import com.dhlk.light.service.service.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xkliu
 * @date 2020/6/5
 * <p>
 * 施工区域控制器
 */
@RestController
@RequestMapping(value = "/area")
@Api(value = "AreaController", description = "施工区域管理")
public class AreaController {

    @Autowired
    private AreaService areaService;

    /**
     * 保存
     *
     * @param area
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("area:save")
    public Result save(@RequestBody Area area) {
        return areaService.save(area);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    @RequiresPermissions("area:delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return areaService.delete(ids);
    }

    /**
     * 施工区域列表
     *
     * @return
     */
    @GetMapping("/findList")
    @RequiresAuthentication
    public Result findList() {
        return areaService.findList();
    }

    /**
     * 根据租户Id获取施工区域
     * @param tenantId
     * @return
     */
    @GetMapping("/findListByTenantId")
    @RequiresAuthentication
    public Result findListByTenantId(@RequestParam(value = "tenantId") Integer tenantId) {
        return areaService.findListByTenantId(tenantId);
    }

    /**
     * 验证区域名称重复
     *
     * @param
     * @return
     */
    @GetMapping("/findAreaRepeat")
    @RequiresAuthentication
    public Result findAreaRepeat(@RequestParam(value = "name") String name) {
        return areaService.findAreaRepeat(name);
    }
}
