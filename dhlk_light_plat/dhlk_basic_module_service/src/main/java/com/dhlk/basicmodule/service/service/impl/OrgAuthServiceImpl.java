package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.OrgAuthDao;
import com.dhlk.basicmodule.service.service.OrgAuthService;
import com.dhlk.util.AuthUserUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.OrgAuth;
import com.dhlk.exceptions.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dhlk.systemconst.Const;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.DES;
import com.dhlk.utils.ResultUtils;

import java.util.Arrays;

/**
 * @Description  厂区网络接口私钥管理
 * @Author lpsong
 * @Date 2020/3/16
 */
@Service
public class OrgAuthServiceImpl implements OrgAuthService {
    @Autowired
    private OrgAuthDao orgAuthDao;

    @Autowired
    private AuthUserUtil authUserUtil;


    @Override
    public Result save(OrgAuth orgAuth) throws MyException {
        //新增
        Integer flag=0;
        if (CheckUtils.isNull(orgAuth.getId())) {
            orgAuth.setTenantId(authUserUtil.tenantId());
            flag=orgAuthDao.insert(orgAuth);
        }else{//修改
            flag=orgAuthDao.update(orgAuth);
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result delete(String ids) throws MyException {
        if (!CheckUtils.isNull(ids)) {
            if (orgAuthDao.delete(Arrays.asList(ids.split(","))) > 0) {
                return  ResultUtils.success();
            }
        }
        return ResultUtils.failure();
    }

    @Override
    public Result findPageList(Integer pageNum, Integer pageSize) throws MyException {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<OrgAuth> pageInfo = new PageInfo<OrgAuth>(orgAuthDao.findList(authUserUtil.tenantId()));
        return ResultUtils.success(pageInfo);
    }

    @Override
    public Result authCenter(String key) {
        if (!CheckUtils.isNull(key)) {
            if(DES.decryptDES(key).equals(DES.md5(Const.FLUMNAUTHPRIVATEKEY))){
                return  ResultUtils.success();
            }
            return ResultUtils.failure();
            //解密秘钥，拆分出机构code和私钥
//            String strs[]= DES.decryptDES(key).split("#dhlk#");
//            if(!CheckUtils.isNull(strs)&&strs.length>1){
//                //根据机构code查询出私钥，并进行比对判断是否相等
//                OrgAuth orgAuth= orgAuthDao.findAuthByOrg(strs[0]);
//                if(orgAuth!=null){
//                     if(strs[1].equals(orgAuth.getAuth())){
//                         return  ResultUtils.success();
//                     }else{
//                         return ResultUtils.failure();
//                     }
//                }
//            }
        }
        return ResultUtils.failure();
    }

    @Override
    public Result findAuthKey(String orgCode) {
        if (!CheckUtils.isNull(orgCode)) {
            OrgAuth orgAuth= orgAuthDao.findAuthByOrg(orgCode);
            if(orgAuth!=null){
                //拼接结构code和私钥作为认证秘钥
                String key=orgAuth.getTenantId()+"#dhlk#"+orgAuth.getAuth();
                //加密后返回
                return ResultUtils.success(DES.encryptDES(key));
            }
            return ResultUtils.error("工厂代码错误");
        }
        return ResultUtils.error("工厂代码为空");
    }


}