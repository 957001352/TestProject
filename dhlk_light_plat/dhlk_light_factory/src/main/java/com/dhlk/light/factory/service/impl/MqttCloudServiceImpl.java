package com.dhlk.light.factory.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.*;
import com.dhlk.light.factory.dao.*;
import com.dhlk.light.factory.enums.LedEnum;
import com.dhlk.light.factory.mqtt.MqttCloudSender;
import com.dhlk.light.factory.mqtt.MqttLocalSender;
import com.dhlk.light.factory.service.DataSyncService;
import com.dhlk.light.factory.service.MqttCloudService;
import com.dhlk.light.factory.service.TaskSchedulerService;
import com.dhlk.light.factory.util.LedConst;
import com.dhlk.light.factory.util.LightDeviceUtil;
import com.dhlk.service.RedisService;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.FileUtils;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/9
 */
@Service
public class MqttCloudServiceImpl implements MqttCloudService {
    private Logger log = LoggerFactory.getLogger(MqttCloudServiceImpl.class);
    @Autowired
    private MqttLocalSender mqttLocalSender;
    @Autowired
    private TaskSchedulerDao taskSchedulerDao;
    @Autowired
    private TenantDao tenantDao;
    @Autowired
    private TaskSchedulerDetailDao taskSchedulerDetailDao;
    @Autowired
    private LedDao ledDao;
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private SwitchDao switchDao;
    @Autowired
    private LedSwitchDao ledSwitchDao;
    @Autowired
    private OriginalPowerDao originalPowerDao;

    @Autowired
    DataSyncService dataSyncService;

    @Value("${cloud.baseUrl}")
    private String cloudBaseUrl;
    @Autowired
    private MqttCloudSender mqttCloudSender;

    @Value("${attachment.path}")
    private String attachPath;
    @Autowired
    private RedisService redisService;
    @Autowired
    private TaskSchedulerService taskSchedulerService;
    @Autowired
    private LightDeviceUtil lightDeviceUtil;
    @Override
    public void subsribe(String topic, String jsonStr) {
        //本地订阅到云端命令，发送给硬件
        if (LedConst.TOPIC_CLOUDTOLOCAL.equals(topic)) {//灯开关,亮度,wifi,人感，光感，开关通道设置
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            if (isEqualsTenant(jsonObject.getInteger("tenantId"))) {
                mqttLocalSender.sendMQTTMessage(LedConst.LOCALTOHARD_TOPIC_DHLKLIGHT, jsonStr);
            } else {
                log.info(jsonObject.getInteger("tenantId") + "租户ID与本地不相等，发送失败");
            }
        } else if (LedConst.LOCAL_TOPIC_SYS_VERSION.equals(topic)) {//订阅版本更新任务
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            if (isEqualsTenant(jsonObject.getInteger("tenantId"))) {
                downloadVersionFile(jsonObject);//下载灯版本文件到本地
            } else {
                log.info(jsonObject.getInteger("tenantId") + "租户ID与本地不相等，发送失败");
            }
        } else if (LedConst.LOCAL_TOPIC_SYNC_DATA.equals(topic)) { //订阅云端同步数据
            SyncDataResult syncDataResult = isTenantIdExit(jsonStr);
            if (syncDataResult != null) {
                syncCloundData(syncDataResult);
            }
        } else if (LedConst.LOCAL_TOPIC_TASK_DATASYNC.equals(topic)) {//订阅定时任务数据同步
            updateTaskScheduler(jsonStr);
        } else if (LedConst.LOCAL_TOPIC_TASK_DELETE.equals(topic)) {//订阅定时任务删除时数据同步
            delTaskSchedulerAndDetail(jsonStr);
        } else if (LedConst.LOCAL_TOPIC_LED_SAVE.equals(topic)) {//订阅灯数据保存
            saveLed(jsonStr);
        } else if (LedConst.LOCAL_TOPIC_LED_UPDATE.equals(topic)) {//订阅灯数据修改
            updateLed(jsonStr);
        } else if (LedConst.LOCAL_TOPIC_LED_DELETE.equals(topic)) {//订阅灯数据删除
            deleteLed(jsonStr);
        } else if (LedConst.LOCAL_TOPIC_AREA_SAVE.equals(topic)) {
            saveArea(jsonStr);
        } else if (LedConst.LOCAL_TOPIC_AREA_DELETE.equals(topic)) {
            delteArea(jsonStr);
        } else if (LedConst.LOCAL_TOPIC_SWITCH_SAVE.equals(topic)) {
            saveSwitch(jsonStr);
        } else if (LedConst.LOCAL_TOPIC_LEDSWITCH_SAVE.equals(topic)) {
            saveLedSwitch(jsonStr);
        } else if (LedConst.LOCAL_TOPIC_LEDSWITCH_DELETE.equals(topic)) {
            deleteLedSwitch(jsonStr);
        } else if (LedConst.LOCAL_TOPIC_SWITCH_DELETE.equals(topic)) {
            deleteSwitch(jsonStr);
        } else if (LedConst.LOCAL_TOPIC_ORIPOWER_SAVE.equals(topic)) {
            saveOriginalPower(jsonStr);
        } else if (LedConst.LOCAL_TOPIC_ORIPOWER_UPDATE.equals(topic)) {
            updateOriginalPower(jsonStr);
        }
    }

