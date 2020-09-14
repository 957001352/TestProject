package dhlk.proxy.dhlk_proxy.util;
import java.util.List;
import	java.util.concurrent.ConcurrentHashMap;
import	java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

import com.dhlk.entity.basicmodule.BiProxyClientInfo;
import com.dhlk.entity.basicmodule.BiProxyServerInfo;
import com.dhlk.systemconst.Const;
import com.google.gson.Gson;
import dhlk.proxy.dhlk_proxy.config.ProxyConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;


public class ProxyUtil {
    public static volatile Map<String,BiProxyServerInfo> SERVER_MAP = new ConcurrentHashMap <>();
    public static volatile Map<String,Boolean> SERVER_STATE_MAP = new ConcurrentHashMap <>();
    public static Gson GSON = new Gson();
    public static ProxyConfig PROXY_CONFIG = null;
    public static volatile Map <String,Map<String, BiProxyClientInfo>> PROXY_MAP = new HashMap<> ();
    public static volatile StampedLock PROXY_LOCK = new StampedLock();

    public static volatile Map<String, List<Integer>> PROXY_PORT_MAP = new HashMap<> ();
}
