package com.dhlk.web.basicmodule.controller;

import com.dhlk.entity.basicmodule.Org;
import com.dhlk.web.basicmodule.service.OrgService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.dhlk.utils.ResultUtils;

import javax.validation.Valid;


/**
* 厂区管理
*/
@RestController
@RequestMapping(value = "/org")
@Api(value = "OrgController",description = "机构管理")
public class OrgController {
    @Autowired
    private OrgService orgService;

    /**
     * 添加、修改厂区
     * @param org
     * @return
     */
    @ApiOperation("新增/修改")
    @PostMapping(value = "/save")
    public Result save(@ApiParam(name="机构对象",value="传入json格式") @Valid @RequestBody Org org, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if(result == null){
            return orgService.save(org);
        }
        return result;
    }
    /**
     * 删除厂区
     * @param id
     * @return result
     */
    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "id") Integer id) {
        return orgService.delete(id);
    }

    /**
     * 查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation("分页查询")
    @GetMapping("/findPageList")
    public Result findPageList(@ApiParam(name="parentId",value = "父级机构id",defaultValue = "0") @RequestParam(value = "parentId",required = false,defaultValue = "0") Integer parentId,
                               @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  orgService.findPageList(parentId,pageNum, pageSize);
    }

    /**
     * 机构树查询
     * @param
     * @return
     */
    @ApiOperation("机构树查询")
    @GetMapping("/findTreeList")
    public Result findTreeList() {
        return  orgService.findTreeList();
    }

    /**
     * 查询机构下所有的用户
     * @param
     * @return
     */
    @ApiOperation("根据机构查询用户")
    @GetMapping("/findPageUserByOrg")
    public Result findPageUserByOrg(@ApiParam(name="orgId",value = "机构id") @RequestParam(value = "orgId") Integer orgId,
                                @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  orgService.findPageUserByOrgId(orgId,pageNum,pageSize);
    }

}
