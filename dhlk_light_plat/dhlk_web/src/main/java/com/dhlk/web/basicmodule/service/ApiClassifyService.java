package com.dhlk.web.basicmodule.service;

import com.dhlk.entity.api.ApiClassify;
import com.dhlk.web.config.FeignMultipartConfig;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 生产设备管理
 **/
@FeignClient(value = "basicmodule-service/apiClassify",configuration = FeignMultipartConfig.class)
public interface ApiClassifyService {

    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody ApiClassify apiClassify);
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
    Result findPageList(@RequestParam(value = "parentId", required = false) Integer parentId,
                        @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);
    /**
     * 分类树列表查询
     * @return
     */
    @GetMapping("/findTreeList")
    Result findTreeList();
}
