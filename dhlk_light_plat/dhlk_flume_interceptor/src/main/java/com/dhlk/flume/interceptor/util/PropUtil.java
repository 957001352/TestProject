package com.dhlk.flume.interceptor.util;

import org.apache.velocity.texen.util.PropertiesUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/30
 */
public class PropUtil {
    /**
    * 方式一
     * @param key
    * @return
    */
    public static String getValue(String key){
        Properties prop = new Properties();
        InputStream in = new PropertiesUtil().getClass().getResourceAsStream("/application.yml");
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(prop);
        return prop.getProperty(key);
    }
}