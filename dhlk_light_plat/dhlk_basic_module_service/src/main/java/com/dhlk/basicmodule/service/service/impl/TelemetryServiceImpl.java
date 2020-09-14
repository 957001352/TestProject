package com.dhlk.basicmodule.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.basicmodule.service.dao.ProductDevicesDao;
import com.dhlk.basicmodule.service.service.TelemetryService;
import com.dhlk.basicmodule.service.util.RestTemplateUtil;
import com.dhlk.entity.basicmodule.ProductDevices;
import com.dhlk.domain.Result;
import com.dhlk.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.dhlk.systemconst.Const;
import com.dhlk.utils.HttpClientResult;
import com.dhlk.utils.HttpClientUtils;
import com.dhlk.utils.ResultUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class TelemetryServiceImpl implements TelemetryService {
    @Autowired
    private ProductDevicesDao productDevicesDao;
    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Value("${tb.baseUrl}")
    private String tbBaseUrl;

    @Override
    public Result getTimeseries(Integer deviceId, String keys, Long startTs,Long endTs,Long interval,Integer limit,String agg) throws Exception {
        ProductDevices pd = productDevicesDao.findProductDevicesById(deviceId);
        ///api/plugins/telemetry/{entityType}/{entityId}/values/timeseries
        String api = tbBaseUrl+ Const.GETTIMESERIES + "/DEVICE/" + pd.getTbId()+"/values/timeseries";
        Map<String, String> params=new HashMap<String, String>();
        params.put("keys",keys);
        params.put("startTs",startTs.toString());
        params.put("endTs",endTs.toString());
        params.put("interval",interval.toString());
        params.put("limit",limit.toString());
        params.put("agg",agg);
        HttpClientResult httpClientResult = HttpClientUtils.doGet(api, restTemplateUtil.getHeaders(true), params);
        System.out.println("2222"+httpClientResult.getContent());
        if(httpClientResult.getCode()==200){
            return ResultUtils.success(JSONObject.parseObject(httpClientResult.getContent()));
        }
        return ResultUtils.success();
    }

    @Override
    public Result getLatestTimeseries(Integer deviceId, String keys) throws Exception {

        return null;
    }

    @Override
    public Result getAttributesByScope(String tbId) throws Exception {
        JSONObject json = new JSONObject();
        if(!CheckUtils.isNull(tbId)) {
            //ProductDevices pd = productDevicesDao.findProductDevicesById(deviceId);
            //   /api/plugins/telemetry/{entityType}/{entityId}/values/attributes/{scope}{?keys}
            String api = tbBaseUrl + Const.GETTIMESERIES + "/DEVICE/" + tbId + "/values/attributes/SERVER_SCOPE";
            Map<String, Object> params = new HashMap<String, Object>();
            HttpClientResult httpClientResult = HttpClientUtils.doGet(api, restTemplateUtil.getHeaders(true), null);
            if (httpClientResult.getCode() == 200) {
                List list = JSON.parseObject(httpClientResult.getContent(), List.class);
                for (Object ll : list) {
                    Map map = JSON.parseObject(ll.toString(), Map.class);
                    if (map.get("key").equals("active")) {
                        json.put("active", map.get("value"));
                    }
                }
                System.out.println("json-----------" + json.toJSONString());
            } else {
                json.put("active", false);
            }
        }else{
            json.put("active", false);
        }
        return ResultUtils.success(json);
    }
}
