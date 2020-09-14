package com.dhlk.basicmodule.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.dhlk.basicmodule.service.dao.EventDao;
import com.dhlk.basicmodule.service.dao.ProductDevicesDao;
import com.dhlk.basicmodule.service.service.EventService;
import com.dhlk.basicmodule.service.util.RestTemplateUtil;
import com.dhlk.entity.basicmodule.Event;
import com.dhlk.entity.basicmodule.ProductDevices;
import com.dhlk.domain.Result;
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
/**
 * 事件Service业务层处理
 *
 * @author jlv
 * @date 2020-04-26
 */
@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private ProductDevicesDao productDevicesDao;
    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Value("${tb.baseUrl}")
    private String tbBaseUrl;
    @Autowired
    private EventDao eventDao;
    public Result getAlarms(Integer deviceId ,String searchStatus, String status, int limit, Long startTime, Long endTime, boolean ascOrder, String offset, Boolean fetchOriginator) throws Exception {
        ProductDevices pd = productDevicesDao.findProductDevicesById(deviceId);
        //   /api/alarm/{entityType}/{entityId}{?searchStatus,status,limit,startTime,endTime,ascOrder,offset,fetchOriginator}
        String api = tbBaseUrl+ Const.GETALARMS + "/DEVICE/" + pd.getTbId();
        Map<String, String> params=new HashMap<String, String>();
        params.put("searchStatus",searchStatus);
        params.put("limit",""+limit);
        if(startTime!=null && endTime!=null){
            params.put("startTime",startTime.toString());
            params.put("endTime",endTime.toString());
        }
        params.put("ascOrder",""+ascOrder);
        params.put("offset",offset);
        params.put("fetchOriginator",fetchOriginator.toString());
        HttpClientResult httpClientResult = HttpClientUtils.doGet(api, restTemplateUtil.getHeaders(true), params);
        return ResultUtils.success(JSON.parse(httpClientResult.getContent()));
    }

    /**
     * 查询事件
     *
     * @param id 事件ID
     * @return 事件
     */
    @Override
    public Result selectEventById(Long id)
    {
        return ResultUtils.success(eventDao.selectEventById(id));
    }

    /**
     * 查询事件列表
     *
     *
     * @return 事件
     */
    @Override
    public Result selectEventList(String tbId, String searchStatus, Long startTime,Long endTime)
    {
        return ResultUtils.success(eventDao.selectEventList(null,searchStatus,startTime,endTime,tbId,null));
    }

    /**
     * 新增事件
     *
     * @param event 事件
     * @return 结果
     */
    @Override
    public Result insertEvent(Event event)
    {
        int res = eventDao.insertEvent(event);
        return res > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    /**
     * 修改事件
     *
     * @param event 事件
     * @return 结果
     */
    @Override
    public Result updateEvent(Event event)
    {
        int res=eventDao.updateEvent(event);
        return res > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    /**
     * 删除事件对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public Result deleteEventByIds(String ids)

    {
        int res=eventDao.deleteEventByIds(ids.split(","));
        return res > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    /**
     * 删除事件信息
     *
     * @param id 事件ID
     * @return 结果
     */
    public Result deleteEventById(Long id)
    {
        int res = eventDao.deleteEventById(id);
        return res > 0 ? ResultUtils.success() : ResultUtils.failure();
    }
}
