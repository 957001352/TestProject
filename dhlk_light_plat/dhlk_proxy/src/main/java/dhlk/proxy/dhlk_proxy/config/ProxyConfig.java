package dhlk.proxy.dhlk_proxy.config;

import com.dhlk.entity.basicmodule.BiProxyServerInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties("proxy")
@Component("proxyData") // 添加到bean容器时指定其名为 configData
public class ProxyConfig {

    List<BiProxyServerInfo> server;

    String localUrl;


    public List<BiProxyServerInfo> getServer() {
        return server;
    }

    public void setServer(List<BiProxyServerInfo> server) {
        this.server = server;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }
}
