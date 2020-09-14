package com.dhlk.web.basicmodule.service;

import com.dhlk.entity.api.ApiList;
import com.dhlk.web.basicmodule.service.fbk.ApiListServiceFbk;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 生产设备管理
 **/
@FeignClient(value = "basicmodule-service/apiList", fallback = ApiListServiceFbk.class)
public interface ApiListService {

    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody ApiList apiList);
    /**
     * 删除
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    Result delete(@RequestParam(value = "ids") String ids);

    /**
     * 列表查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/findPageList")
    public Result findPageList(@RequestParam(value = "classifyId", required = false) Integer classifyId,
                               @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);






    @PostMapping(value = "/importExcel",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result importExcel(@RequestPart(value = "file") MultipartFile file);




    /**
     * 导出列表查询
     */
    @GetMapping("/findExportList")
    Result findExportList();
}
