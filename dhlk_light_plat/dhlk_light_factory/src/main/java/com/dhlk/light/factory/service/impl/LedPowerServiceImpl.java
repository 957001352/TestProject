package com.dhlk.light.factory.service.impl;/**
 * @创建人 wangq
 * @创建时间 2020/6/16
 * @描述
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.Result;
import com.dhlk.entity.light.CloudPeopleFeelStatistics;
import com.dhlk.entity.light.LedOnline;
import com.dhlk.entity.light.LedPower;
import com.dhlk.entity.light.LocalPeopleFeelStatistics;
import com.dhlk.light.factory.dao.LedPowerDao;
import com.dhlk.light.factory.dao.TenantDao;
import com.dhlk.light.factory.mqtt.MqttCloudSender;
import com.dhlk.light.factory.service.LedPowerService;
import com.dhlk.light.factory.util.LedConst;
import com.dhlk.utils.DateUtils;
import com.dhlk.utils.HttpClientResult;
import com.dhlk.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: dhlk.multi.tenant
 * @description: 灯能耗数据处理
 * @author: wqiang
 * @create: 2020-06-16 09:57
 **/
@EnableScheduling
@Service
public class LedPowerServiceImpl implements LedPowerService {

    @Autowired
    private LedPowerDao ledPowerDao;

    @Autowired
    private MqttCloudSender mqttCloudSender;

    @Autowired
    private TenantDao tenantDao;

    @Value("${cloud.baseUrl}")
    private String baseUrl;

    @Value("${task.date}")
    private Integer dayNum;

    /**
     * 将订阅到的灯能耗数据存入数据库（MQtt每四秒上报一次信息）
     *
     * @param list
     * @return
     */
    @Override
    public Integer saveLedPower(List<LedPower> list) {
        return ledPowerDao.insert(list);
    }

    /**
     * 将订阅到的人感耗数据存入数据库(List)
     *
     * @param list
     * @return
     */
    @Override
    public Integer saveLocalPeopleFeel(List<LocalPeopleFeelStatistics> list) {
        return ledPowerDao.insertPeopleFeelStatistics(list);
    }

    /**
     * 将订阅到的人感耗数据存入数据库(对象)
     *
     * @param
     * @return
     */
    @Override
    public Integer saveLocalPeopleFeel(LocalPeopleFeelStatistics localPeopleFeelStatistics) {
        return ledPowerDao.savePeopleFeelStatistics(localPeopleFeelStatistics);
    }

