package com.dhlk.light.factory.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Tenant;
import com.dhlk.entity.basicmodule.User;
import com.dhlk.entity.light.*;
import com.dhlk.light.factory.dao.*;
import com.dhlk.light.factory.service.DataSyncService;
import com.dhlk.systemconst.Const;
import com.dhlk.utils.DateUtils;
import com.dhlk.utils.HttpClientResult;
import com.dhlk.utils.HttpClientUtils;
import com.dhlk.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * @author xkliu
 * @date 2020/6/15
 * <p>
 * 数据同步service实现类
 */
@Service
@Transactional
public class DataSyncServiceImpl implements DataSyncService {

    @Value("${cloud.baseUrl}")
    private String cloudBaseUrl;

    @Value("${attachment.path}")
    private String targetPath;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private LedDao ledDao;

    @Autowired
    private TenantDao tenantDao;

    @Autowired
    private SwitchDao switchDao;

    @Autowired
    private LedSwitchDao ledSwitchDao;

    @Autowired
    private TaskSchedulerDao taskSchedulerDao;

    @Autowired
    private TaskSchedulerDetailDao taskSchedulerDetailDao;

    @Autowired
    private OriginalPowerDao originalPowerDao;
    @Autowired
    private MqttSyncDao mqttSyncDao;


    @Value("${attachment.path}")
    private String attachPath;


