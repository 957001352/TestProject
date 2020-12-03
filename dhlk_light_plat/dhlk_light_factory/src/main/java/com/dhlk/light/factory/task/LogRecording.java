package com.dhlk.light.factory.task;

import com.dhlk.entity.light.MonitoringLog;
import com.dhlk.light.factory.dao.MonitoringLogDao;
import com.dhlk.light.factory.util.LedConst;
import com.dhlk.service.RedisService;
import com.dhlk.util.AuthUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@EnableScheduling
@Slf4j
public class LogRecording {

    @Autowired
    private RedisService redisService;
    @Autowired
    private MonitoringLogDao monitoringLogDao;
    @Scheduled(cron = "0 0/3 * * * ? ")
    public void logCreate(){
        Map<Object, Object> hmget = redisService.hmget(LedConst.REDIS_RECORDING_LOG);
        if(!hmget.isEmpty()){
            Set<Object> objects =hmget.keySet();
            List<MonitoringLog> upLines = new ArrayList<>();
            List<MonitoringLog> downLines = new ArrayList<>();
            for (Object obj:objects) {
                boolean flag = true;
                MonitoringLog monitoringLog = new MonitoringLog();
                monitoringLog.setSn(obj.toString());
                if(!redisService.hasKey(LedConst.REDIS_POWER+obj)){
                    //插入一条离线数据
                    monitoringLog.setStatus(-1);
                    downLines.add(monitoringLog);
                    flag = false;
                }

                if(flag){
                    //从数据库查sn，如果没有就插入一条在在线数据，如果有就不操作
                    int hget = Integer.parseInt(redisService.hget(LedConst.REDIS_RECORDING_LOG, obj.toString()).toString());
                    if(hget == 0){
                        monitoringLog.setStatus(0);
                        upLines.add(monitoringLog);
                        redisService.hset(LedConst.REDIS_RECORDING_LOG, obj.toString(),"1");
                    }
                }
            }
            if(upLines.size() > 0){
                monitoringLogDao.insertList(upLines);
                log.info("{}盏灯上线",upLines.size());
            }
            int count = 0;
            while (true){
                count += 1;
                log.info("第{}次进行校验,{}盏灯离线",count,downLines.size());
                if(downLines!=null&&downLines.size()>0){
                    downLines.removeIf(dLog -> redisService.hasKey(LedConst.REDIS_POWER + dLog.getSn()));
                }
                if(downLines.size() == 0 || count > 10){
                    for (MonitoringLog moni:downLines) {
                        redisService.hdel(LedConst.REDIS_RECORDING_LOG,moni.getSn());
                    }
                    break;
                }
                try {
                    Thread.sleep(12000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(downLines.size() > 0){
                monitoringLogDao.insertList(downLines);
            }
        }
    }
}
