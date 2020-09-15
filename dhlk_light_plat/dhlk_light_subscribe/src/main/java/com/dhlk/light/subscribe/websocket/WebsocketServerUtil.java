package com.dhlk.light.subscribe.websocket;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dhlk.entity.light.LedPower;
import com.dhlk.light.subscribe.service.RedisService;
import com.dhlk.light.subscribe.util.LedConst;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
* @Description:    websoked
* @Author:         gchen
* @CreateDate:     2020/6/11 15:56
*/
@Slf4j
@Component
@ServerEndpoint("/websocket/{token}")
public class WebsocketServerUtil {

    private ThreadPoolTaskScheduler executorService;

    //在线连接数
    private static int onlineCount = 0;

    //用于存放当前Websocket对象的Set集合
    private static Map<String, WebsocketServerUtil> websocketServerEndpoints = new HashMap<>();

    //与客户端的会话Session
    private Session session;

    //会话窗口的ID标识
    private String token = "";

    /**
     * 链接成功调用的方法
     *
     * @param session
     * @param token
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        log.info("onOpen >> 链接成功");
        this.session = session;
        //将当前websocket对象存入到Set集合中
        websocketServerEndpoints.put(this.session.getId(),this);


        //在线人数+1
//        addOnlineCount();
//        log.info("有新窗口开始监听：" + token + ", 当前在线人数为：" + getOnlineCount());

        this.token = token;

    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        log.info("onClose >> 链接关闭");

        //移除当前Websocket对象
        websocketServerEndpoints.remove(this.session.getId());

        if(this.executorService!=null){
            this.executorService.shutdown();
        }

        //在内线人数-1
//        subOnLineCount();
//        log.info("链接关闭，当前在线人数：" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        //log.info("接收到窗口：" + token + " 的信息：" + message);

        if(!CheckUtils.isNull(message)){
            if(executorService != null){
                executorService.shutdown();
            }
            executorService= this.threadPoolTaskScheduler();
            RedisService redisService = SpringContextHolder.getBean("redisServiceImpl");
            executorService.schedule(new WebsocketTask(redisService,message), new CronTrigger("*/2 * * * * ?"));
        }
    }

    @OnError
    public void onError(Session session, Throwable e) {
        e.printStackTrace();
    }

    /**
     * 推送消息
     *
     * @param message
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 自定义推送消息
     *
     * @param message
     * @param token
     */
    public void sendInfo(String token, String message, WebsocketServerUtil endpoint) {
        //log.info("推送消息到窗口：" + token + " ，推送内容：" + message);
        try {
            if (token.equals(endpoint.token)) {
                endpoint.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void subOnLineCount() {
        WebsocketServerUtil.onlineCount--;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private void addOnlineCount() {
        WebsocketServerUtil.onlineCount++;
    }

    public class WebsocketTask implements  Runnable {
        private RedisService redisService;
        private String sns;

        public WebsocketTask(RedisService redisService, String sns) {
            this.redisService = redisService;
            this.sns = sns;
        }

        @Override
        public void run() {
            if (websocketServerEndpoints.get(session.getId()) != null) {
                websocketServerEndpoints.get(session.getId()).sendInfo(token, getLedToRedis(sns), websocketServerEndpoints.get(session.getId()));
            }

        }

        /**
         * 查看redis里灯的状态
         *
         * @param sns
         */
        public String getLedToRedis(String sns) {
            if (StringUtils.isEmpty(sns)) {
                return null;
            }
            //开关灯后3s websocket不给页面推送数据
            if(redisService.hasKey(LedConst.REDIS_WEBSOCKET)){
                return null;
            }
            Map<String, LedPower> map = new HashMap<>();
            long s=System.currentTimeMillis();
            for (String sn : sns.split(",")) {
                LedPower ledPower = null;
                //获取能耗信息 判断redis里存在该sn的灯的能耗信息，如果有直接转换，没有就初始化
                Object power = redisService.get(LedConst.REDIS_POWER+sn);
                if (power == null) {
                    ledPower = new LedPower();
                    ledPower.setStatus("-1");
                } else {
                    ledPower = JSONObject.parseObject(power.toString(), LedPower.class);
                }
                ledPower.setLedSn(sn);
                //人感状态设置 人感开启，有人感触发时上报值，无人触发不上报值。
                //人感关闭时，人感开关状态从redis中获取，如果redis中为空，则主动请求，请求失败，则设置为-1，不显示人感标志
                Object peopleFell = redisService.get(LedConst.REDIS_PEOPLEFELL+sn);
                if (peopleFell != null) {
                    //获取人感触发状态 0未触发，1触发
                    ledPower.setPeopleStatus(Integer.parseInt(peopleFell.toString()));
                }else{
                    //人感触发状态获取为空，说明无人触发，则从redis中被动获取人感开关状态
                    peopleFell = redisService.get(LedConst.REDIS_PEOPLEONOFF+sn);
                    if(peopleFell!=null){
                        if(peopleFell.toString().equals("1")){//人感开启，设置为0无人，人感关闭，不设置，则为空，前端不显示
                            ledPower.setPeopleStatus(0);//光感开启
                        }else{
                            ledPower.setPeopleStatus(-1);//人感关闭
                        }
                    }else{
                        //如果没有从redis中获取到值，则设置为-1，防止重复请求
                        redisService.set(LedConst.REDIS_PEOPLEONOFF+sn,-1);
                        ledPower.setPeopleStatus(-1);
                    }
                }
                //光感状态设置 光感开启上报 实时光感值，光感关闭不报送
                Object linghtFell = redisService.get(LedConst.REDIS_LIGHTFELL+sn);
                if (linghtFell != null) {
                    ledPower.setLinghtStatus(1);
                }else{
                    ledPower.setLinghtStatus(0);
                }
                //获取故障码  判断redis里存在该sn的灯的故障信息
                Object fault = redisService.get(LedConst.REDIS_FAULT + sn);
                if (fault != null) {
                    ledPower.setFault(String.valueOf(fault));
                }
                map.put(ledPower.getLedSn(), ledPower);
            }
            long e=System.currentTimeMillis();
           // log.info("websocket 推送消息耗时---------------->>>"+(e-s)+"毫秒");
            return JSONObject.toJSONString(map, SerializerFeature.WriteMapNullValue);
        }
    }
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.initialize();
        executor.setPoolSize(10);
        executor.setThreadNamePrefix("task-");
        // 线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //调度器shutdown被调用时等待当前被调度的任务完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //等待时长
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }
}
