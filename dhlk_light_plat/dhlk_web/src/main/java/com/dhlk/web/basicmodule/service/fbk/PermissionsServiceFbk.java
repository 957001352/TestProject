package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.entity.basicmodule.Permissions;
import com.dhlk.web.basicmodule.service.PermissionsService;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import org.springframework.stereotype.Service;
import com.dhlk.utils.ResultUtils;

import java.util.List;

/**
 * 权限管理
 */
@Service
public class PermissionsServiceFbk implements PermissionsService {

    @Override
    public Result insert(Integer roleId, String menuIds) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result deleteByRoleIds(String  roleIds) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public List<Permissions> selectList(Integer roleId, Integer menuId) throws Exception {
        return null;
    }
}
