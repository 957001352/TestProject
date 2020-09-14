package com.dhlk.websockt;

import com.alibaba.fastjson.JSON;
import com.dhlk.service.ProcessManageService;
import com.dhlk.service.RedisService;
import com.dhlk.service.SystemStatusService;
import com.dhlk.systemconst.Const;
import com.dhlk.task.BuildTask;
import com.dhlk.task.job.WsJob;
import com.dhlk.utils.SpringContextHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.quartz.JobDataMap;
import org.quartz.JobKey;

import java.util.HashMap;
import java.util.Map;


/**
 * MyWebSocketHandler
 * WebSocket处理器，处理websocket连接相关
 * @author zhengkai.blog.csdn.net
 * @date 2019-06-12
 */

public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private BuildTask buildTask;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端建立连接，通道开启！");
        MyChannelHandlerPool.channelMap.put(ctx.channel().id(),ctx.channel());
        //添加到channelGroup通道组
        MyChannelHandlerPool.channelGroup.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端断开连接，通道关闭！");

        MyChannelHandlerPool.channelMap.remove(ctx.channel().id());
        MyChannelHandlerPool.channelGroup.remove(ctx.channel());

        if(buildTask.getScheduler().checkExists(new JobKey("dhlk",ctx.channel().id().toString()))){
            buildTask.getScheduler().deleteJob(new JobKey("dhlk", ctx.channel().id().toString()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //首次连接是FullHttpRequest，处理参数 by zhengkai.blog.csdn.net
        if (msg != null && msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();

            Map paramMap=getUrlParams(uri);
            System.out.println("接收到的参数是："+ JSON.toJSONString(paramMap));
            //如果url包含参数，需要处理
            if(uri.contains("?")){
                String newUri=uri.substring(0,uri.indexOf("?"));
                System.out.println(newUri);
                request.setUri(newUri);
            }

        }else if(msg instanceof TextWebSocketFrame){
            //正常的TEXT消息类型
            TextWebSocketFrame frame=(TextWebSocketFrame)msg;
            if(Const.MACINE_HANDSHAKE.equals(frame.text())){
                if(buildTask == null){
                    buildTask = SpringContextHolder.getBean("buildTask");
                }
                if(buildTask.getScheduler().checkExists(new JobKey("dhlk",ctx.channel().id().toString()))){
                    buildTask.getScheduler().deleteJob(new JobKey("dhlk", ctx.channel().id().toString()));
                }
                SystemStatusService systemStatusService = SpringContextHolder.getBean("systemStatusServiceImpl");
                RedisService redisService = SpringContextHolder.getBean("redisServiceImpl");
                ProcessManageService processManageService = SpringContextHolder.getBean("processManageServiceImpl");
                JobDataMap jobDataMap = new JobDataMap();
                jobDataMap.put("ctx",ctx);
                jobDataMap.put("redisService",redisService);
                jobDataMap.put("processManageService",processManageService);
                jobDataMap.put("systemStatusService",systemStatusService);
                buildTask.collectCpuAlarmInfo(Const.WS_TS,WsJob.class,ctx.channel().id().toString(),ctx.channel().id().toString(),jobDataMap);

            }
        }
        super.channelRead(ctx, msg);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

    }


    private void sendAllMessage(String message){
        //收到信息后，群发给所有channel
        MyChannelHandlerPool.channelGroup.writeAndFlush( new TextWebSocketFrame(message));
    }

    private void sendMessage(ChannelHandlerContext ctx, String message){
        //收到信息后，单发给某个channel
        MyChannelHandlerPool.channelMap.get(ctx.channel().id()).writeAndFlush(new TextWebSocketFrame(message));

    }

    private static Map getUrlParams(String url){
        Map<String,String> map = new HashMap<>();
        url = url.replace("?",";");
        if (!url.contains(";")){
            return map;
        }
        if (url.split(";").length > 0){
            String[] arr = url.split(";")[1].split("&");
            for (String s : arr){
                String key = s.split("=")[0];
                String value = s.split("=")[1];
                map.put(key,value);
            }
            return  map;

        }else{
            return map;
        }
    }
}