package com.dhlk.basicmodule.service.service.impl;/**
 * @创建人 wangq
 * @创建时间 2020/7/1
 * @描述
 */

import com.dhlk.basicmodule.service.dao.DeskTopDao;
import com.dhlk.basicmodule.service.service.DeskTopService;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.DeskTop;
import com.dhlk.entity.light.FaultCode;
import com.dhlk.enums.ResultEnum;
import com.dhlk.util.AuthUserUtil;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @program: dhlk.light.plat
 *
 * @description: 桌面菜单业务逻辑层
 *
 * @author: wqiang
 *
 * @create: 2020-07-01 15:03
 **/
@Service
public class DeskTopServiceImpl implements DeskTopService {

    @Autowired
    private DeskTopDao deskTopDao;
    @Autowired
    private AuthUserUtil authUserUtil;

    @Override
    public Result save(List<DeskTop> deskTopList) {

        Integer userId = authUserUtil.userId();
        //保存之前删除该用户下所有的菜单
        deskTopDao.deleteAllByUserId(userId);
        if(deskTopList == null || deskTopList.size() <= 0 ){
            return ResultUtils.success();
        }
        for (DeskTop deskTop: deskTopList) {
            deskTop.setUserId(userId);
        }
        return deskTopDao.insert(deskTopList) > 0 ? ResultUtils.success():ResultUtils.failure();
    }

    @Override
    public Result delete(String ids) {

        if (!CheckUtils.isNull(ids)) {
            if (deskTopDao.delete(Arrays.asList(ids.split(","))) > 0) {
                return  ResultUtils.success();
            }
        }

        return ResultUtils.failure();
    }

    @Override
    public Result findList(Integer userId, Integer pageNum, Integer pageSize) {
        if (!CheckUtils.checkId(pageNum) || !CheckUtils.checkId(pageSize)) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<DeskTop> deskTopList = deskTopDao.findList(userId);
        PageInfo<DeskTop> deskTopPage = new PageInfo<>(deskTopList);
        return ResultUtils.success(deskTopPage);
    }
}