    public Result authToken(String token) {
        Map tokenInfo = new HashMap();
        try {
            tokenInfo.put("token", token);
            HttpClientResult httpClientResult = HttpClientUtils.doPost(cloudBaseUrl + "/app/sycToken", tokenInfo);
            String content = httpClientResult.getContent();
            if (httpClientResult.getCode() == 200) {
                Result result = JSON.parseObject(content, Result.class);
                if (result.getCode() == 1005) {
                    return ResultUtils.error("网络异常");
                }
            }
            if (httpClientResult.getCode() != 200) {
                return ResultUtils.error("网络异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.error("网络异常");
        }
        return ResultUtils.success();
    }


    @Override
    public Result dataSync(String token, String code) {
        //同步数据前先同步token给云端
        Result result = authToken(token);
        if (result.getMsg().equals("网络异常")) {
            return ResultUtils.error(result.getMsg());
        }
        Integer flag = 0;
        List<Area> listArea = new ArrayList<>();
        List<User> listUser = new ArrayList<>();
        List<Led> listLed = new ArrayList<>();
        List<Tenant> listTenant = new ArrayList<>();
        List<Switch> listSwitch = new ArrayList<>();
        List<LedSwitch> listLedSwitch = new ArrayList<>();
        List<TaskScheduler> listTaskScheduler = new ArrayList<>();
        List<TaskSchedulerDetail> listTaskSchedulerDetail = new ArrayList<>();
        //List<TaskSchedulerLog> listTaskSchedulerLog = new ArrayList<>();
        List<OriginalPower> listOriginalPower = new ArrayList<>();
        try {
            Map<String, String> param = new HashMap<>();
            param.put("code", code);
            //调用云端接口获取云端表的数据
            HttpClientResult httpClientResult = HttpClientUtils.doGet(cloudBaseUrl + "/dataSync/sync", getHeadersn(token), param);
            Map map = HttpClientUtils.resultToMap(httpClientResult);
            Map<String, Object> mapData = (Map<String, Object>) map.get("data");
            if (CollectionUtils.isEmpty(mapData)) {
                return ResultUtils.error("同步失败,获取云端数据为空");
            }
            Iterator<Map.Entry<String, Object>> it = mapData.entrySet().iterator();
            //遍历map集合中的value转换成JSONArray
            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                if (entry.getKey().equals("tenant")) {
                    Tenant tenant = JSONObject.toJavaObject((JSONObject) entry.getValue(), Tenant.class);
                    listTenant.add(tenant);
                    continue;
                }
                JSONArray objects = JSON.parseArray(String.valueOf(entry.getValue()));
                if (entry.getKey().equals("area")) {
                    for (int i = 0; i < objects.size(); i++) {
                        JSONObject json = objects.getJSONObject(i);
                        if (json != null) {
                            Area area = JSONObject.toJavaObject(json, Area.class);
                            String fileUrl = cloudBaseUrl + area.getImagePath();
                            fileUrl = fileUrl.replace("\\", "/");
                            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                            try {
                                downloadFile(fileUrl, targetPath, fileName);
                            } catch (Exception e) {
                                e.printStackTrace();
                                return ResultUtils.error("区域Id: " + area.getId() + " 同步失败");
                            }
                            listArea.add(area);
                        }
                    }
                }
                if (entry.getKey().equals("user")) {
                    for (int i = 0; i < objects.size(); i++) {
                        JSONObject json = objects.getJSONObject(i);
                        if (json != null) {
                            User user = JSONObject.toJavaObject(json, User.class);
                            listUser.add(user);
                        }
                    }
                }
                if (entry.getKey().equals("led")) {
                    for (int i = 0; i < objects.size(); i++) {
                        JSONObject json = objects.getJSONObject(i);
                        if (json != null) {
                            Led led = JSONObject.toJavaObject(json, Led.class);
                            listLed.add(led);
                        }
                    }
                }
                if (entry.getKey().equals("switch")) {
                    for (int i = 0; i < objects.size(); i++) {
                        JSONObject json = objects.getJSONObject(i);
                        if (json != null) {
                            Switch sw = JSONObject.toJavaObject(json, Switch.class);
                            listSwitch.add(sw);
                        }
                    }
                }
                if (entry.getKey().equals("ledSwitch")) {
                    for (int i = 0; i < objects.size(); i++) {
                        JSONObject json = objects.getJSONObject(i);
                        if (json != null) {
                            LedSwitch ledSwitch = JSONObject.toJavaObject(json, LedSwitch.class);
                            listLedSwitch.add(ledSwitch);
                        }
                    }
                }
                if (entry.getKey().equals("taskScheduler")) {
                    for (int i = 0; i < objects.size(); i++) {
                        JSONObject json = objects.getJSONObject(i);
                        if (json != null) {
                            TaskScheduler taskScheduler = JSONObject.toJavaObject(json, TaskScheduler.class);
                            listTaskScheduler.add(taskScheduler);
                        }
                    }
                }
                if (entry.getKey().equals("detail")) {
                    for (int i = 0; i < objects.size(); i++) {
                        JSONObject json = objects.getJSONObject(i);
                        if (json != null) {
                            TaskSchedulerDetail taskSchedulerDetail = JSONObject.toJavaObject(json, TaskSchedulerDetail.class);
                            listTaskSchedulerDetail.add(taskSchedulerDetail);
                        }
                    }
                }
                if (entry.getKey().equals("originalPower")) {
                    for (int i = 0; i < objects.size(); i++) {
                        JSONObject json = objects.getJSONObject(i);
                        if (json != null) {
                            OriginalPower originalPower = JSONObject.toJavaObject(json, OriginalPower.class);
                            listOriginalPower.add(originalPower);
                        }
                    }
                }
//                if (entry.getKey().equals("log")) {
//                    for (int i = 0; i < objects.size(); i++) {
//                        JSONObject json = objects.getJSONObject(i);
//                        TaskSchedulerLog taskSchedulerLog = JSONObject.toJavaObject(json, TaskSchedulerLog.class);
//                        listTaskSchedulerLog.add(taskSchedulerLog);
//                    }
//                }
            }
            if (!CollectionUtils.isEmpty(listArea)) {
                areaDao.delete();
                flag = areaDao.insertBatch(listArea);
            }
            if (!CollectionUtils.isEmpty(listUser)) {
                userDao.delete();
                flag = userDao.insertBatch(listUser);
            }
            if (!CollectionUtils.isEmpty(listLed)) {
                ledDao.delete();
                flag = ledDao.insertBatch(listLed);
            }
            if (!CollectionUtils.isEmpty(listTenant)) {
                tenantDao.delete();
                flag = tenantDao.insertBatch(listTenant);
            }
            if (!CollectionUtils.isEmpty(listSwitch)) {
                switchDao.delete();
                flag = switchDao.insertBatch(listSwitch);
            }
            if (!CollectionUtils.isEmpty(listLedSwitch)) {
                ledSwitchDao.delete();
                flag = ledSwitchDao.insertBatch(listLedSwitch);
            }
            if (!CollectionUtils.isEmpty(listTaskScheduler)) {
                Integer tenantId = tenantDao.findTenantId();
                List<TaskScheduler> taskSchedulers = taskSchedulerDao.selectTaskSchedulerByTenantId(tenantId);
                if (!CollectionUtils.isEmpty(taskSchedulers)) {
                    listTaskScheduler.forEach(item -> {
                        taskSchedulers.forEach(k -> {
                            if (k.getId().equals(item.getId())) {
                                //本地定时任务数据是开启状态0,把listTaskScheduler中的状态的状态设置为0
                                if (k.getStatus() == 0) {
                                    item.setStatus(k.getStatus());
                                }
                            }
                        });
                    });
                }
                taskSchedulerDao.delete();
                flag = taskSchedulerDao.insertBatch(listTaskScheduler);
            }
            if (!CollectionUtils.isEmpty(listTaskSchedulerDetail)) {
                taskSchedulerDetailDao.delete();
                flag = taskSchedulerDetailDao.insertBatch(listTaskSchedulerDetail);
            }
            if (!CollectionUtils.isEmpty(listOriginalPower)) {
                originalPowerDao.del();
                flag = originalPowerDao.insertBatch(listOriginalPower);

            }
           /* if (!CollectionUtils.isEmpty(listTaskSchedulerLog)) {
                taskSchedulerLogDao.delete();
                flag = taskSchedulerLogDao.insertBatch(listTaskSchedulerLog);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtils.error("网络异常");

        }
        JSONObject json = new JSONObject();
        json.put("time", DateUtils.getToday("yyyy-MM-dd HH:mm:ss"));
        json.put("area", "成功获取 " + listArea.size() + " 条区域信息");
        json.put("led", "成功获取 " + listLed.size() + " 条灯信息");
        json.put("switch", "成功获取 " + listSwitch.size() + " 条组信息");
        json.put("ledSwitch", "成功获取 " + listLedSwitch.size() + " 条组-灯信息");
        json.put("taskScheduler", "成功获取 " + listTaskScheduler.size() + " 条定时任务");
        //json.put("taskSchedulerDetail","成功获取 " + listTaskSchedulerDetail.size() + " 条灯信息");
        //json.put("taskSchedulerLog","成功获取 " + listTaskSchedulerLog.size() + " 条灯信息");
        return flag > 0 ? ResultUtils.success(json) : ResultUtils.failure();
    }


    public Map<String, String> getHeadersn(String token) throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", token);
        return headers;
    }


    /**
     * 文件下载
     *
     * @param fileUrl 下载路径
     * @throws Exception
     */
    @Override
    public void downloadFile(String fileUrl, String targetPath, String fileName) throws Exception {
        DataInputStream in = null;
        DataOutputStream out = null;
        String pathBef = StringUtils.substringBeforeLast(fileUrl, "/");
        String ymd = StringUtils.substringAfterLast(pathBef, "/");
        String path = getPath(targetPath, ymd);
        File file = new File(path + "/" + fileName);
        //判断文件是否存在，不存在则创建文件
        if (!file.exists()) {
            file.createNewFile();
        }
        URL url = new URL(fileUrl);
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        urlCon.setConnectTimeout(6000);
        urlCon.setReadTimeout(6000);
        int code = urlCon.getResponseCode();
        if (code != HttpURLConnection.HTTP_OK) {
            throw new Exception("文件读取失败");
        }
        in = new DataInputStream(urlCon.getInputStream());
        out = new DataOutputStream(new FileOutputStream(path + "/" + fileName));
        byte[] buffer = new byte[2048];
        int count = 0;
        while ((count = in.read(buffer)) > 0) {
            out.write(buffer, 0, count);
        }
        if (out != null) {
            out.close();
        }
        if (in != null) {
            in.close();
        }

    }

    @Override
    public Integer syncData(SyncDataResult syncDataResult) {
        int count = -1;
        StringBuffer prex = new StringBuffer();
        StringBuffer suffix = new StringBuffer();
        String prexSql = "";
        String suffixSql = "";
        if (Const.SYNC_DATA_OPERATE_INSERT.equals(syncDataResult.getOperate())) {
            List<LinkedHashMap<String, String>> fields = mqttSyncDao.findFields(syncDataResult.getTableName());
            JSONObject jsonObject = JSON.parseObject(syncDataResult.getData() + "");
            //insert into table_name() values();
            prex.append("insert into " + syncDataResult.getTableName() + "(");
            suffix.append(") values(");
            fields.stream().forEach(field -> {
                String key = field.get("COLUMN_NAME");
                String value = jsonObject.getString(CaseUtils.toCamelCase(key, false, new char[]{'_'}));
                if (value != null) {
                    prex.append(key + ",");
                    if ("int".equals(field.get("DATA_TYPE"))) {
                        suffix.append(value + ",");
                    } else {
                        suffix.append("'" + value + "',");
                    }
                }
            });

            if (syncDataResult.getTableName().equals("dhlk_light_original_power")) {
                OriginalPower originalPower = JSONObject.toJavaObject(jsonObject, OriginalPower.class);
                OriginalPower power = originalPowerDao.selectOriginalPowerByTenantId(originalPower.getTenantId());
                if (power == null) {
                    originalPowerDao.insert(originalPower);
                } else {
                    originalPowerDao.setValues(originalPower.getLedCount(), originalPower.getLedOpentime(), originalPower.getLedPower(), originalPower.getTenantId(), originalPower.getPreBrightness());
                }
            } else {
                prexSql = prex.deleteCharAt(prex.length() - 1).toString();
                suffixSql = suffix.deleteCharAt(suffix.length() - 1) + ")";
            }
            if (syncDataResult.getTableName().equals("dhlk_light_area")) {
                try {
                    downAreaImg(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                    return count;
                }
            }
        } else if (Const.SYNC_DATA_OPERATE_UPDATE.equals(syncDataResult.getOperate())) {
            List<LinkedHashMap<String, String>> fields = mqttSyncDao.findFields(syncDataResult.getTableName());
            JSONObject jsonObject = JSON.parseObject(syncDataResult.getData() + "");
            //update table_name set 1=1 where id = 1;
            prex.append("update " + syncDataResult.getTableName() + " set ");
            suffix.append(" where ");
            fields.stream().forEach(field -> {
                String key = field.get("COLUMN_NAME");
                String value = jsonObject.getString(CaseUtils.toCamelCase(key, false, new char[]{'_'}));
                if (value != null) {
                    boolean flag = true;
                    if ("id".equals(key)) {
                        if ("int".equals(field.get("DATA_TYPE"))) {
                            suffix.append(key + "=" + value);
                        } else {
                            suffix.append(key + "=" + "'" + value + "'");
                        }
                        flag = false;
                    }
                    if (flag) {
                        if ("int".equals(field.get("DATA_TYPE"))) {
                            prex.append(key + "=" + value + ",");
                        } else {
                            prex.append(key + "=" + "'" + value + "',");
                        }
                    }
                }
            });
            prexSql = prex.deleteCharAt(prex.length() - 1).toString();
            if (syncDataResult.getTableName().equals("dhlk_light_original_power")) {
                OriginalPower originalPower = JSONObject.toJavaObject(jsonObject, OriginalPower.class);
                OriginalPower power = originalPowerDao.selectOriginalPowerByTenantId(originalPower.getTenantId());
                if (power == null) {
                    originalPowerDao.insert(originalPower);
                } else {
                    suffixSql = " where tenant_id = " + originalPower.getTenantId();
                }
            } else {
                suffixSql = suffix + "";
            }
        } else if (Const.SYNC_DATA_OPERATE_DELETE.equals(syncDataResult.getOperate())) {
            //delete from table_name where id = 1;
            String ids = syncDataResult.getData() + "";
            if (!"".equals(ids)) {
                if (syncDataResult.getTableName().equals("dhlk_light_led_switch")) {
                    prex.append("delete from " + syncDataResult.getTableName() + " where switch_id in (" + ids + ")");
                } else if (syncDataResult.getTableName().equals("dhlk_light_task_scheduler_detail")) {
                    prex.append("delete from " + syncDataResult.getTableName() + " where schedule_id in (" + ids + ")");
                } else {
                    prex.append("delete from " + syncDataResult.getTableName() + " where id in (" + ids + ")");
                }
                prexSql = prex + "";
            }
        }

        if (!"".equals(prexSql + suffixSql)) {
            count = mqttSyncDao.executeSql(prexSql + suffixSql);
        }
        return count;
    }

    private void downAreaImg(JSONObject jsonObject) throws Exception {
        Area area = JSONObject.toJavaObject(jsonObject, Area.class);
        String str = StringUtils.replaceAll(area.getImagePath(), attachPath, "/attach/");
        //测试图片下载使用的路径
        //String str = StringUtils.replaceAll("/home/software/attachment/2020-08-06/7341cb514a2f4e6e877ffebcd5929d93.png","/home/software/attachment/", "/attach/");
        area.setImagePath(str);
        String fileUrl = cloudBaseUrl + area.getImagePath();
        fileUrl = fileUrl.replace("\\", "/");
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        downloadFile(fileUrl, attachPath, fileName);
    }

    private String getPath(String targetPath, String ymd) {
        String path = targetPath + ymd;
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        return path;
    }


}
