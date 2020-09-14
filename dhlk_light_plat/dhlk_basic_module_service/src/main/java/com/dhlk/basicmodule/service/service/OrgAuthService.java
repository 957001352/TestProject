package com.dhlk.basicmodule.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.OrgAuth;

/**
 * 厂区访问秘钥
 */
public interface OrgAuthService{
        /**
         * 新增/修改
         * 判断秘钥是否重复
         */
        Result save(OrgAuth orgAuth);
        /**
         * 物理删除
         * @param ids
         */
        Result delete(String ids);
        /**
         * 分页查询
         * @param pageNum
         * @param pageSize
         */
        Result findPageList(Integer pageNum, Integer pageSize);


        /**
         * 认证授权中心
         * @param key 秘钥
         */
        Result authCenter(String key);
        /**
         * 获取秘钥
         * @param orgCode 单位code
         */
        Result findAuthKey(String orgCode);



        }
