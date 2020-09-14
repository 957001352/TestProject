package com.dhlk.basicmodule.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.basicmodule.service.dao.NetDevicesDao;
import com.dhlk.basicmodule.service.dao.ProductDevicesDao;
import com.dhlk.basicmodule.service.dao.ProductNetDao;
import com.dhlk.basicmodule.service.service.ProductNetService;
import com.dhlk.basicmodule.service.util.RestTemplateUtil;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.NetDevices;
import com.dhlk.entity.basicmodule.ProductDevices;
import com.dhlk.entity.basicmodule.ProductNet;
import com.dhlk.exceptions.MyException;
import com.dhlk.systemconst.Const;
import com.dhlk.util.AuthUserUtil;
import com.dhlk.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 生产和网络设备关系配置
 * @Author lpsong
 * @Date 2020/3/16
 */
@Service
public class ProductNetServiceImpl implements ProductNetService {
    @Autowired
    private ProductNetDao productNetDao;
    @Autowired
    private ProductDevicesDao productDevicesDao;
    @Autowired
    private NetDevicesDao netDevicesDao;
    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Value("${tb.baseUrl}")
    private String tbBaseUrl;

    @Autowired
    private AuthUserUtil authUserUtil;

    @Override
    @Transactional
    public Result save(String netId, String productIds, Integer type) throws Exception {
        List<String> productNameList = new ArrayList<String>();
        //新增
        Integer flag = 0;
        if (!CheckUtils.isNull(productIds)) {
            //先删除，在插入
            productNetDao.deleteByNetId(Convert.stringToInteger(netId));
            List<ProductNet> list = new ArrayList<ProductNet>();
            for (String productId : Arrays.asList(productIds.split(","))) {
                ProductNet productNet = new ProductNet();
                productNet.setNetId(Convert.stringToInteger(netId));
                productNet.setProductId(Convert.stringToInteger(productId));
                productNet.setType(type);
                productNet.setTenantId(authUserUtil.tenantId());
                list.add(productNet);
                ProductDevices productDevices = productDevicesDao.findProductDevicesById(Convert.stringToInteger(productId));
                if(productDevices!=null){
                    productNameList.add(productDevices.getCode());
                }

            }
            if (list != null && list.size() > 0) {
                flag = productNetDao.insertBatch(list);
            }
            //把dhlk网络设备关联的生产设备的名字保存到对应的tb网关设备的共享属性中
            if(productNameList!=null&&productNameList.size()>0){
                //根据netId查询网络设备
                NetDevices netDevices = netDevicesDao.findProductNetDevicesById(Convert.stringToInteger(netId));
                JSONObject jsonSharedArrribute = new JSONObject();
                jsonSharedArrribute.put("deviceList", JSON.toJSONString(productNameList));
                //把dhlk网络设备关联的生产设备的名字保存到对应的tb网关设备的共享属性中
                HttpClientResult responseEntityBack = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.SAVEDEVICESHAREDATTRIBUTE + "/DEVICE/" + netDevices.getTbId() + "/SHARED_SCOPE", restTemplateUtil.getHeaders(true), jsonSharedArrribute.toJSONString());
            }
        }else{
            productNetDao.deleteByNetId(Convert.stringToInteger(netId));
            return  ResultUtils.success();
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result delete(String id) throws MyException {
        if (!CheckUtils.isNull(id)) {
            if (productNetDao.findListByNetId(id) > 0) {
                if (productNetDao.deleteByNetId(Convert.stringToInteger(id)) > 0) {
                    return ResultUtils.success();
                }
            }
            return ResultUtils.error("无可删除数据");
        }
        return ResultUtils.failure();
    }

    @Override
    public Result findList(String name, Integer type) {
        return ResultUtils.success(productNetDao.findList(name, type,authUserUtil.tenantId()));
    }

    @Override
    public Result findBiList(Integer netId) {
        return ResultUtils.success(productNetDao.findBiList(netId,authUserUtil.tenantId()));
    }

    @Override
    public Result findComputerList(Integer netId) {
        return ResultUtils.success(productNetDao.findProductList(netId,authUserUtil.tenantId()));
    }


    @Override
    public Result findNotBiList() {
        return ResultUtils.success(productNetDao.findNotBiList(authUserUtil.tenantId()));
    }

    @Override
    public Result findNotComputerList() {
        return ResultUtils.success(productNetDao.findNotProductList(authUserUtil.tenantId()));
    }
}
