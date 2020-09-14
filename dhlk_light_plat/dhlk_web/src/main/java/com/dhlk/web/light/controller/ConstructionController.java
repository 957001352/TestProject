package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Construction;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.ConstructionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xkliu
 * @date 2020/6/3
 * <p>
 * 施工信息控制器
 */
@RestController
@RequestMapping(value = "/construction")
@Api(description = "施工信息管理", value = "ConstructionController")
public class ConstructionController {


    @Autowired
    private ConstructionService constructionService;

    /**
     * 新增/修改施工信息
     *
     * @param construction
     * @param bindingResult
     * @return
     */
    @ApiOperation("新增/修改施工信息")
    @PostMapping("/save")
    public Result save(@Valid @RequestBody Construction construction, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if (result == null) {
            return constructionService.save(construction);
        }
        return result;
    }


    /**
     * 施工信息列表查询
     *
     * @param
     * @return
     */
    @ApiOperation("施工信息列表查询")
    @GetMapping("/findList")
    public Result findList(@RequestParam(value = "implPeople", required = false) String implPeople,
                           @RequestParam(value = "startDate", required = false) String startDate,
                           @RequestParam(value = "endDate", required = false) String endDate,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return constructionService.findList(implPeople, startDate, endDate, pageNum, pageSize);
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
        return constructionService.delete(ids);
    }


}
