package com.dhlk.web.basicmodule.controller;

import com.dhlk.entity.api.ApiClassify;
import com.dhlk.web.basicmodule.service.ApiClassifyService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.dhlk.utils.ExcelUtil;
import com.dhlk.utils.ResultUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;


/**
* 接口分类管理
*/
@RestController
@RequestMapping(value = "/apiClassify")
@Api(description = "接口分类管理")
public class ApiClassifyController {
    @Autowired
    private ApiClassifyService apiClassifyService;

    /**
     * 保存
     * @param
     * @return
     */
    @ApiOperation("新增/修改")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody ApiClassify apiClassify, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if (result==null) {
            return apiClassifyService.save(apiClassify);
        }
        return result;
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return apiClassifyService.delete(ids);
    }

    /**
     * 列表查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation("分页查询")
    @GetMapping("/findPageList")
    public Result findPageList(@RequestParam(value = "parentId", required = false) Integer parentId,
                               @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  apiClassifyService.findPageList(parentId,pageNum, pageSize);
    }
    /**
     * 分类树列表查询
     * @return
     */
    @ApiOperation("api分类查询")
    @GetMapping("/findTreeList")
    public Result findTreeList() {
        return  apiClassifyService.findTreeList();
    }
}
