package com.dhlk.bicontroller.config.dhlk_bicontroller_config.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import com.dhlk.systemconst.Const;


public class ProxyUtil {

    /**
     * 获取反向代理信息
     * @return
     */

    public static String getInfo(RestTemplate restTemplate,String url,String body){
        if (Const.BI_PROXY_SERVER_TOKEN!=null){
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add("Cookie","token="+Const.BI_PROXY_SERVER_TOKEN);

            HttpEntity<String> requestEntity = new HttpEntity<String>(body, requestHeaders);
            String s = restTemplate.postForObject(url, requestEntity, String.class);
            return s;
        }
        return null;
    }
}
