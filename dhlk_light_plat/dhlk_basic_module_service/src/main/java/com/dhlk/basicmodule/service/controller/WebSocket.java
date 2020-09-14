package com.dhlk.basicmodule.service.controller;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;
//WebSocket的服务
@ServerEndpoint("/websocket")
@Component
public class WebSocket {
    //与某个客户端的连接会话，需要用这个session给客户端发数据
    private Session session;
    //存放每个客户端对应的WebSocket对象
    private static CopyOnWriteArraySet<WebSocket> webSockets=new CopyOnWriteArraySet<>();

    //浏览器找我们，建立连接
    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        webSockets.add(this);
    }

    //关闭连接
    @OnClose
    public void onClose(){
        webSockets.remove(this);
    }

    //接收客户端消息
    @OnMessage
    public void onMessage(String message){
        System.out.println("收到消息："+message);
    }

    //发消息给客户端
    public void sendMessage(String message){
        for (WebSocket webSocket:webSockets){
            try {
                webSocket.session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
