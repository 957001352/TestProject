package com.dhlk.basicmodule.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.basicmodule.service.dao.NetDevicesDao;
import com.dhlk.basicmodule.service.dao.NetDevicesSoftDao;
import com.dhlk.basicmodule.service.dao.ProductNetDao;
import com.dhlk.basicmodule.service.service.NetDevicesService;
import com.dhlk.basicmodule.service.util.RestTemplateUtil;
import com.dhlk.entity.basicmodule.NetDevices;
import com.dhlk.entity.tb.AdditionalInfo;
import com.dhlk.entity.tb.Id;
import com.dhlk.entity.tb.TbProductDevices;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import com.dhlk.exceptions.MyException;
import com.dhlk.util.AuthUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.dhlk.systemconst.Const;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.HttpClientResult;
import com.dhlk.utils.HttpClientUtils;
import com.dhlk.utils.ResultUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
/**
 * @Description 网络设备管理
 * @Author lpsong
 * @Date 2020/3/12
 */
@Service
public class NetDevicesSerivceImpl implements NetDevicesService {
    @Autowired
    private NetDevicesDao netDevicesDao;

    @Autowired
    private NetDevicesSoftDao netDevicesSoftDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AuthUserUtil authUserUtil;
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Value("${tb.baseUrl}")
    private String tbBaseUrl;
    @Autowired
    private ProductNetDao productNetDao;

