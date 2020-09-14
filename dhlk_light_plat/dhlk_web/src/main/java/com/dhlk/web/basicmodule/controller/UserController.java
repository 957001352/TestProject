package com.dhlk.web.basicmodule.controller;

import com.alibaba.fastjson.JSON;
import com.dhlk.entity.basicmodule.User;
import com.dhlk.entity.light.SyncDataResult;
import com.dhlk.systemconst.Const;
import com.dhlk.web.basicmodule.service.UserService;
import com.dhlk.domain.Result;
import com.dhlk.web.light.service.DataSyncService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.dhlk.utils.ResultUtils;

import javax.validation.Valid;


/**
* 用户管理
*/
@RestController
@RequestMapping(value = "/user")
@Api(value = "UserController",description = "用户管理")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private DataSyncService dataSyncService;

    /**
     * 保存
     * @param
     * @return
     */
    @ApiOperation("新增/修改")
    @PostMapping(value = "/save")
    public Result save(@ApiParam(name="用户对象",value="传入json格式") @Valid @RequestBody User user, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if (result==null) {
            Result save = userService.save(user);
            if(save.getCode() == 0) {
                String operate = Const.SYNC_DATA_OPERATE_UPDATE;
                if(user.getId()==null){
                    operate = Const.SYNC_DATA_OPERATE_INSERT;
                }
                String userInfo = JSON.toJSONString(save.getData());
                User userResult = JSON.parseObject(userInfo, User.class);
                Result syncData = dataSyncService.syncDataToLocal(new SyncDataResult(Const.USER_TABLE_NAME, userInfo,operate,userResult.getTenantId()));
                if(syncData.getCode() != 0) save.setMsg("数据同步到本地失败");
            }
            return save;
        }
        return result;
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @ApiOperation("批量删除")
    @GetMapping(value = "/delete")
    public Result delete(@ApiParam(name="ids",value="数据id,多个时用逗号隔开，如 1,2",required=true) @RequestParam(value = "ids") String ids) {
        Result delete = userService.delete(ids);
        if(delete.getCode() == 0){
            Result syncData = dataSyncService.syncDataToLocal(new SyncDataResult<String>(Const.USER_TABLE_NAME, ids,Const.SYNC_DATA_OPERATE_DELETE));
            if(syncData.getCode() != 0) delete.setMsg("数据同步到本地失败");
        }
        return delete;
    }

    /**
    * 列表查询
     * @param name name为模糊查询，name如果没有或者为空就是全表扫描
    * @return result
    */
    @ApiOperation("根据用户名称查询")
    @GetMapping("/findList")
    public Result findList(@ApiParam(name="name",value="用户名称模糊查询") @RequestParam(value = "name", required = false) String name) {
        return  userService.findList(name);
    }
    /**
     * 修改密码
     * @param id
     * @param password
     * @return result
     */
    @ApiOperation("修改密码")
    @GetMapping("/updatePassword")
    public Result updatePassword(@RequestParam(value = "id") Integer id,
                                 @RequestParam(value = "password") String password) {
        return  userService.updatePassword(id,password);
    }

    /**
     * 查询用户所属的机构
     * @param id
     * @return result
     */
    @ApiOperation("查询用户所属的机构")
    @GetMapping("/findOrg")
    public Result updatePassword(@RequestParam(value = "id") Integer id) {
        return  userService.findOrg(id);
    }

    /**
     * 改变用户状态
     * @param id
     * @return result
     */
    @ApiOperation("禁用/启用")
    @GetMapping("/isEnable")
    public Result isEnable(@RequestParam(value = "id") Integer id,
                           @RequestParam(value = "status") Integer status) {
        return  userService.isEnable(id,status);
    }

    /**
     * 根据租户Id查询列表
     * @return result
     */
    @ApiOperation("根据租户Id查询")
    @GetMapping("/findListByTenantId")
    public Result findListByTenantId(@RequestParam(value = "tenantId", required = false) String tenantId) {
        return  userService.findListByTenantId(tenantId);
    }
}
