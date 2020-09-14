package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.LedVersionInfo;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.LedVersionInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
/**
 * @program: dhlk.light.plat
 *
 * @description: 灯版本信息
 *
 * @author: wqiang
 *
 * @create: 2020-06-30 12:06
 **/

@RestController
@RequestMapping(value = "/ledVersionInfo")
@Api(description = "灯版本信息", value = "LedVersionInfoController")
public class LedVersionInfoController {

    @Autowired
    private LedVersionInfoService ledVersionInfoService;

    /**
     * 新增/修改版本信息
     *
     * @param ledVersionInfo
     * @param bindingResult
     * @return
     */
    @ApiOperation("新增/修改故障代码")
    @PostMapping("/save")
    public Result save(@RequestBody LedVersionInfo ledVersionInfo, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if (result == null) {
            return ledVersionInfoService.save(ledVersionInfo);
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
        return ledVersionInfoService.delete(ids);
    }


    /**
     * 施工信息列表查询
     *
     * @param
     * @return
     */
    @ApiOperation("灯版本信息查询")
    @GetMapping("/findList")
    public Result findList(  @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "creator", required = false) String creator,
                             @RequestParam(value = "createTime", required = false) String createTime,
                             @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return ledVersionInfoService.findList(name, creator,createTime, pageNum, pageSize);
    }

    /**
     * 固件升级
     */
    @PostMapping(value = "/firmwareUpgrade")
    public Result firmwareUpgrade(@RequestBody InfoBox<LedVersionInfo> infoBox) {
        return ledVersionInfoService.firmwareUpgrade(infoBox);
    }

}
