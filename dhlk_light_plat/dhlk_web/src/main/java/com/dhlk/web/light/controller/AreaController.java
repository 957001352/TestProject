package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Area;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xkliu
 * @date 2020/6/5
 * <p>
 * 施工区域控制器
 */
@RestController
@RequestMapping(value = "/area")
@Api(description = "施工区域管理", value = "AreaController")
public class AreaController {

    @Autowired
    private AreaService areaService;

    /**
     * 新增/修改区域信息
     *
     * @param area
     * @param bindingResult
     * @return
     */
    @ApiOperation("新增区域信息")
    @PostMapping("/save")
    public Result save(@Valid @RequestBody Area area, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if (result == null) {
            return areaService.save(area);
        }
        return result;
    }

    /**
     * 删除
     *
     * @param ids
     * @return result
     */
    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return areaService.delete(ids);
    }

    /**
     * 施工区域列表
     *
     * @param
     * @return
     */
    @ApiOperation("施工区域列表")
    @GetMapping("/findList")
    public Result findList() {
        return areaService.findList();
    }

    /**
     * 根据租户Id查询施工区域列表
     *
     * @param
     * @return
     */
    @ApiOperation("根据租户Id查询施工区域列表")
    @GetMapping("/findListByTenantId")
    public Result findListByTenantId(@RequestParam(value = "tenantId") Integer tenantId) {
        return areaService.findListByTenantId(tenantId);
    }

    /**
     * 验证区域名称重复
     *
     * @param
     * @return
     */
    @ApiOperation("验证区域名称重复")
    @GetMapping("/findAreaRepeat")
    public Result findAreaRepeat(@RequestParam(value = "name") String name) {
        return areaService.findAreaRepeat(name);
    }
}
