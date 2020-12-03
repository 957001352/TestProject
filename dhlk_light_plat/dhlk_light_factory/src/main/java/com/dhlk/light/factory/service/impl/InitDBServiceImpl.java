package com.dhlk.light.factory.service.impl;

import com.dhlk.domain.Result;
import com.dhlk.light.factory.dao.MqttSyncDao;
import com.dhlk.light.factory.service.InitDBService;
import com.dhlk.light.factory.util.LedConst;
import com.dhlk.systemconst.Const;
import com.dhlk.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * @Des InitDBService实现
 * @Author xkliu
 * @Date 2020/9/29
 **/
@Service
public class InitDBServiceImpl implements InitDBService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MqttSyncDao execute;

    @Override
    public Result execSQL() {
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("light_factory.sql");
            String sql = fileParseStr(in);
            execute.executeSql(sql);
            return ResultUtils.success();
        } catch (Exception e) {
            log.error("执行sql脚本失败!", e);
            return ResultUtils.failure();
        }
    }


    @Override
    public Result delRedisKeys() {
        Object mqttIsRight= redisTemplate.opsForValue().get(LedConst.REDIS_MQTTISRIGHT);
        Set keys = redisTemplate.keys("REDIS_" + "*");
        redisTemplate.delete(keys);
        Set<String> userTokenKeys = redisTemplate.keys(Const.TOKEN_CACHE_ITEM_PREFIX + "*");
        redisTemplate.delete(userTokenKeys);
        redisTemplate.opsForValue().set(LedConst.REDIS_MQTTISRIGHT,mqttIsRight);
        return ResultUtils.success();
    }

    @Override
    public Result cleanData() {
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("light_factory.sql");
            String sql = fileParseStr(in);
            execute.executeSql(sql);
            //获取云端mqtt连接状态
            Object mqttIsRight= redisTemplate.opsForValue().get(LedConst.REDIS_MQTTISRIGHT);
            Set keys = redisTemplate.keys("REDIS_" + "*");
            redisTemplate.delete(keys);
            Set<String> userTokenKeys = redisTemplate.keys(Const.TOKEN_CACHE_ITEM_PREFIX + "*");
            redisTemplate.delete(userTokenKeys);
            redisTemplate.opsForValue().set(LedConst.REDIS_MQTTISRIGHT,mqttIsRight);
            return ResultUtils.success();
        } catch (Exception e) {
            log.error("执行sql脚本失败!", e);
            return ResultUtils.failure();
        }
    }

    /**
     * 读取文件内容，将内容转换成字符串
     *
     * @return
     */
    public static String fileParseStr(InputStream in) {
        try {
            //FileInputStream in = new FileInputStream(file);
            //size 为字串的长度,这里一次性读完
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            String str = new String(buffer, "UTF-8");
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
