package com.dhlk.light.service.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Construction;
import com.dhlk.entity.light.OriginalPower;
import com.dhlk.light.service.service.OriginalPowerService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @return
     */
    @PostMapping("/save")
    @RequiresPermissions("originalPower:save")
    public Result save(@RequestBody OriginalPower originalPower) {
        return originalPowerService.save(originalPower);
    }


    /**
     * 企业历史照明功率维护数据查询
     *
     * @param
     * @return
     */
    @GetMapping("/findOne")
    @RequiresAuthentication
    public Result findOne() {
        return originalPowerService.findOne();
    }

    /**
     * 删除
     *
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresAuthentication
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
