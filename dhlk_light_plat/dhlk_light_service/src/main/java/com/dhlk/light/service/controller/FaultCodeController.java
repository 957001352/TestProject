package com.dhlk.light.service.controller;/**
 * @创建人 wangq
 * @创建时间 2020/6/5
 * @描述
 */

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Construction;
import com.dhlk.entity.light.FaultCode;
import com.dhlk.light.service.service.FaultCodeService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: dhlk.multi.tenant
 *
 * @description: 故障代码controller
 *
 * @author: wqiang
 *
 * @create: 2020-06-05 09:12
 **/

@RestController
@RequestMapping(value = "/faultCode")
@Api(value = "FaultCodeController", description = "故障代码")
public class FaultCodeController {

    @Autowired
    FaultCodeService faultCodeService;

    /**
     * 保存
     *
     * @param faultCode
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresAuthentication
    public Result save(@RequestBody FaultCode faultCode) {
        return faultCodeService.save(faultCode);
    }


    /**
     * 列表查询
     *
     * @param name
     * @param code
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/findList")
    @RequiresAuthentication
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
     * @return
     */
    @GetMapping("/delete")
    @RequiresAuthentication
    public Result delete(@RequestParam(value = "ids") String ids) {
        return faultCodeService.delete(ids);
    }
}
