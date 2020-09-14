package com.dhlk.light.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.Area;
import com.dhlk.entity.light.Led;
import com.dhlk.entity.light.SyncDataResult;
import com.dhlk.enums.ResultEnum;
import com.dhlk.light.service.dao.AreaDao;
import com.dhlk.light.service.dao.LedDao;
import com.dhlk.light.service.mqtt.MqttSendServer;
import com.dhlk.light.service.service.AreaService;
import com.dhlk.light.service.service.DataSyncService;
import com.dhlk.light.service.util.HeaderUtil;
import com.dhlk.light.service.util.LedConst;
import com.dhlk.systemconst.Const;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/5
 * <p>
 * 施工区域service实现类
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private LedDao ledDao;

    @Autowired
    private HeaderUtil headerUtil;

    @Value("${attachment.path}")
    private String attachmentPath;

    @Autowired
    private MqttSendServer mqttSendServer;

    @Autowired
    private DataSyncService dataSyncService;


    @Override
    public Result save(Area area) {
        Integer flag = 0;
        //校验是否有重复的区域名称
        if (areaDao.findAreaRepeat(area.getArea(),headerUtil.tenantId()) > 0) {
            return ResultUtils.error("区域名已经存在");
        }
        area.setTenantId(headerUtil.tenantId());
        flag = areaDao.insert(area);
        if (flag > 0) {
            //给本地发送数据,新增
            String jsonStrArea = JSON.toJSONString(area);
            SyncDataResult syncData = new SyncDataResult(Const.AREA_TABLE_NAME,jsonStrArea, Const.SYNC_DATA_OPERATE_INSERT,headerUtil.tenantId());
            dataSyncService.syncDataToLocal(syncData);
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result delete(String ids) {
        if(CheckUtils.isNull(ids)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        List<String> strings  = Arrays.asList(ids.split(","));
        for (String str: strings) {
            //先查询区域信息的数据是否存在
            Area area = areaDao.selectTenantById(str);
            if(area == null){
                return ResultUtils.error("该区域信息数据不存在,请刷新页面");
            }
            List<Led> leds = ledDao.findListByAreaId(str);
            if(leds.size() > 0){
                return  ResultUtils.error("该区域下有绑定的灯");
            }
        }
        //如果删除施工区域成功后,再删除附件
        if (areaDao.delete(strings) > 0) {
            areaDao.deleteByDataId(strings);
            for (String str: strings) {
                //给本地发送数据,删除
                String jsonStrArea = JSON.toJSONString(str);
                SyncDataResult syncData = new SyncDataResult(Const.AREA_TABLE_NAME, jsonStrArea, Const.SYNC_DATA_OPERATE_DELETE, headerUtil.tenantId());
                dataSyncService.syncDataToLocal(syncData);
            }
            return ResultUtils.success();

        }
        return ResultUtils.failure();
    }

    @Override
    public Result findList() {
        List<Area> areas = areaDao.findList(headerUtil.tenantId(), attachmentPath);
        return ResultUtils.success(areas);
    }

    @Override
    public Result findListByTenantId(Integer tenantId) {
        if(CheckUtils.isNull(tenantId)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        List<Area> areas = areaDao.findListByTenantId(tenantId, attachmentPath);
        return ResultUtils.success(areas);
    }

    @Override
    public Result findAreaRepeat(String name) {
        if(CheckUtils.isNull(name)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        if (areaDao.findAreaRepeat(name,headerUtil.tenantId()) > 0) {
            return ResultUtils.error("区域名已经存在!");
        }
        return ResultUtils.success();
    }


}
