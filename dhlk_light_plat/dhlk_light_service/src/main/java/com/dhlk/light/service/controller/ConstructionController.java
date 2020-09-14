package com.dhlk.light.service.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Construction;
import com.dhlk.light.service.service.ConstructionService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xkliu
 * @date 2020/6/4
 * <p>
 * 施工信息控制器
 */
@RestController
@RequestMapping(value = "/construction")
@Api(value = "ConstructionController", description = "施工信息管理")
public class ConstructionController {

    @Autowired
    private ConstructionService constructionService;

    /**
     * 保存
     *
     * @param construction
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("construction:save")
    public Result save(@RequestBody Construction construction) {
        return constructionService.save(construction);
    }

    /**
     * 列表查询
     *
     * @param implPeople
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/findList")
    @RequiresAuthentication
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
     * @return
     */
    @GetMapping("/delete")
    @RequiresAuthentication
    public Result delete(@RequestParam(value = "ids") String ids) {
        return constructionService.delete(ids);
    }
}
