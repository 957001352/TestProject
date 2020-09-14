package com.dhlk.web.light.controller;/**
 * @创建人 wangq
 * @创建时间 2020/6/5
 * @描述
 */

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Construction;
import com.dhlk.entity.light.FaultCode;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.FaultCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: dhlk.multi.tenant
 *
 * @description: 故障代码Controller
 *
 * @author: wqiang
 *
 * @create: 2020-06-05 09:20
 **/
@RestController
@RequestMapping(value = "/faultCode")
@Api(description = "故障代码", value = "FaultCodeController")
public class FaultCodeController {

    @Autowired
    FaultCodeService faultCodeService;

    /**
     * 新增/修改施工信息
     *
     * @param faultCode
     * @param bindingResult
     * @return
     */
    @ApiOperation("新增/修改故障代码")
    @PostMapping("/save")
    public Result save(@RequestBody FaultCode faultCode, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if (result == null) {
            return faultCodeService.save(faultCode);
        }
        return result;
    }

    /**
     * 施工信息列表查询
     *
     * @param
     * @return
     */
    @ApiOperation("故障代码查询")
    @GetMapping("/findList")
    public Result findList(@RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "code", required = false) String code,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return faultCodeService.findList(name, code, pageNum, pageSize);
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
        return faultCodeService.delete(ids);
    }

}
