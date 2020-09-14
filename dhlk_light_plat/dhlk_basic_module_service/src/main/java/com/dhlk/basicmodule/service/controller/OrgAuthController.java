package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.OrgAuthService;
import com.dhlk.entity.basicmodule.OrgAuth;
import com.dhlk.domain.Result;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 厂区接口访问秘钥管理
*/
@RestController
@RequestMapping(value = "/orgAuth")
public class OrgAuthController {
    @Autowired
    private OrgAuthService orgAuthService;

    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresAuthentication
    public Result save(@RequestBody OrgAuth orgAuth) {
        return orgAuthService.save(orgAuth);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresAuthentication
    public Result delete(@RequestParam(value = "id") String ids) {
        return orgAuthService.delete(ids);
    }

    /**
    * 列表查询
     * @param pageNum
     * @param pageSize
    * @return
    */
    @GetMapping("/findPageList")
    @RequiresAuthentication
    public Result findPageList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  orgAuthService.findPageList(pageNum, pageSize);
    }


    /**
     * 认证授权中心
     * @param key 秘钥
     */
    @GetMapping("/authCenter")
    public Result authCenter(@RequestParam(value = "key", required = true) String key) {
        return  orgAuthService.authCenter(key);
    }
    /**
     * 获取秘钥
     * @param orgCode 机构code
     */
    @GetMapping("/findAuthKey")
    public Result findAuthKey(@RequestParam(value = "orgCode", required = true) String orgCode) {
        return  orgAuthService.findAuthKey(orgCode);
    }
}
