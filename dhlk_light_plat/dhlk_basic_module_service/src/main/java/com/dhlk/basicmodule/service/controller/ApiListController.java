package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.ApiListService;
import com.dhlk.entity.api.ApiList;
import com.dhlk.domain.Result;
import com.dhlk.utils.ExcelUtil;
import com.dhlk.utils.ResultUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* api接口管理
*/
@RestController
@RequestMapping(value = "/apiList")
public class ApiListController {
    @Autowired
    private ApiListService apiListService;

    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("apiList:save")
    public Result save(@RequestBody ApiList apiList) {
        return apiListService.save(apiList);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresPermissions("apiList:delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return apiListService.delete(ids);
    }

    /**
    * 列表查询
     * @param pageNum
     * @param pageSize
    * @return
    */
    @GetMapping("/findPageList")
    @RequiresAuthentication
    public Result findPageList(@RequestParam(value = "classifyId", required = false) Integer classifyId,
                               @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  apiListService.findPageList(classifyId,pageNum, pageSize);
    }


    @PostMapping(value = "/importExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //@RequiresPermissions("apiList:importExcel")
    public Result importExcel(@RequestParam(value = "file") MultipartFile file) {
        return  apiListService.insertBatch(file);
    }
    /**
     * 导出列表查询
     */
    @GetMapping("/findExportList")
    //@RequiresAuthentication
    public Result findExportList() {
        return  apiListService.findExportList();
    }
}
