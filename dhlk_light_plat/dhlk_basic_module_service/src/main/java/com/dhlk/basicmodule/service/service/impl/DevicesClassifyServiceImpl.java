package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.DevicesClassifyDao;
import com.dhlk.basicmodule.service.dao.DevicesClassifyDetailDao;
import com.dhlk.basicmodule.service.dao.ProductDevicesDao;
import com.dhlk.basicmodule.service.service.DevicesClassifyService;
import com.dhlk.entity.basicmodule.DevicesClassify;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.DevicesClassifyDetail;
import com.dhlk.util.AuthUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
* @Author:         gchen
* @CreateDate:     2020/3/31 10:19
*/
@Service
@Transactional
public class DevicesClassifyServiceImpl implements DevicesClassifyService {
    @Autowired
    private DevicesClassifyDao devicesClassifyDao;
    @Autowired
    private DevicesClassifyDetailDao devicesClassifyDetailDao;
    @Autowired
    private ProductDevicesDao productDevicesDao;
    @Autowired
    private AuthUserUtil authUserUtil;
    @Value("${attachment.path}")
    private String attachmentPath;
    @Override
    @Transactional
    public Result save(DevicesClassify devicesClassify) {
        if(CheckUtils.isNull(devicesClassify.getClassifyName())){
            return ResultUtils.error("分类名称不能为空");
        }
        Integer flag = -1;
        //设置租户ID
        devicesClassify.setTenantId(authUserUtil.tenantId());
        if(devicesClassifyDao.isRepeatName(devicesClassify)>0){
            return ResultUtils.error("分类名称不能重复");
        }
        if(devicesClassify.getId() == null){
            return ResultUtils.error("请上传文件获取ID");
        }
        if(devicesClassifyDao.findDevicesClassifyById(devicesClassify.getId()) == null){ //如果分类id为空的话就存储，不为空就修改
            List<LinkedHashMap<String, Object>> attrSet = devicesClassify.getAttrSet();
            List<DevicesClassifyDetail> devicesClassifyDetails = getDevicesClassifyDetailsByAttrSet(attrSet, devicesClassify.getId());
            if(devicesClassifyDetails != null && devicesClassifyDetails.size() > 0){
                flag = devicesClassifyDetailDao.insertDevicesClassifyDetails(devicesClassifyDetails);
            }
            flag = devicesClassifyDao.insert(devicesClassify);
        }else{
            String devicesClassifyId = devicesClassify.getId();
            flag = devicesClassifyDetailDao.deleteByDevicesClassifyId(devicesClassifyId);
            List<LinkedHashMap<String, Object>> attrSet = devicesClassify.getAttrSet();
            List<DevicesClassifyDetail> devicesClassifyDetails = getDevicesClassifyDetailsByAttrSet(attrSet, devicesClassifyId);
            if(devicesClassifyDetails != null && devicesClassifyDetails.size() > 0){
                    flag = devicesClassifyDetailDao.insertDevicesClassifyDetails(devicesClassifyDetails);
            }
            flag = devicesClassifyDao.update(devicesClassify);
        }
        return flag>0? ResultUtils.success():ResultUtils.failure();
    }

    @Override
    public Result delete(String id) {
        Integer flag = -1;
        if(productDevicesDao.isBoundClassify(id) > 0){//检查设备类型是否与设备绑定
            return ResultUtils.error("该类型已被绑定，无法删除");
        }
        flag = devicesClassifyDetailDao.deleteByDevicesClassifyId(id);//删除属性集合下所有的属性明细
        flag = devicesClassifyDao.delete(id);//删除设备分类
        return flag > 0? ResultUtils.success():ResultUtils.failure();
    }

    @Override
    public Result findList(String classifyName) {
        List<DevicesClassify> list = devicesClassifyDao.findList(classifyName,authUserUtil.tenantId(),attachmentPath);
        list.stream().forEach(devicesClassify -> devicesClassify.setNameCount(devicesClassify.getAttrSet().size()));
        return ResultUtils.success(list);
    }


    //根据attrSet添加编辑设备分类属性集合
    public List<DevicesClassifyDetail> getDevicesClassifyDetailsByAttrSet(List<LinkedHashMap<String, Object>> attrSet,String devicesClassifyId){
        if(attrSet != null && attrSet.size() > 0){
            List<DevicesClassifyDetail> devicesClassifyDetails = new ArrayList<>();
            for (LinkedHashMap<String, Object> lhm:attrSet) {
                Integer setId = (Integer) lhm.get("setId");
                String detailIds = (String) lhm.get("detailIds");
                if(!CheckUtils.isNull(detailIds)){
                    String[] detailId = detailIds.split(",");
                    for (String id:detailId) {
                        DevicesClassifyDetail devicesClassifyDetail = new DevicesClassifyDetail();
                        devicesClassifyDetail.setAttrSetId(setId);
                        devicesClassifyDetail.setAttrDetailId(Integer.parseInt(id));
                        devicesClassifyDetail.setDevicesClassifyId(devicesClassifyId);
                        devicesClassifyDetails.add(devicesClassifyDetail);
                    }
                }
            }
            return devicesClassifyDetails;
        }
        return null;
    }
}
