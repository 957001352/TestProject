package dhlk.proxy.dhlk_proxy;

import com.dhlk.entity.basicmodule.BiProxyClientInfo;
import dhlk.proxy.dhlk_proxy.util.ProxyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class DhlkProxyApplication {
	@Autowired
	private DiscoveryClient discoveryClient;
	/**
	 * 获取所有服务
	 */
	@RequestMapping("/services")
	public Object services() {
		return discoveryClient.getServices();
	}

	@RequestMapping("/map")
	public Object map() {
		long l = ProxyUtil.PROXY_LOCK.tryOptimisticRead();
		Map<String, Map<String, BiProxyClientInfo>> proxyMap = ProxyUtil.PROXY_MAP;
		if(!ProxyUtil.PROXY_LOCK.validate(l)){
			l = ProxyUtil.PROXY_LOCK.readLock();
			proxyMap = ProxyUtil.PROXY_MAP;
			ProxyUtil.PROXY_LOCK.unlockRead(l);
		}
		return proxyMap;
	}


	public static void main(String[] args) {
		SpringApplication.run(DhlkProxyApplication.class, args);

	}

}
