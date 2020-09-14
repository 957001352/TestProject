package com.dhlk.task.job;

import com.alibaba.fastjson.JSON;
import com.dhlk.domain.AlarmInfo;
import com.dhlk.domain.ProcessInfoEntity;
import com.dhlk.domain.ServerInfo;
import com.dhlk.domain.SystemStatus;
import com.dhlk.service.ProcessManageService;
import com.dhlk.service.RedisService;
import com.dhlk.service.SystemStatusService;
import com.dhlk.systemconst.Const;
import com.dhlk.websockt.MyChannelHandlerPool;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.logging.log4j.util.PropertySource;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class WsJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap mergedJobDataMap = jobExecutionContext.getMergedJobDataMap();
        RedisService redisService = (RedisService) mergedJobDataMap.get("redisService");
        ChannelHandlerContext ctx = (ChannelHandlerContext) mergedJobDataMap.get("ctx");
        ProcessManageService processManageService = (ProcessManageService) mergedJobDataMap.get("processManageService");
        SystemStatusService systemStatusService = (SystemStatusService) mergedJobDataMap.get("systemStatusService");

        SystemStatus runtime = null;
        try {
            runtime = systemStatusService.getRuntime();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if(redisService.hasKey(Const.REDIS_ALARM_INFO_KEY)){
            List<AlarmInfo> alarmInfos = new ArrayList<>();
            List<Object> objects = redisService.lGet(Const.REDIS_ALARM_INFO_KEY, -6, -1);
            for (Object ob:objects) {
                alarmInfos.add(JSON.parseObject(ob.toString(),AlarmInfo.class));
            }
            alarmInfos = alarmInfos.stream().sorted(Comparator.comparing(AlarmInfo::getTs).reversed()).collect(Collectors.toList());
            runtime.setAlarmInfos(alarmInfos);
        }
        runtime.setServerStartInfos(getService(0,processManageService));
        runtime.setServerEndInfos(getService(1,processManageService));
        if(MyChannelHandlerPool.channelMap.get(ctx.channel().id()) != null){
            MyChannelHandlerPool.channelMap.get(ctx.channel().id()).writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(runtime)));
        }
    }


    private List<ServerInfo> getService(int status,ProcessManageService processManageService){
        //获取正在运行/未运行的服务
        Object data = processManageService.getAllProcessInfo(null,null,"2").getData();
        List<ServerInfo> serverInfos = new ArrayList<>();
        if(data != null){
            List<ProcessInfoEntity> data1 = (List<ProcessInfoEntity>) data;
            if(status==0){
                for (ProcessInfoEntity sme:data1) {
                    serverInfos.add(new ServerInfo(sme.getProcessName(),sme.getStartTime()));
                }
            }else if(status==1){
//                for (ProcessInfoEntity sme:data1) {
//                    serverInfos.add(new ServerInfo(sme.getProcessName()));
//                }
            }

        }
        return serverInfos;
    }
}
