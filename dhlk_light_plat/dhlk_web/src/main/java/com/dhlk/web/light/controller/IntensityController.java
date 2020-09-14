package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.Intensity;
import com.dhlk.entity.light.IntensityInfo;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.IntensityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xkliu
 * @date 2020/6/30
 * <p>
 * 光感控制器
 */
@RestController
@RequestMapping(value = "/intensity")
@Api(description = "光感管理", value = "IntensityController")
public class IntensityController {

    @Autowired
    private IntensityService intensityService;

    /**
     * 新增/修改
     *
     * @param intensity
     * @param bindingResult
     * @return
     */
    @ApiOperation("新增/修改")
    @PostMapping("/save")
    public Result save(@Valid @RequestBody Intensity intensity, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if (result == null) {
            return intensityService.save(intensity);
        }
        return result;
    }


    /**
     * 数据查询
     *
     * @param
     * @return
     */
    @ApiOperation("数据查询")
    @GetMapping("/find")
    public Result findOne() {
        return intensityService.findOne();
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
        return intensityService.delete(ids);
    }

    /**
     * 光感控制
     * @return
     */
    @PostMapping(value = "/intensityContro")
//    @RequiresAuthentication
    public Result intensityContro(@RequestBody InfoBox<IntensityInfo> intensityInfo) {
        return intensityService.intensityContro(intensityInfo);
    }

    /**
     *记忆光感强度
     */
    @ApiOperation("记忆光感强度")
    @GetMapping(value = "/memoryIntensity")
    public Result memoryIntensity() {
        return intensityService.memoryIntensity();
    }

}
