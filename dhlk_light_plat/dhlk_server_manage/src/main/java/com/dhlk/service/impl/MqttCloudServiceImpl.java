package com.dhlk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.BiProxyServerInfo;
import com.dhlk.service.MqttCloudService;
import com.dhlk.service.RedisService;
import com.dhlk.systemconst.Const;
import com.dhlk.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/9
 */
@Service
@Slf4j
public class MqttCloudServiceImpl implements MqttCloudService {
    @Value("${jarname}")
    private String jarName;
    @Override
    public void subsribe(String topic, String jsonStr) {
      if(Const.TOPIC_CLOUDTOLOCAL_PROXY_DATA.equals(topic)) {//订阅到云端发的代理数据发命令
          if(jsonStr != null){
              JSONObject jsonObject = JSON.parseObject(jsonStr);
              String code = jsonObject.getString("code");
              log.info("云端传来本地的mac地址===========================>{}",code);
              Set<String> macs = getMac();
              boolean flag = false;
              for (String mac:macs) {
                  log.info("一体机的mac地址===========================>{}",mac);
                  if(code.equals(mac)){
                      flag = true;
                      break;
                  }
              }
              if(flag){
                  linuxCmd(jsonStr);
              }
          }
      }
    }

    private void linuxCmd(String jsonStr) {
        if (!CheckUtils.isNull(jsonStr)) {
            String path = getPath()+"/proxy/client_linux_amd64";
            List<String> exec = LinuxCommandUtils.exec(new String[]{"/bin/sh", "-c", "ps -ef | grep client_linux_amd64"});
            String pid = "";
            for (String s:exec){
                int i = s.indexOf(path);
                if (i != -1){
                    int i1 = 0;
                    while (i1 != -1){
                        s = s.replaceAll("  ", " ");
                        i1 = s.indexOf("  ");
                    }
                    String[] s1 = s.split(" ");
                    pid = s1[1];
                }
            }
            if(!"".equals(pid)){
                LinuxCommandUtils.exec(new String[]{"/bin/sh", "-c", "kill -9 "+pid});
            }
            BiProxyServerInfo biProxyServerInfo = JSON.parseObject(jsonStr, BiProxyServerInfo.class);
            //nohup /home/software/java/proxy/client_linux_amd64 -s 192.168.2.159 -p 4993 -k bi123 -ssl true 1>/home/software/java/logs/client_linux_amd64.log 2>&1 &
            String command = "nohup "+path +  " -s "+biProxyServerInfo.getHost()+" -p "+biProxyServerInfo.getProxyPort()+" -k " + biProxyServerInfo.getToken() + " -ssl true 1>/home/software/java/logs/client_linux_amd64.log 2>&1 &";
            LinuxCommandUtils.exec(new String[]{"/bin/sh", "-c",command});
            System.out.println("执行命令后的返回结果是: "+command);
        }
    }

    public String getPath(){
        String projectPath = MyPath.getProjectPath();
        String substring = projectPath.substring(projectPath.indexOf(":") + 1, projectPath.indexOf(jarName));
        return substring;
    }

    public Set<String> getMac(){
        List<String> exec = LinuxCommandUtils.exec(new String[]{"/bin/sh", "-c", "ifconfig -a"});
        List<Integer> foos = new ArrayList<>();
        Set<String> macs = new HashSet<>();

        for (String info:exec){
            log.info("---------------------->"+info);
            if(info.indexOf("ether") > 0){
                while (info.indexOf("  ") > 0){
                    info = info.replace("  ", " ");
                }
                String[] s = info.split(" ");
                for (int i = 0; i < s.length; i++) {
                    if("ether".equals(s[i])){
                        log.info(s[i]+"----------"+s.length+"------------>"+i);
                        foos.add(i);
                    }
                }
                for (Integer foo:foos) {
                    if (s[foo+1] != null) {
                        String s1 = s[foo + 1].replaceAll(":", "");
                        macs.add(s1);
                    }

                }
            }
        }
        return macs;
    }
}