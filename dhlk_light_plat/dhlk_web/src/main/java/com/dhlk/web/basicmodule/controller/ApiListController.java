package com.dhlk.web.basicmodule.controller;

import com.dhlk.entity.api.ApiList;
import com.dhlk.utils.ExcelUtil;
import com.dhlk.web.basicmodule.service.ApiListService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.dhlk.utils.ResultUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;


/**
* api接口管理
*/
@RestController
@RequestMapping(value = "/apiList")
@Api(description = "Api接口管理")
public class ApiListController {
    @Autowired
    private ApiListService apiListService;

    /**
     * 保存
     * @param
     * @return
     */
    @ApiOperation("新增/修改")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody ApiList apiList, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if (result==null) {
            return apiListService.save(apiList);
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
        return apiListService.delete(ids);
    }

    /**
    * 列表查询
     * @param pageNum
     * @param pageSize
    * @return
    */
    @ApiOperation("分页查询")
    @GetMapping("/findPageList")
    public Result findPageList(@RequestParam(value = "classifyId", required = false) Integer classifyId,
                               @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  apiListService.findPageList(classifyId,pageNum, pageSize);
    }



    @ApiOperation("文件导出")
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response){
        try{
            List<LinkedHashMap<String,Object>> list= (List<LinkedHashMap<String, Object>>) apiListService.findExportList().getData();
            ExcelUtil.exportExcel(response, Arrays.asList(new String[]{"名称","版本","说明"}), list, "测试excel导出");
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    @ApiOperation("文件导入")
    @PostMapping(value = "/importExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result importExcel(@RequestPart(value = "file") MultipartFile file) throws Exception{
        return apiListService.importExcel(file);
    }
}
