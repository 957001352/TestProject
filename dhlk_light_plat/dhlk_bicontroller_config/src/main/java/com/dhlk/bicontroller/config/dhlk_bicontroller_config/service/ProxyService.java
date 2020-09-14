package com.dhlk.bicontroller.config.dhlk_bicontroller_config.service;



import com.dhlk.bicontroller.config.dhlk_bicontroller_config.util.ProxyUtil;
import com.dhlk.entity.basicmodule.*;
import com.google.gson.Gson;
import com.dhlk.enums.SystemEnums;
import com.dhlk.exceptions.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.dhlk.systemconst.Const;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


@Service
public class ProxyService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    Gson gson;
    /**
     * 获取反向代理信息
     * @return
     */
    public BiProxyConfig getProxyInfo() {
        try {
            String url = "http://"+ Const.BI_PROXY_SERVER_IP+":"+Const.BI_PROXY_SERVER_PROT+"/config/detail";

            String info = ProxyUtil.getInfo(restTemplate, url, null);

            BiProxyConfig biConfigEntity = gson.fromJson(info, BiProxyConfig.class);
            return biConfigEntity;
        }catch (Exception e){
            e.printStackTrace();
            login();
            return null;
        }



    }


    public String updateProxyInfo(BiProxyConfig biConfigEntity){
        try {
            String url = "http://" + Const.BI_PROXY_SERVER_IP + ":" + Const.BI_PROXY_SERVER_PROT + "/config/update";

            String body = gson.toJson(biConfigEntity.getData());

            String info = ProxyUtil.getInfo(restTemplate, url, body);
            return info;
        }catch (Exception e){
            login();
            e.printStackTrace();
            return null;
        }
    }

    public int getLeisurePort(BiProxyConfig biConfigEntity) throws MyException{
        List<Integer> list = new ArrayList<>();
        for (BiProxyClientInfo clientInfo:biConfigEntity.getData()){
            for (BiProxyPortInfo proxyPortInfo:clientInfo.getProxyMappings()){
                list.add(proxyPortInfo.getInetPort());
            }
        }
        list.sort(new intComparetor());

        int port = getMinPort(list);
        return port;

    }

    public Integer getMinPort(List<Integer> list) throws MyException{
        int initPort = Const.BI_MIN_PORT;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            int xx = (int) it.next();
            if (initPort>=Const.BI_MAX_PORT){
                throw new MyException(SystemEnums.PORT_DEPLETE_ERR);
            }

            if (initPort<xx){
                return initPort;
            }else if (initPort==xx){
                initPort++;
            }
        }
        return null;

    }

    public class intComparetor implements Comparator<Integer> {
        @Override
        public int compare(Integer h1, Integer h2) {
            if (h1 > h2) {
                return 1;
            } else if (h1.compareTo(h2)==0) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    public String getUrlInfo(String token){

            try {
                //获取代理信息
                BiProxyConfig biConfigEntity = getProxyInfo();
                //获取端口信息
                int port = getLeisurePort(biConfigEntity);
                /**
                 * 1、存在端口，直接返回
                 * 2、假如存在该客户端，直接创建端口
                 * 3、不存在，创建客户端，创建端口
                 */
                for (BiProxyClientInfo clientInfo:biConfigEntity.getData()){
                    if (token.equals(clientInfo.getClientKey())){
                       for(BiProxyPortInfo proxyPortInfo :clientInfo.getProxyMappings()){
                            if (proxyPortInfo.getName().equals(Const.BI_NODERED_SERVER_NAME)){
                                return Const.BI_PROXY_SERVER_IP+":"+proxyPortInfo.getInetPort();
                            }
                       }
                       //创建新客户端
                        BiProxyPortInfo portInfo = new BiProxyPortInfo();
                        portInfo.setName(Const.BI_NODERED_SERVER_NAME);
                        portInfo.setInetPort(port);
                        portInfo.setLan(Const.BI_LOCAL_NODERED_URL);
                        clientInfo.getProxyMappings().add(portInfo);
                        updateProxyInfo(biConfigEntity);
                        return Const.BI_PROXY_SERVER_IP+":"+port;
                    }
                }

                BiProxyPortInfo portInfo = new BiProxyPortInfo();
                portInfo.setName(Const.BI_NODERED_SERVER_NAME);
                portInfo.setInetPort(port);
                portInfo.setLan(Const.BI_LOCAL_NODERED_URL);

                List<BiProxyPortInfo> proxyPortInfoList = new ArrayList <>();
                proxyPortInfoList.add(portInfo);

                BiProxyClientInfo clientInfo = new BiProxyClientInfo();
                clientInfo.setClientKey(token);
                clientInfo.setName(Const.BI_NAME_PREFIX+token);
                clientInfo.setStatus(0);
                clientInfo.setProxyMappings(proxyPortInfoList);

                biConfigEntity.getData().add(clientInfo);

                updateProxyInfo(biConfigEntity);
                return Const.BI_PROXY_SERVER_IP+":"+port;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    public void login(){
        String url = "http://"+ Const.BI_PROXY_SERVER_IP+":"+Const.BI_PROXY_SERVER_PROT+"/login";
        BiProxyUser user = new BiProxyUser();
        user.setUsername(Const.BI_PROXY_SERVER_USERNAME);
        user.setPassword(Const.BI_PROXY_SERVER_PASSWORD);
        String body = gson.toJson(user);

        String info = ProxyUtil.getInfo(restTemplate, url, body);
        BiProxyLoginInfo loginEntity = gson.fromJson(info, BiProxyLoginInfo.class);
        if(loginEntity.getCode()==20000){
            System.out.println("登录成功");
            Const.BI_PROXY_SERVER_TOKEN = loginEntity.getData();
        }

    }
}