    @Override
    @Transactional
    public Result save(NetDevices netDevices) throws Exception {
        if(CheckUtils.isNull(netDevices.getName())){
            return  ResultUtils.error("设备名称不能为空");
        }
        netDevices.setName(netDevices.getName().trim());
        //设置租户ID
        netDevices.setTenantId(authUserUtil.tenantId());
        if(netDevicesDao.isRepeatName(netDevices)>0){
            return  ResultUtils.error("设备名称重复");
        }
        //jsonDescription设备描述信息
        JSONObject jsonDescription = new JSONObject();
        jsonDescription.put("pdFactoryId", netDevices.getTenantId());
        /*
         * productDevices id为空 保存 id不为空 更新
         */
        //返回结果
        Result result =null;
        String api = tbBaseUrl+Const.GETTENANTDEVICE + "?deviceName=" + netDevices.getName();
        HttpClientResult mapResponseEntity = HttpClientUtils.doGet(api, restTemplateUtil.getHeaders(true), null);
        if (CheckUtils.isNull(netDevices.getId())) {
            if(mapResponseEntity.getCode()==200 && !CheckUtils.isNull(mapResponseEntity.getContent())){
                return  ResultUtils.error("设备名称重复");
            }
            result = saveNetDevices(netDevices, jsonDescription);
        } else {
            if(mapResponseEntity.getCode()==200 && !CheckUtils.isNull(mapResponseEntity.getContent())){
                Map map = HttpClientUtils.resultToMap(mapResponseEntity);
                Map<String, Object> mapId = (Map<String, Object>) map.get("id");
                String tbId = mapId.get("id").toString();
                if(!netDevices.getTbId().equals(tbId)){
                    return  ResultUtils.error("设备名称重复");
                }
            }
            result = updateNetDevices(netDevices, jsonDescription);
        }
        return result;
    }
    @Override
    @Transactional
    public Result delete(String  ids) throws Exception {
        if (CheckUtils.isNull(ids)) {
            return ResultUtils.error(ResultEnum.PARAM_ISNULL);
        }
        if (productNetDao.findBiIsBand(Arrays.asList(ids.split(",")))>0) {
            return ResultUtils.error("该设备已被绑定，无法删除");
        }
        Integer flag=0;
        //根据id查出对应的tb_id
        List<NetDevices> netDevicesList = netDevicesDao.findTbIdsListbyIds(Arrays.asList(ids.split(",")));
        //删除tb中的数据
        for (NetDevices nd : netDevicesList) {
            HttpClientResult httpClientResult = HttpClientUtils.doDeleteHeaders(tbBaseUrl + Const.TBDELETEDEVICEBYID + "/" + nd.getTbId(), restTemplateUtil.getHeaders(true));
             if (httpClientResult.getCode() == 200) {
                //删除dhlk db中的数据
                Integer res = netDevicesDao.deleteById(nd.getId());
                if (res > 0) {
                    //删除软件信息
                    netDevicesSoftDao.deleteByNetDevicesId(nd.getId());
                    flag=1;
                    continue;
                } else {
                    flag=-1;
                    //还原tb中的数据
                    break;
                }
            } else {
                return ResultUtils.failure();
            }
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result findList(String name) {
        return ResultUtils.success(netDevicesDao.findList(name,authUserUtil.tenantId()));
    }

    @Override
    public Result findPruductDevicesList(Integer netDevicesId) {
        return  ResultUtils.success(netDevicesDao.findPruductDevicesList(netDevicesId));
    }

    @Override
    public Result isEnable(Integer id, Integer status) throws MyException{
        if (!CheckUtils.isNumeric(id)) {
            return ResultUtils.failure();
        }
        if(!CheckUtils.checkStatus(status)){
            return ResultUtils.error("非法状态");
        }
        if (netDevicesDao.isEnable(id,status) > 0) {
            return  ResultUtils.success();
        }
        return ResultUtils.failure();
    }
    //保存网络设备
    private Result saveNetDevices(NetDevices netDevices, JSONObject jsonDescription) throws Exception {
        //网络设备 "gateway":true
        //设备additionalInfo属性
        AdditionalInfo additionalInfo = new AdditionalInfo(true, jsonDescription.toJSONString());
        //保存设备信息
        TbProductDevices tbProductDevices = new TbProductDevices(netDevices.getName(), netDevices.getTypeId().toString(), netDevices.getName(), additionalInfo);
        HttpClientResult responseEntity = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.TBSAVEDEVICE, restTemplateUtil.getHeaders(true), JSON.toJSONString(tbProductDevices));
        if (responseEntity.getCode() == 200) {//保存设备数据到tb成功
            //保存设备数据到dhlk数据库
            Map map = HttpClientUtils.resultToMap(responseEntity);
            Map<String, Object> mapId = (Map<String, Object>) map.get("id");
            String tbId = mapId.get("id").toString();
            netDevices.setTbId(tbId);

            try {
                //新增
                Integer flag = netDevicesDao.insert(netDevices);
                //插入软件版本信息
                if(netDevices.getSoftList()!=null&&netDevices.getSoftList().size()>0){
                    netDevicesSoftDao.insertBatch(netDevices.getSoftList(),netDevices.getId());
                }
                //成功
                return ResultUtils.success();
            } catch (RuntimeException e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                e.printStackTrace();
                //失败 删除保存到tb中的数据
                HttpClientUtils.doDeleteHeaders(tbBaseUrl+Const.TBDELETEDEVICEBYID + "/" + netDevices.getTbId(),restTemplateUtil.getHeaders(true));
                return ResultUtils.failure();
            }
        } else {
            //保存设备数据到tb失败  返回保存失败信息
            return ResultUtils.failure();
        }
    }

    //更新网络设备
    private Result updateNetDevices(NetDevices netDevices, JSONObject jsonDescription) throws Exception{
        //根据设备id查出tbId
        NetDevices tbND = netDevicesDao.findProductNetDevicesById(netDevices.getId());
        if (tbND!=null ) {
            //设备描述信息
            jsonDescription.put("pdId", netDevices.getId());
            //设备additionalInfo属性
            AdditionalInfo additionalInfo = new AdditionalInfo(true, jsonDescription.toJSONString());
            //构造tb设备更新格式
            Id id = new Id(tbND.getTbId(), "DEVICE");
            //在更新tb之前备份数据
            NetDevices pdBack = netDevicesDao.findProductNetDevicesById(netDevices.getId());
            String api = Const.SELECTTBDEVICEBYID + "/" + pdBack.getTbId();
            //ResponseEntity<Map> resTbBack = restTemplateUtil.getRestTemplate(api, Map.class, true);
            HttpClientResult resTbBack = HttpClientUtils.doGet(tbBaseUrl+api, restTemplateUtil.getHeaders(true), null);
            TbProductDevices tbProductDevices = new TbProductDevices(id, tbND.getName(), tbND.getTypeId().toString(),tbND.getName(), additionalInfo);
            HttpClientResult responseEntity = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.TBSAVEDEVICE, restTemplateUtil.getHeaders(true), JSON.toJSONString(tbProductDevices));
            if (responseEntity.getCode() == 200) {//保存设备数据到tb成功
                //保存设备数据到dhlk数据库
                Map map = HttpClientUtils.resultToMap(responseEntity);
                Map<String, Object> mapId = (Map<String, Object>) map.get("id");
                String tbId = mapId.get("id").toString();
                netDevices.setTbId(tbId);
                try {
                    //更新
                    Integer flag = netDevicesDao.update(netDevices);
                    //更新软件版本信息，先删除，在插入
                    if(netDevices.getSoftList()!=null&&netDevices.getSoftList().size()>0){
                        netDevicesSoftDao.deleteByNetDevicesId(netDevices.getId());
                        netDevicesSoftDao.insertBatch(netDevices.getSoftList(),netDevices.getId());
                    }
                    //成功
                    return ResultUtils.success();
                } catch (RuntimeException e) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    //更新网络设备失败 还原tb中的数据
                    handleFailure(resTbBack);
                    return ResultUtils.failure();
                }
            }else {
                //更新设备数据到tb失败  返回保存失败信息
                return ResultUtils.failure();
            }
        }else{
            return ResultUtils.failure();
        }
    }
    //更新网络设备失败 还原tb中的数据
    private  void handleFailure(HttpClientResult resTbBack) throws Exception {
        Map device = HttpClientUtils.resultToMap(resTbBack);
        String name=device.get("name").toString();
        String label=device.get("label").toString();
        String type=device.get("type").toString();
        String additionalInfoBackUpdate=device.get("additionalInfo").toString();
        Map<String,Object> mapIdBack=(Map<String,Object>)device.get("id");
        String idBack=mapIdBack.get("id").toString();
        Id idBackUpdate=new Id(idBack,"DEVICE");
        //设备additionalInfo属性
        AdditionalInfo additionalInfoBack = new AdditionalInfo(false, additionalInfoBackUpdate);
        TbProductDevices tbProductDevicesBack = new TbProductDevices(idBackUpdate,name, type,label, additionalInfoBack);
        while(true){
            //失败 还原tb中的数据
            HttpClientResult responseEntityBack = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.TBSAVEDEVICE, restTemplateUtil.getHeaders(true), JSON.toJSONString(tbProductDevicesBack));
            if(responseEntityBack.getCode()==200){
                break;
            }
        }
    }
}