package dhlk.proxy.dhlk_proxy.service;

import com.dhlk.domain.Result;

public interface ProxyService {
    /**
     * 添加 代理设备，通过 设备唯一id
     * @param deviceId
     * @return
     */
    Result add(String deviceId);

    /**
     * 删除 代理设备，通过 设备唯一id
     * @param deviceId
     * @return
     */
    Result del(String deviceId);

    /**
     * 获取所有代理设备
     * @return
     */
    Result findList();

    /**
     * 根据 设备Id，获取该设备的代理连接信息
      * @param deviceId
     * @return
     */
    Result findProxyInfo(String deviceId);

    /**
     * 为该设备 请求临时端口
     * @param deviceId
     * @return
     */
    Result requestTempPort(String deviceId,String localUrl,int count);

}
