package dhlk.proxy.dhlk_proxy.thread;
import java.util.*;

import com.dhlk.entity.basicmodule.BiProxyClientInfo;
import com.dhlk.entity.basicmodule.BiProxyConfig;
import com.dhlk.entity.basicmodule.BiProxyServerInfo;
import com.google.gson.JsonSyntaxException;
import dhlk.proxy.dhlk_proxy.util.ClientUtil;
import dhlk.proxy.dhlk_proxy.util.ProxyUtil;
import lombok.extern.slf4j.Slf4j;



@Slf4j
public class LoginThread implements Runnable{

    private BiProxyServerInfo info;


    public LoginThread(BiProxyServerInfo biProxyServerInfo) {
        this.info = biProxyServerInfo;
    }

    @Override
    public void run() {
        try {
            if (!ProxyUtil.SERVER_MAP.containsKey(info.getName())){
                ProxyUtil.SERVER_STATE_MAP.put(info.getName(),false);
                login(info);
                return;
            }
            BiProxyServerInfo biProxyServerInfo = ProxyUtil.SERVER_MAP.get(info.getName());
            if (biProxyServerInfo.getToken()==null || biProxyServerInfo.getToken().equals("")){
                ProxyUtil.SERVER_STATE_MAP.put(info.getName(),false);
                login(info);
                return;
            }


            BiProxyConfig detailInfo = ClientUtil.getDetailInfo(biProxyServerInfo);
            if(detailInfo.getCode()!=20000){
                ProxyUtil.SERVER_STATE_MAP.put(info.getName(),false);
                login(info);
            }else {
                log.info(info.getName()+" status true");
                ProxyUtil.SERVER_STATE_MAP.put(info.getName(),true);
                //保存信息
                Map<String, BiProxyClientInfo> stringBiProxyClientInfoMap = ProxyUtil.PROXY_MAP.get(info.getName());
                if (stringBiProxyClientInfoMap.size()!=detailInfo.getData().size()){
                    //全量更新
                    long l = ProxyUtil.PROXY_LOCK.writeLock();
                    stringBiProxyClientInfoMap.clear();
                    detailInfo.getData().stream().forEach(biProxyClientInfo -> {
                        stringBiProxyClientInfoMap.put(biProxyClientInfo.getClientKey(),biProxyClientInfo);
                    });
                    ProxyUtil.PROXY_LOCK.unlockWrite(l);
                    return;
                }
                detailInfo.getData().stream().forEach(biProxyClientInfo -> {
                    long l = ProxyUtil.PROXY_LOCK.tryOptimisticRead();
                    if (stringBiProxyClientInfoMap.containsKey(biProxyClientInfo.getClientKey())){
                        BiProxyClientInfo biProxyClientInfo1 = stringBiProxyClientInfoMap.get(biProxyClientInfo.getClientKey());
                        if((!biProxyClientInfo1.equals(biProxyClientInfo)) || (biProxyClientInfo1.getProxyMappings().size()!=biProxyClientInfo.getProxyMappings().size())){
                            if(!ProxyUtil.PROXY_LOCK.validate(l)){
                                l = ProxyUtil.PROXY_LOCK.readLock();
                                if((!biProxyClientInfo1.equals(biProxyClientInfo)) || (biProxyClientInfo1.getProxyMappings().size()!=biProxyClientInfo.getProxyMappings().size())){
                                    l = ProxyUtil.PROXY_LOCK.tryConvertToWriteLock(l);
                                    stringBiProxyClientInfoMap.put(biProxyClientInfo.getClientKey(),biProxyClientInfo);
                                    ProxyUtil.PROXY_LOCK.unlockWrite(l);
                                }
                            }else {
                                l = ProxyUtil.PROXY_LOCK.tryConvertToWriteLock(l);
                                stringBiProxyClientInfoMap.put(biProxyClientInfo.getClientKey(),biProxyClientInfo);
                                ProxyUtil.PROXY_LOCK.unlockWrite(l);
                            }
                        }
                    }else {
                        l = ProxyUtil.PROXY_LOCK.tryConvertToWriteLock(l);
                        stringBiProxyClientInfoMap.put(biProxyClientInfo.getClientKey(),biProxyClientInfo);
                        ProxyUtil.PROXY_LOCK.unlockWrite(l);
                    }
                });

            }
        }catch (JsonSyntaxException e) {
            ProxyUtil.SERVER_STATE_MAP.put(info.getName(),false);
            login(info);
        }catch (NullPointerException npe) {
            log.error(npe.getMessage());
            ProxyUtil.SERVER_STATE_MAP.put(info.getName(),false);
            login(info);
        }catch (Exception e){
            log.error(e.getMessage());
            ProxyUtil.SERVER_STATE_MAP.put(info.getName(),false);
            login(info);
        }

    }

    private void login(BiProxyServerInfo info){
        ProxyUtil.SERVER_STATE_MAP.put(info.getName(),false);
        log.info(info.getName()+" login start");
        boolean login = ClientUtil.login(info);
        if (login){
            log.info(info.getName()+" login succ");
            ClientUtil.putPort(ProxyUtil.SERVER_MAP.get(info.getName()),"",0,false);
        }else {
            log.info(info.getName()+" login error");
        }
    }
}
