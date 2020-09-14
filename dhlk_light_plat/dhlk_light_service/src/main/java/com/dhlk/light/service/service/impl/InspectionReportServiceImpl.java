package com.dhlk.light.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.InspectionReport;
import com.dhlk.enums.ResultEnum;
import com.dhlk.light.service.dao.InspectionReportDao;
import com.dhlk.light.service.dao.LedDao;
import com.dhlk.light.service.dao.TaskSchedulerDao;
import com.dhlk.light.service.mqtt.MqttSendServer;
import com.dhlk.light.service.service.InspectionReportService;
import com.dhlk.light.service.task.InspectionTask;
import com.dhlk.light.service.util.HeaderUtil;
import com.dhlk.light.service.util.LightDeviceUtil;
import com.dhlk.service.RedisService;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author xkliu
 * @date 2020/6/12
 * <p>
 * 验收报告service实现类
 */
@Service
@Transactional
public class InspectionReportServiceImpl implements InspectionReportService {

    private Logger log = LoggerFactory.getLogger(InspectionReportServiceImpl.class);

    @Autowired
    private InspectionReportDao inspectionReportDao;


    @Autowired
    private RedisService redisService;

    @Autowired
    private LedDao ledDao;

    @Autowired
    private LightDeviceUtil lightDeviceUtil;

    @Autowired
    private TaskSchedulerDao taskSchedulerDao;

    @Autowired
    private HeaderUtil headerUtil;
    @Autowired
    private MqttSendServer mqttSendServer;

    @Override
    public Result executeInspection() {
        List<InspectionReport> inspectionReports = new ArrayList<>();
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            //创建一个线程,让里边跑执行任务
            InspectionTask task = new InspectionTask(inspectionReportDao, mqttSendServer, redisService, ledDao, lightDeviceUtil,
                    taskSchedulerDao, headerUtil.tenantId(), inspectionReports, key);
            Thread thread = new Thread(task);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtils.success(key);
    }


    @Override
    public Result findLampList(Integer pageNum, Integer pageSize, String areaId, String sn, String onOffBri, String result) {
        if (!CheckUtils.checkId(pageNum) || !CheckUtils.checkId(pageSize)) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        //定义flag的变量用于区分传多个onOffBri时，sql语句不在拼接 result 条件
        String flag = "true";
        Map<String, Object> map = getStringObjectMap(onOffBri, result);
        PageHelper.startPage(pageNum, pageSize);
        if (!CollectionUtils.isEmpty(map)) {
            flag = "false";
        }
        List<InspectionReport> inspectionReport = inspectionReportDao.findList(headerUtil.tenantId(), areaId, sn, map, result, flag);
        PageInfo<InspectionReport> inspectionReportPage = new PageInfo<>(inspectionReport);
        return ResultUtils.success(inspectionReportPage);
    }

    private Map<String, Object> getStringObjectMap(String onOffBri, String result) {
        Map<String, Object> map = new HashMap<>();
        if (!CheckUtils.isNull(onOffBri)  && !CheckUtils.isNull(result)) {
            String[] split = StringUtils.split(onOffBri, ",");
            for (String str : split) {
                if (str.equals("1")) {
                    map.put("t1.ons", result);
                }
                if (str.equals("2")) {
                    map.put("t1.dimming", result);
                }
                if (str.equals("3")) {
                    map.put("t1.off", result);
                }
            }
        }
        return map;
    }

    @Override
    public Result getInspection(String key) {
        if (CheckUtils.isNull(key)) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        Object value = redisService.get(key);
        return ResultUtils.success(value);
    }

    @Override
    public Result getQualifiedLed() {
        JSONObject json = new JSONObject();
        Integer onOff = inspectionReportDao.onOffQualified(headerUtil.tenantId());
        Integer dimming = inspectionReportDao.dimmingQualified(headerUtil.tenantId());
        Integer timed = inspectionReportDao.timedQualified(headerUtil.tenantId());
        json.put("onOff", onOff);
        json.put("dimming", dimming);
        json.put("timed", timed);
        return ResultUtils.success(json);
    }

    @Override
    public Result exportExcel(String areaId,Integer tenantId, String sn, String onOffBri, String result) {
        if(CheckUtils.isNull(tenantId)){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        String flag = "true";
        Map<String, Object> map = getStringObjectMap(onOffBri, result);
        if (!CollectionUtils.isEmpty(map)) {
            flag = "false";
        }
        List<LinkedHashMap<String, Object>> list = inspectionReportDao.exportExcel(tenantId, areaId, sn, map, onOffBri, result, flag);
        if (!CollectionUtils.isEmpty(list)) {
            getExportList(list);
        }
        return ResultUtils.success(list);
    }

    private void getExportList(List<LinkedHashMap<String, Object>> list) {
        for (LinkedHashMap<String, Object> item : list) {
            Integer off = Integer.parseInt(String.valueOf(item.get("off")));
            if (off == 1) {
                item.put("off", "合格");
            } else {
                item.put("off", "不合格");
            }
            Integer ons = Integer.parseInt(String.valueOf(item.get("ons")));
            if (ons == 1) {
                item.put("ons", "合格");
            } else {
                item.put("ons", "不合格");
            }
            Integer dimming = Integer.parseInt(String.valueOf(item.get("dimming")));
            if (dimming == 1) {
                item.put("dimming", "合格");
            } else {
                item.put("dimming", "不合格");
            }
        }
    }

}
