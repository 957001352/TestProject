package com.dhlk.task;

import com.alibaba.fastjson.JSON;
import com.dhlk.domain.AlarmInfo;
import com.dhlk.domain.RedisAlarmRule;
import com.dhlk.service.RedisService;
import com.dhlk.systemconst.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class AlarmInfoToRedis {

    @Autowired
    private RedisService redisService;

    public void getAlarm(String key,String item){
        RedisAlarmRule cpuRule = null;
        List<String> obj = new ArrayList<>();
        if(redisService.hasKeyAndItem(Const.REDIS_ALARM_RULE_KEY,key)){
            cpuRule = JSON.parseObject(redisService.hget(Const.REDIS_ALARM_RULE_KEY, key).toString(), RedisAlarmRule.class);
        }
        if(redisService.hasKey(key)){
            List<Object> objects =redisService.lGet(key, 0, -1);
            for (Object ob:objects) {
                obj.add(ob.toString());
            }
        }
        if(cpuRule != null){
            switch (cpuRule.getWay()){ //判断是否求出平均值/最大值/最小值
                case 1:
                    Double average = avg(obj);
                    checkAlarm(cpuRule,average.toString(),item);
                    break;
                case 2:
                    String maxvalue=Collections.max(obj);
                    checkAlarm(cpuRule,maxvalue,item);
                    break;
                case 3:
                    String minvalue= Collections.min(obj);
                    checkAlarm(cpuRule,minvalue,item);
                    break;
            }

        }
    }


    //求平均值
    private Double avg(List<String> objects) {
        double count = objects.size();
        double sum = 0;
        for (String dou:objects) {
            sum += Double.parseDouble(dou);
        }
        return sum/count;
    }

    private  void mapToRedis(RedisAlarmRule cpuRule, String item, String symbol) {
        AlarmInfo alarmInfo = new AlarmInfo(item,item+"使用率"+symbol+cpuRule.getThreshold()+"%",System.currentTimeMillis());
        redisService.lSet(Const.REDIS_ALARM_INFO_KEY, JSON.toJSONString(alarmInfo));
    }

    /**
     * 根据条件进行比较 条件 1:大于 2:大于等于 3:等于 4:小于等于 5:小于
     * @param cpuRule
     */
    public  void checkAlarm(RedisAlarmRule cpuRule, String value, String item) {
        switch (cpuRule.getCondition()) {
            case 1:
                if(Double.parseDouble(value) > cpuRule.getThreshold()){
                    mapToRedis(cpuRule,item,"大于");
                }
                break;
            case 2:
                if(Double.parseDouble(value) >= cpuRule.getThreshold()){
                    mapToRedis(cpuRule,item,"大于等于");
                }
                break;
            case 3:
                if(Double.parseDouble(value) == cpuRule.getThreshold()){
                    mapToRedis(cpuRule,item,"等于");
                }
                break;
            case 4:
                if(Double.parseDouble(value) <= cpuRule.getThreshold()){
                    mapToRedis(cpuRule,item,"小于等于");
                }
                break;
            case 5:
                if(Double.parseDouble(value) < cpuRule.getThreshold()){
                    mapToRedis(cpuRule,item,"小于");
                }
                break;
        }
    }
}
