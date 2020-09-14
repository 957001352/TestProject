package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.OriginalPower;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.OriginalPowerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xkliu
 * @date 2020/6/10
 * <p>
 * 企业历史照明功率维护控制器
 */

@RestController
@RequestMapping(value = "/originalPower")
@Api(description = "系统配置", value = "OriginalPowerController")
public class OriginalPowerController {

    @Autowired
    private OriginalPowerService originalPowerService;

    /**
     * 新增/修改企业历史照明功率维护
     *
     * @param originalPower
     * @param bindingResult
     * @return
     */
    @ApiOperation("新增/修改企业历史照明功率维护")
    @PostMapping("/save")
    public Result save(@Valid @RequestBody OriginalPower originalPower, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if (result == null) {
            return originalPowerService.save(originalPower);
        }
        return result;
    }


    /**
     * 企业历史照明功率维数据查询
     *
     * @param
     * @return
     */
    @ApiOperation("企业历史照明功率维护数据查询")
    @GetMapping("/find")
    public Result findOne() {
        return originalPowerService.findOne();
    }

    /**
     * 删除
     *
     * @param ids
     * @return result
     */
    @ApiOperation("物理删除")
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return originalPowerService.delete(ids);
    }

    /**
     * 预设亮度设置
     *
     * @return
     */
    @GetMapping("/preBrightness")
    public Result preBrightness(@RequestParam(value = "preBrightness") Integer preBrightness) {
        return originalPowerService.preBrightness(preBrightness);
    }
}
