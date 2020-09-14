package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.DevicesAttrDetailDao;
import com.dhlk.basicmodule.service.dao.DevicesAttrSetDao;
import com.dhlk.basicmodule.service.dao.DevicesClassifyDetailDao;
import com.dhlk.basicmodule.service.service.DevicesAttrSetService;
import com.dhlk.entity.basicmodule.DevicesAttrDetail;
import com.dhlk.entity.basicmodule.DevicesAttrSet;
import com.dhlk.domain.Result;
import com.dhlk.util.AuthUserUtil;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
* @Author:         gchen
* @CreateDate:     2020/3/30 15:30
*/
@Service
@Transactional
public class DevicesAttrSetServiceImpl implements DevicesAttrSetService {

    @Autowired
    private DevicesAttrSetDao devicesAttrSetDao;
    @Autowired
    private DevicesAttrDetailDao devicesAttrDetailDao;
    @Autowired
    private DevicesClassifyDetailDao devicesClassifyDetailDao;
    @Autowired
    private AuthUserUtil authUserUtil;
    @Override
    public Result save(DevicesAttrSet devicesAttrSet) {
        Integer flag = -1;
        //设置租户ID
        devicesAttrSet.setTenantId(authUserUtil.tenantId());
        if(devicesAttrSetDao.isRepeatAttr(devicesAttrSet) > 0){
            return ResultUtils.error("属性名称已存在");
        }
        if(CheckUtils.isNull(devicesAttrSet.getId())){ //如果集合id为空的话就存储，不为空就修改

            flag = devicesAttrSetDao.insert(devicesAttrSet);
            if(!CheckUtils.isNull(devicesAttrSet.getAttrDetails())){ //判断属性集合中明细是否为空
                flag = devicesAttrDetailDao.insertDevicesAttrDetails(devicesAttrSet.getAttrDetails(),devicesAttrSet.getId());
            }
        }else{
            List<DevicesAttrDetail> attrDetails = devicesAttrSet.getAttrDetails();
            if(!CheckUtils.isNull(attrDetails)){
                //从数据库中取出对应属性集合的属性明细
                List<DevicesAttrDetail> BaseAttrDetails = devicesAttrDetailDao.findAttrDetailsByAttrSetId(devicesAttrSet.getId());
                //页面上删除了属性
                List<Integer> baseAttrDetailIds = BaseAttrDetails.stream().map(DevicesAttrDetail :: getId).collect(Collectors.toList());
                List<Integer> attrDetailIds = attrDetails.stream().map(DevicesAttrDetail :: getId).collect(Collectors.toList());
                List<Integer> subtractDelete = ListUtils.subtract(baseAttrDetailIds, attrDetailIds);
                //判断属性明细是否与分类绑定
                if(subtractDelete != null && subtractDelete.size() > 0){
                    if(devicesClassifyDetailDao.findDevicesClassifyDetailByDetail(subtractDelete) > 0){
                        return ResultUtils.error("变量已被绑定,无法删除");
                    }
                    flag = devicesAttrDetailDao.delete(subtractDelete);
                }
                //页面上添加了属性
                attrDetails.stream().forEach(attrDetail -> {
                    if(attrDetail.getId() == null){
                        attrDetail.setAttrSetId(devicesAttrSet.getId());
                        devicesAttrDetailDao.insert(attrDetail);
                    }else{
                        devicesAttrDetailDao.update(attrDetail);
                    }
                });
            }
            flag = devicesAttrSetDao.update(devicesAttrSet);
        }
        return flag>0? ResultUtils.success():ResultUtils.failure();
    }

    @Override
    public Result delete(Integer id) {
        Integer flag = -1;
        if(devicesClassifyDetailDao.findDevicesClassifyDetailByAttrSetId(id) > 0){//检查属性集合是否与分类绑定
            return ResultUtils.error("该参数已被绑定，无法删除");
        }
        flag = devicesAttrDetailDao.deleteByAttrSetId(id);//删除属性集合下所有的属性明细
        flag = devicesAttrSetDao.delete(id);//删除属性集合
        return flag > 0? ResultUtils.success():ResultUtils.failure();
    }

    @Override
    public Result findList(String name) {
        List<DevicesAttrSet> devicesAttrSets = devicesAttrSetDao.findList(name,authUserUtil.tenantId());
        return ResultUtils.success(devicesAttrSets);
    }

}
