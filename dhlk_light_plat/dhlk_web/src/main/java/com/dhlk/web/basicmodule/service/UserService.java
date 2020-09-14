package com.dhlk.web.basicmodule.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.User;
import com.dhlk.web.basicmodule.service.fbk.UserServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户管理
 */
@FeignClient(value = "basicmodule-service/user", fallback = UserServiceFbk.class)
public interface UserService {
    /**
     * 保存
     *
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody User user);

    /**
     * 删除
     *
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    Result delete(@RequestParam(value = "ids") String ids);

    /**
     * 列表查询
     *
     * @param name
     * @return result
     */
    @GetMapping("/findList")
    Result findList(@RequestParam(value = "name", required = false) String name);

    /**
     * 修改密码
     *
     * @param id
     * @param password
     * @return result
     */
    @GetMapping("/updatePassword")
    Result updatePassword(@RequestParam(value = "id") Integer id,
                          @RequestParam(value = "password") String password);

    /**
     * 查询用户所属的机构
     *
     * @param id
     * @return result
     */
    @GetMapping("/findOrg")
    Result findOrg(@RequestParam(value = "id") Integer id);

    /**
     * 改变用户状态
     *
     * @param id
     * @return result
     */
    @GetMapping("/isEnable")
    Result isEnable(@RequestParam(value = "id") Integer id,
                    @RequestParam(value = "status") Integer status);

    /**
     * 根据租户Id查询用户
     * @param tenantId
     * @return
     */
    @GetMapping("/findListByTenantId")
    Result findListByTenantId(@RequestParam(value = "tenantId", required = false) String tenantId);
}
