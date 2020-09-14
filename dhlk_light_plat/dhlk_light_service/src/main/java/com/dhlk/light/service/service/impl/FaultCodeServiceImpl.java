package com.dhlk.light.service.service.impl;


import com.dhlk.domain.Result;
import com.dhlk.entity.light.Construction;
import com.dhlk.entity.light.FaultCode;
import com.dhlk.enums.ResultEnum;
import com.dhlk.light.service.dao.FaultCodeDao;
import com.dhlk.light.service.service.FaultCodeService;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import com.dhlk.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**

/**
 * @program: dhlk.multi.tenant
 *
 * @description: 故障代码service
 *
 * @author: wqiang
 *
 * @create: 2020-06-04 17:43
 **/
@Service
@Transactional
public class FaultCodeServiceImpl implements FaultCodeService {

    @Autowired
    private FaultCodeDao faultCodeDao;
    /**

     *@描述 新增或修改故障代码

     *@参数

     *@返回值
     */
    @Override
    public Result save(FaultCode faultCode) {
        int flag = 0;
        //id为空新增，存在更新
        if(CheckUtils.isNull(faultCode.getId())){
            //校验 名字和编码都不能重复
            List<FaultCode> list = faultCodeDao.findList(faultCode.getName(), faultCode.getCode(),null, 0);
            if( list != null && list.size()> 0){
                for(FaultCode fc: list){
                    if(fc.getName().equals(faultCode.getName())){
                        return ResultUtils.error("编码名称已存在，不能重复添加！");
                    }
                    if(fc.getCode().equals(faultCode.getCode())){
                        return ResultUtils.error("故障代码已存在，不能重复添加！");
                    }
                }
            }
            flag = faultCodeDao.insert(faultCode);
        }else{
            //校验 名字和编码都不能重复
            List<FaultCode> list = faultCodeDao.findList(faultCode.getName(), faultCode.getCode(),faultCode.getId(), 2);
            if( list != null && list.size()> 0){
                for(FaultCode fc: list){
                    if(fc.getName().equals(faultCode.getName())){
                        return ResultUtils.error("编码名称已存在，不能重复添加！");
                    }
                    if(fc.getCode().equals(faultCode.getCode())){
                        return ResultUtils.error("故障代码已存在，不能重复添加！");
                    }
                }
            }
            flag = faultCodeDao.update(faultCode);
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    /**

     *@描述  批量物理删除

     *@参数

     *@返回值
     */
    @Override
    public Result delete(String ids) {
            if(!CheckUtils.isNull(ids)){
                if(faultCodeDao.delete(Arrays.asList(ids.split(","))) > 0){
                    return ResultUtils.success();
                }

            }
        return ResultUtils.failure();
    }

    /**

     *@描述 查询列表 (分页)

     *@参数

     *@返回值
     */
    @Override
    public Result findList(String name, String code, Integer pageNum, Integer pageSize) {
        if (!CheckUtils.checkId(pageNum) || !CheckUtils.checkId(pageSize)) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<FaultCode> faultCodeList = faultCodeDao.findList(name, code,null,1); // flag 1 正常查询   0校验名字和代码都不能重复
        PageInfo<FaultCode> faultCodePage = new PageInfo<>(faultCodeList);
        return ResultUtils.success(faultCodePage);
    }
}