    /**
     * 获取统计人感有人上报的数据(每天23点执行一次：)
     *
     * @return
     */
    @Scheduled(cron = "0 0 23 * * ?")
    @Override
    public void peopleFeelStatistics() throws Exception {

        //请求头
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=utf-8");
        headers.put("dataType", "json");

        List<CloudPeopleFeelStatistics> list = ledPowerDao.peopleFeelStatistics();
        if (list != null && list.size() > 0) {
            //调用获取验证码接口，判断云端服务运行是否正常
            if (HttpClientUtils.doGet(baseUrl + "/kaptcha/").getCode() == 200) {
                //list分组 100条为一组
                Map<String, List> map = groupList(list);
                for (List pfList : map.values()) {
                    String ledPowerListJson = JSON.toJSONString(pfList);
                    try {
                        //将（灯能耗数据和灯在线时长数据）通过http传给云服务
                        HttpClientResult httpClientResult = HttpClientUtils.doPostStringParams(baseUrl + "/led/savePeopleList/", headers, ledPowerListJson);
                        Result result = JSON.parseObject(httpClientResult.getContent(), Result.class);
                        if (result.getCode() == 0) {
                            //发送成功，更新人感状态
                            ledPowerDao.updatePeopleFellStatus(pfList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }


    /**
     * 获取每10分钟灯消耗的能耗 汇总一个小时数据,每小时执行一次  和  灯在线时间统计
     *
     * @return
     */
    @Scheduled(cron = "0 0 */1 * * ?")
    @Override
    public void ledEnergyStatistics() throws Exception {

        //汇总一个小时数据存入本地并且向云端发送已汇总数据
        List<LedPower> lpList = ledPowerDao.ledEnergyStatistics(DateUtils.getSubtractTime(), DateUtils.getCurrentTime());
        if (lpList != null && lpList.size() > 0) {
            //过滤掉能耗是0的数据 BigDecimal类型与0比较 返回-1是小于0；返回1是大于0；返回0是等于0；
            List<LedPower> ftList = lpList.stream().filter(k -> k.getEnergy().compareTo(BigDecimal.ZERO) == 1).collect(Collectors.toList());
            if (ftList != null && ftList.size() > 0) {
                Integer tenantId = tenantDao.findTenantId();
                if (tenantId == null || tenantId == 0) {
                    tenantId = -1;
                }
                //存入本地
                Integer insertCount = ledPowerDao.insertBySumEnergy(ftList, tenantId);
                if (insertCount > 0) {
                    //调用获取验证码接口，判断云端服务运行是否正常
                    if (HttpClientUtils.doGet(baseUrl + "/kaptcha/").getCode() == 200) {

                        //获取所有未发送成功的数据
                        List<LedPower> ledPowerList = ledPowerDao.findSendCloudErrorEnergyList();
                        if (ledPowerList != null && ledPowerList.size() > 0) {
                            //list分组 100条为一组，循环发送给云端
                            Map<String, List> map = groupList(ledPowerList);
                            for (List pfList : map.values()) {
                                String ledPowerListJson = JSON.toJSONString(pfList);
                                //请求头
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json;charset=utf-8");
                                headers.put("dataType", "json");
                                try {
                                    //将（灯能耗数据和灯在线时长数据）通过http传给云服务
                                    HttpClientResult httpClientResult = HttpClientUtils.doPostStringParams(baseUrl + "/led/savePower/", headers, ledPowerListJson);
                                    Result result = JSON.parseObject(httpClientResult.getContent(), Result.class);
                                    if (result.getCode() == 0) {
                                        //发送成功，更新能耗状态
                                        ledPowerDao.updateEnergyStatus(ledPowerList);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    /**
     * 获取每10分钟灯在线时长 汇总一个小时数据,每小时执行一次
     */
    @Scheduled(cron = "0 0 */1 * * ?")
    @Override
    public void ledOnLineStatistics() throws Exception {

        List<LedOnline> ledOnlineList = ledPowerDao.ledOnLinTime(DateUtils.getSubtractTime(), DateUtils.getCurrentTime());
        if (ledOnlineList != null && ledOnlineList.size() > 0) {
            Integer tenantId = tenantDao.findTenantId();
            if (tenantId == null || tenantId == 0) {
                tenantId = -1;
            }
            //存入本地
            Integer insertCount = ledPowerDao.insertOnlineList(ledOnlineList, tenantId);
            if (insertCount > 0) {
                //判断与云端是否连接正常
                if (HttpClientUtils.doGet(baseUrl + "/kaptcha/").getCode() == 200) {
                    List<LedOnline> OnLineList = ledPowerDao.findSendCloudErrorOnLineList();

                    if(OnLineList != null && OnLineList.size() > 0){
                        //list分组 100条为一组，循环发送给云端
                        Map<String, List> map = groupList(OnLineList);
                        for (List pfList : map.values()) {
                            String ledOnlineJson = JSON.toJSONString(pfList);
                            //请求头
                            Map<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json;charset=utf-8");
                            headers.put("dataType", "json");

                            try {
                                //将灯在线时长数据通过http传给云服务
                                HttpClientResult httpClientResult = HttpClientUtils.doPostStringParams(baseUrl + "/led/saveOnlineList/", headers, ledOnlineJson);
                                Result result = JSON.parseObject(httpClientResult.getContent(), Result.class);
                                if (result.getCode() == 0) {
                                    //发送成功，更新能耗状态
                                    ledPowerDao.updateOnLineStatus(OnLineList);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }


                }
            }
        }
    }

    /**
     * 每天凌晨删除一周前数据  dayNum(当前时间往前推几天)
     */
    @Scheduled(cron = "0 0 0 */1 * ?")
    @Override
    public void delete() {
        //获取指定天数之前的日期
        String date = DateUtils.getSubtractDate(dayNum);
        ledPowerDao.delete(date);
    }


    /**
     * 每个月1号删除上个月的数据
     */
   /* @Scheduled(cron = "0 0 0 1 * ?")
    @Override
    public void delete(){
        ledPowerDao.delete();
    }
*/


    /**
     * 根据list 每100条分一组装map中
     *
     * @param list
     * @return
     */
    public static Map<String, List> groupList(List list) {

        int listSize = list.size();
        int toIndex = 100;
        Map<String, List> map = new HashMap();     //用map存起来新的分组后数据
        int keyToken = 0;
        for (int i = 0; i < list.size(); i += 100) {
            if (i + 100 > listSize) {        //作用为toIndex最后没有100条数据则剩余几条newList中就装几条
                toIndex = listSize - i;
            }
            List newList = list.subList(i, i + toIndex);
            map.put("group" + keyToken, newList);
            keyToken++;
        }

        return map;
    }

}