    private SyncDataResult isTenantIdExit(String jsonStr) {
        boolean flag = false;
        if (!CheckUtils.isNull(jsonStr)) {
            SyncDataResult syncDataResult = JSON.parseObject(jsonStr, SyncDataResult.class);
            Integer tenantId;
            if (!redisService.hasKey(LedConst.REDIS_TENANTID)) {
                tenantId = tenantDao.findTenantId();
                if (CheckUtils.isNull(tenantId)) {
                    redisService.set(LedConst.REDIS_TENANTID, -1, 60);
                } else {
                    redisService.set(LedConst.REDIS_TENANTID, tenantId);
                }
            } else {
                tenantId = (Integer) redisService.get(LedConst.REDIS_TENANTID);
            }
            if (tenantId != null && tenantId.equals(syncDataResult.getTenantId())) {
                flag = true;
            }
            if (flag || syncDataResult.getTenantId() == null) {
                return syncDataResult;
            }
        }
        return null;
    }


    private void syncCloundData(SyncDataResult syncDataResult) {
        Integer count = dataSyncService.syncData(syncDataResult);
        if(count > 0)
            mqttCloudSender.sendMQTTMessage(LedConst.CLOUD_TOPIC_SYNC_DATA, JSON.toJSONString(syncDataResult));
    }

