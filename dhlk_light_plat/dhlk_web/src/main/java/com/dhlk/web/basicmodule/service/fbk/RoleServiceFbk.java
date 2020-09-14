package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.entity.basicmodule.Role;
import com.dhlk.web.basicmodule.service.RoleService;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import org.springframework.stereotype.Service;
import com.dhlk.utils.ResultUtils;

/**
 * 角色管理
 */
@Service
public class RoleServiceFbk  implements RoleService {

    @Override
    public Result save(Role role) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete( String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result selectRoleById(Integer id) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }


    @Override
    public Result findPageList(Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result selectRoleByName(String name) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findAllList() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result selectRoleByUserId(Integer userId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result selectUserByRoleId(Integer roleId,Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

}
