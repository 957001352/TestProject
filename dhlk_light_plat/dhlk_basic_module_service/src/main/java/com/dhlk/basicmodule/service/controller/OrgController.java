package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.OrgService;
import com.dhlk.entity.basicmodule.Org;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 厂区管理
 */
@RestController
@Api(value = "OrgController", description = "厂区管理")
@RequestMapping(value = "/org")
public class OrgController {
    @Autowired
    private OrgService orgService;

    /**
     * 添加、修改厂区
     * @param org
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("org:save")
    public Result save(@RequestBody Org org) {
        return orgService.save(org);
    }
    /**
     * 删除厂区
     * @param id
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresPermissions("org:delete")
    public Result delete(@RequestParam(value = "id") Integer id) {
        return orgService.delete(id);
    }

    /**
     * 查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/findPageList")
    @RequiresPermissions("dhlk:view")
    public Result findPageList(@RequestParam(value = "parentId",required = false,defaultValue = "0") Integer parentId,
                               @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  orgService.findPageList(parentId,pageNum, pageSize);
    }

    /**
     * 机构树查询
     * @param
     * @return
     */
    @GetMapping("/findTreeList")
    @RequiresPermissions("dhlk:view")
    public Result findTreeList() {
        return  orgService.findTreeList();
    }

    /**
     * 查询机构下所有的用户
     * @param
     * @return
     */
    @GetMapping("/findPageUsersByOrg")
    @RequiresPermissions("dhlk:view")
    public Result findPageUsersByOrg(@RequestParam(value = "orgId") Integer orgId,
                                     @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  orgService.findPageUserByOrgId(orgId,pageNum,pageSize);
    }
}

