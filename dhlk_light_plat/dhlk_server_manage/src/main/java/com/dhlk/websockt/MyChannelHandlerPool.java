package com.dhlk.websockt;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.Map;

/**
 * MyChannelHandlerPool
 * 通道组池，管理所有websocket连接
 * @author zhengkai.blog.csdn.net
 * @date 2019-06-12
 */
public class MyChannelHandlerPool {

    public MyChannelHandlerPool(){}

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static Map<ChannelId, Channel> channelMap = new HashMap<>();

}