    private void downloadVersionFile(JSONObject jsonStr) {
        if (!CheckUtils.isNull(jsonStr)) {
            LedVersionInfo ledVersionInfo = JSON.parseObject(jsonStr.getString("t"),LedVersionInfo.class);
            String str = StringUtils.replace(ledVersionInfo.getAddress(), attachPath, "/attach/");
            try {
                String fileUrl = cloudBaseUrl + str;
                fileUrl = fileUrl.replace("\\", "/");

                String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                String substring = fileUrl.substring(0, fileUrl.lastIndexOf("/"));
                String fileDir = substring.substring(substring.lastIndexOf("/") + 1);

                String dirName = attachPath +fileDir+"/"+fileName;
                if(!FileUtils.exists(dirName)){
                    dataSyncService.downloadFile(fileUrl, attachPath, fileName);
                }

                //然后给灯硬件发送升级命令
                lightDeviceUtil.sendMessageToMqttByThree(jsonStr.getString("sns"), LedEnum.SETVERSION,new LedVersion(dirName),jsonStr.getInteger("tenantId"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 增加系统配置数据
     *
     * @param jsonStr
     */
    public void saveOriginalPower(String jsonStr) {
        if (!CheckUtils.isNull(jsonStr)) {
            JSONObject json = JSONObject.parseObject(jsonStr);
            OriginalPower originalPower = JSONObject.toJavaObject(json, OriginalPower.class);
            if (originalPower != null) {
                if (tenantDao.findTenantIsExitsById(originalPower.getTenantId()) > 0) {
                    originalPowerDao.insert(originalPower);
                }
            }
        }
    }

    /**
     * 修改预设亮度值
     *
     * @param jsonStr
     */
    public void updateOriginalPower(String jsonStr) {
        if (!CheckUtils.isNull(jsonStr)) {
            JSONObject json = JSONObject.parseObject(jsonStr);
            String brightness = json.getString("brightness");
            String ledCount = json.getString("ledCount");
            String ledOpentime = json.getString("ledOpentime");
            String ledPower = json.getString("ledPower");
            String tenantId = json.getString("tenantId");
            if (!CheckUtils.isNull(ledCount) && !CheckUtils.isNull(ledOpentime) && !CheckUtils.isNull(ledPower)) {
                originalPowerDao.setValues(Integer.valueOf(ledCount), Float.valueOf(ledOpentime).floatValue(),
                        Float.valueOf(ledPower).floatValue(), Integer.valueOf(tenantId), null);
            } else {
                originalPowerDao.setValues(null, null, null, Integer.valueOf(tenantId), Integer.valueOf(brightness));
            }

        }
    }

    /**
     * 比对租户与本地是否相等
     *
     * @param id
     * @return
     */
    public boolean isEqualsTenant(Integer id) {
        if (redisService.get(LedConst.REDIS_TENANTID) == null) {
            Integer tenantId = tenantDao.findTenantId();
            if (CheckUtils.isNull(tenantId)) {
                redisService.set(LedConst.REDIS_TENANTID, -1, 60);
            } else {
                redisService.set(LedConst.REDIS_TENANTID, tenantId);
            }
            if (tenantId == id) {
                return true;
            }
        } else {
            Integer tenantId = (Integer) redisService.get(LedConst.REDIS_TENANTID);
            if (tenantId == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * 订阅到云端组的数据进行保存
     */
    @Transactional
    public void saveSwitch(String jsonStr) {
        if (!CheckUtils.isNull(jsonStr)) {
            JSONObject json = JSONObject.parseObject(jsonStr);
            Switch aSwitch = JSONObject.toJavaObject(json, Switch.class);
            if (aSwitch != null) {
                if (tenantDao.findTenantIsExitsById(aSwitch.getTenantId()) > 0) {
                    switchDao.insert(aSwitch);
                    ledSwitchDao.deleteBySwitchId(aSwitch.getId());
                }
            }
        }
    }

    /**
     * 订阅到云端组的中间表数据进行保存
     */
    @Transactional
    public void saveLedSwitch(String jsonStr) {
        if (!CheckUtils.isNull(jsonStr)) {
            JSONObject json = JSONObject.parseObject(jsonStr);
            LedSwitch ledSwitch = JSONObject.toJavaObject(json, LedSwitch.class);
            if (ledSwitch != null) {
                //中间表先获取switchId
                Integer switchId = ledSwitch.getSwitchId();
                if (!CheckUtils.isNull(switchId)) {
                    //根据switchId获取Switch
                    Switch aswitch = switchDao.getSwitchById(switchId);
                    if (aswitch != null) {
                        //租户id与本地租户一致，进行添加
                        if (tenantDao.findTenantIsExitsById(aswitch.getTenantId()) > 0) {
                            ledSwitchDao.saveLedSwitch(ledSwitch);
                        }
                    }
                }
            }
        }
    }

    /**
     * 订阅到云端灯-组的数据进行删除
     *
     * @param id
     */
    @Transactional
    public void deleteLedSwitch(String id) {
        if (!CheckUtils.isNull(id)) {
            ledSwitchDao.deleteBySwitchId(Integer.valueOf(id));
        }
    }

    /**
     * 订阅到云端组的数据进行删除
     *
     * @param id
     */
    @Transactional
    public void deleteSwitch(String id) {
        if (!CheckUtils.isNull(id)) {
            switchDao.deteleById(id);
        }
    }


    /**
     * 订阅到云端区域的数据进行删除
     *
     * @param id
     */
    @Transactional
    public void delteArea(String id) {
        if (!CheckUtils.isNull(id)) {
            areaDao.deteleById(id);
        }
    }


    /**
     * 订阅到云端区域的数据进行保存
     *
     * @param jsonStr
     */
    @Transactional
    public void saveArea(String jsonStr) {
        if (!CheckUtils.isNull(jsonStr)) {
            JSONObject json = JSONObject.parseObject(jsonStr);
            Area area = JSONObject.toJavaObject(json, Area.class);
            if (area != null) {
                //租户id与本地租户一致，进行添加
                if (tenantDao.findTenantIsExitsById(area.getTenantId()) > 0) {
                    String str = StringUtils.replaceAll(area.getImagePath(), attachPath, "/attach/");
                    area.setImagePath(str);
                    try {
                        Integer result = areaDao.save(area);
                        if (result > 0) {
                            String fileUrl = cloudBaseUrl + area.getImagePath();
                            fileUrl = fileUrl.replace("\\", "/");
                            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                            dataSyncService.downloadFile(fileUrl, attachPath, fileName);
                        }
                    } catch (Exception e) {
                        areaDao.deteleById(area.getId());
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    /**
     * 订阅到云端灯数据进行删除
     *
     * @param id
     */
    @Transactional
    public void deleteLed(String id) {
        try {
            if (!CheckUtils.isNull(id)) {
                ledDao.deteleById(Integer.parseInt(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅到云端灯数据进行修改
     *
     * @param jsonStr
     */
    @Transactional
    public void updateLed(String jsonStr) {
        if (!CheckUtils.isNull(jsonStr)) {
            JSONObject json = JSONObject.parseObject(jsonStr);
            Led led = JSONObject.toJavaObject(json, Led.class);
            if (led != null) {
                //租户id与本地租户一致，进行修改
                if (tenantDao.findTenantIsExitsById(led.getTenantId()) > 0) {
                    //查询该租户下是否存在相同sn被删除的灯，如果存在则原来的灯物理删除，然后进行修改，如果不存在则直接修改
                    Led entity=  ledDao.findLed(led);
                    if (entity == null) {
                        ledDao.update(led);
                    } else {
                        ledDao.deleteLed(entity.getId());
                        ledDao.update(led);
                    }
                }
            }
        }
    }

    /**
     * 订阅到云端灯数据进行保存
     *
     * @param jsonStr
     */
    @Transactional
    public void saveLed(String jsonStr) {
        if (!CheckUtils.isNull(jsonStr)) {
            JSONObject json = JSONObject.parseObject(jsonStr);
            Led led = JSONObject.toJavaObject(json, Led.class);
            if (led != null) {
                //租户id与本地租户一致，进行保存
                if (tenantDao.findTenantIsExitsById(led.getTenantId()) > 0) {
                    //查询该租户下是否存在相同sn被删除的灯，如果存在则修改，如果不存在则新增
                    Led entity=  ledDao.findLed(led);
                    if (entity == null) {
                        ledDao.save(led);
                    } else {
                        led.setId(entity.getId());
                        led.setStatus(0);
                        ledDao.update(led);
                    }
                }
            }
        }
    }

    /**
     * 订阅到云端的数据同步,本地进行修改或者新增
     *
     * @param jsonStr
     */
    @Transactional
    public void updateTaskScheduler(String jsonStr) {
        if (!CheckUtils.isNull(jsonStr)) {
            JSONObject json = JSONObject.parseObject(jsonStr);
            TaskScheduler taskScheduler = JSONObject.toJavaObject(json, TaskScheduler.class);
            if (taskScheduler != null) {
                List<TaskSchedulerDetail> taskSchedulerDetailList = taskScheduler.getTaskSchedulerDetailList();
                //新增或者修改任务明细
                updateTaskSchedulerDetail(taskScheduler.getId(), taskSchedulerDetailList);
                TaskScheduler task = taskSchedulerDao.selectTaskSchedulerById(taskScheduler.getId());
                if (task == null) {
                    //租户id与本地租户一致，进行更新
                    if (tenantDao.findTenantIsExitsById(taskScheduler.getTenantId()) > 0) {
                        taskSchedulerDao.insertTaskScheduler(taskScheduler);
                    }
                } else {
                    //租户id与本地租户一致，进行更新
                    if (tenantDao.findTenantIsExitsById(taskScheduler.getTenantId()) > 0) {
                        if(taskSchedulerDao.updateTaskScheduler(taskScheduler)>0){
                            //数据更新成功，重启定时任务
                            if(task.getStatus()==0){
                                //如果编辑前，任务状态是开启，则编辑后需要重启定时任务，先关闭，在开启
                                //定时任务停止成功之后，在开启
                                Result stopRes=taskSchedulerService.stopTaskScheduler(task.getId());
                                if(stopRes.getCode()==0){
                                    taskSchedulerService.startTaskScheduler(task.getId());
                                    System.out.println(task.getId()+"任务状态更新完成");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 本地进行修改或者新增任务详细
     *
     * @param taskSchedulerDetailList
     */
    @Transactional
    public void updateTaskSchedulerDetail(Integer id, List<TaskSchedulerDetail> taskSchedulerDetailList) {
        List<Integer> inIds= taskSchedulerDetailDao.findListByScheduleId(id).stream().map(TaskSchedulerDetail::getId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(taskSchedulerDetailList)) {
            taskSchedulerDetailList.forEach(item -> {
                TaskSchedulerDetail taskSchedulerDetail = taskSchedulerDetailDao.selectTaskSchedulerDetailById(Long.valueOf(item.getId()));
                if (taskSchedulerDetail == null) {
                    taskSchedulerDetailDao.insertTaskSchedulerDetail(item);
                } else {
                    if(inIds.contains(taskSchedulerDetail.getId())){
                        inIds.remove(taskSchedulerDetail.getId());
                    }
                    taskSchedulerDetailDao.updateTaskSchedulerDetail(item);
                }
            });
            if(inIds!=null&&inIds.size()>0){
                for (Integer inId : inIds) {
                    taskSchedulerDetailDao.deleteTaskSchedulerDetailById(Long.parseLong(inId.toString()));
                }
            }
        }
    }

    /***
     *订阅云端删除的定时任务，本地也进行删除
     * @param id
     */
    @Transactional
    public void delTaskSchedulerAndDetail(String id) {
        if (!CheckUtils.isNull(id)) {
            taskSchedulerDao.deleteTaskSchedulerById(Integer.parseInt(id));
            taskSchedulerDetailDao.deleteTaskSchedulerDetailBySchedulerId(Integer.parseInt(id));
        }
    }


}