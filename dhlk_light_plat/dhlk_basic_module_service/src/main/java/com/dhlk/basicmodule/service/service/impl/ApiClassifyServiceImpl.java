package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.ApiClassifyDao;
import com.dhlk.basicmodule.service.service.ApiClassifyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.dhlk.domain.Result;
import com.dhlk.entity.api.ApiClassify;
import com.dhlk.exceptions.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/13
 */
@Service
public class ApiClassifyServiceImpl implements ApiClassifyService {
    @Autowired
    private ApiClassifyDao apiClassifyDao;
    @Override
    public Result save(ApiClassify apiClassify) throws MyException {
        if(CheckUtils.isNull(apiClassify.getClassName())){
            return  ResultUtils.error("分类名称不能为空");
        }
        if(apiClassifyDao.isRepeatName(apiClassify)>0){
           return  ResultUtils.error("分类名称重复");
        }
        //新增
        Integer flag=0;
        if (CheckUtils.isNull(apiClassify.getId())) {
            flag=apiClassifyDao.insert(apiClassify);
        }else{//修改
            flag=apiClassifyDao.update(apiClassify);
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result delete(String ids) throws MyException{
        if (!CheckUtils.isNull(ids)) {
            if (apiClassifyDao.delete(Arrays.asList(ids.split(","))) > 0) {
                return  ResultUtils.success();
            }
        }
        return ResultUtils.failure();
    }

    @Override
    public Result findPageList(Integer parentId, Integer pageNum, Integer pageSize) throws MyException{
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<ApiClassify> pageInfo = new PageInfo<ApiClassify>(apiClassifyDao.findList(parentId));
        return ResultUtils.success(pageInfo);
    }

    @Override
    public Result findTreeList(){
        List<ApiClassify> list=apiClassifyDao.findList(null);
        List<ApiClassify> treeLi = new ArrayList<ApiClassify>();
        /* 适配转换 */
        for (ApiClassify pojo : list) {
            if (pojo.getParentId().intValue() == 0) {
                treeBuilder(pojo, list);
                treeLi.add(pojo);
            }
         }
        return ResultUtils.success(treeLi);
    }
    /**
   *  构建数据树
    * @param mt
    * @param list
   */
    private void treeBuilder(ApiClassify mt, List<ApiClassify> list) {
        for (ApiClassify child : list) {
            if (mt.getId().intValue() == child.getParentId()) {
                treeBuilder(child, list);
                mt.getChildList().add(child);
            }
        }
    }
}