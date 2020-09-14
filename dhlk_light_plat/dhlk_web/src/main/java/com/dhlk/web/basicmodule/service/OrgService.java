package com.dhlk.web.basicmodule.service;

import com.dhlk.entity.basicmodule.Org;
import com.dhlk.web.basicmodule.service.fbk.OrgServiceFbk;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 厂区
 */
@FeignClient(value = "basicmodule-service/org", fallback = OrgServiceFbk.class)
public interface OrgService {
        /**
         * 添加、修改厂区
         *
         * @param
         * @return
         */
        @PostMapping(value = "/save")
        Result save(@RequestBody Org org);

        /**
         * 删除厂区
         *
         * @param id
         * @return result
         */
        @GetMapping(value = "/delete")
        Result delete(@RequestParam(value = "id") Integer id);

        @GetMapping("/findPageList")
        Result findPageList(@RequestParam(value = "parentId") Integer parentId,
                            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);

        @GetMapping("/findTreeList")
        Result findTreeList();

        /**
         * 查询机构下所有的用户
         * @param
         * @return
         */
        @GetMapping("/findPageUsersByOrg")
        Result findPageUserByOrgId(@RequestParam(value = "orgId") Integer orgId,
                             @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);
}