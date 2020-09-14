package com.dhlk.web.basicmodule.service;

import com.dhlk.entity.basicmodule.OrgAuth;
import com.dhlk.web.basicmodule.service.fbk.OrgAuthServiceFbk;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 厂区访问秘钥
 */
@FeignClient(value = "basicmodule-service/orgAuth", fallback = OrgAuthServiceFbk.class)
public interface OrgAuthService {
        /**
         * 保存
         * @param
         * @return
         */
        @PostMapping(value = "/save")
        Result save(@RequestBody OrgAuth orgAuth);
        /**
         * 删除
         * @param ids
         * @return result
         */
        @GetMapping(value = "/delete")
        Result delete(@RequestParam(value = "id") String ids);

        /**
         * 列表查询
         * @param pageNum
         * @param pageSize
         * @return
         */
        @GetMapping("/findPageList")
        Result findPageList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);
        /**
         * 认证授权中心
         * @param key 秘钥
         */
        @GetMapping("/authCenter")
        Result authCenter(@RequestParam(value = "key", required = true) String key);
        /**
         * 获取秘钥
         * @param orgCode 机构code
         */
        @GetMapping("/findAuthKey")
        Result findAuthKey(@RequestParam(value = "orgCode", required = true) String orgCode);
}
