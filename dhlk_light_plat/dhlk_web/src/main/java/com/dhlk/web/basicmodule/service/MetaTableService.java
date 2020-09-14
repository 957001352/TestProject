package com.dhlk.web.basicmodule.service;

import com.dhlk.entity.hive.MetaTable;
import com.dhlk.web.basicmodule.service.fbk.MetaTableServiceFbk;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * hive元数据表管理
 */
@FeignClient(value = "basicmodule-service/metaTable", fallback = MetaTableServiceFbk.class)
public interface MetaTableService {
        /**
         * 保存
         * @param
         * @return
         */
        @PostMapping(value = "/save")
        public Result save(@RequestBody MetaTable metaTable);
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
        Result findPageList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);
        

}
