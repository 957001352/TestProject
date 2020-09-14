package com.dhlk.flume.interceptor.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.dhlk.flume.interceptor.entity.Result;
import com.dhlk.flume.interceptor.util.DES;
import com.dhlk.flume.interceptor.util.PropUtil;
import org.apache.commons.codec.Charsets;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.mortbay.util.ajax.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 自定义拦截器，实现Interceptor接口，并且实现其抽象方法
 */
public class CustomInterceptor implements Interceptor {

    private static Map<String,Object> map=new HashMap<String,Object>();
    //打印日志，便于测试方法的执行顺序
    private static final Logger logger = LoggerFactory.getLogger(CustomInterceptor.class);
    //自定义拦截器参数，用来接收自定义拦截器flume配置参数
    private static String param = "";
    /**
     * 拦截器构造方法，在自定义拦截器静态内部类的build方法中调用，用来创建自定义拦截器对象。
     */
    public CustomInterceptor() {
        logger.info("----------自定义拦截器构造方法执行 \n");
    }
    /**
     * 该方法用来初始化拦截器，在拦截器的构造方法执行之后执行，也就是创建完拦截器对象之后执行
     */
    @Override
    public void initialize() {
        logger.info("----------自定义拦截器的initialize方法执行 \n");
    }
    /**
     * 用来处理每一个event对象，该方法不会被系统自动调用，一般在 List<Event> intercept(List<Event> events) 方法内部调用。
     *
     * @param event
     * @return
     */
    @Override
    public Event intercept(Event event) {
        String eventBody = new String(event.getBody(), Charsets.UTF_8);
        if(authorization("fanwo")){
            logger.info("----------自定义拦截器认证通过 \n");
            event.setBody(eventBody.getBytes());
        }else{
            logger.info("----------自定义拦截器认证失败 \n");
        }
        return event;
    }
    /**
    * 认证授权中心
     * @param orgCode
    * @return
    */
    private  Boolean authorization(String orgCode){
        boolean flag=true;
        HttpClient httpClient=null;
        HttpGet httpGet=null;
        if(map.get(orgCode)==null) {
            String url= PropUtil.getValue("basicmodule_url");
            String key = DES.encryptDES(DES.md5(PropUtil.getValue("auth_key")));
            //去服务端认证，code==0说明认证通过，非0说明失败
            try {
                httpClient = new DefaultHttpClient();
                //2.声明get请求
                httpGet = new HttpGet(url+"/orgAuth/authCenter?key=" + key);
                HttpResponse response = httpClient.execute(httpGet);
                //4.判断状态码
                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    String returnMsg=EntityUtils.toString(entity, "utf-8");
                    Result result = JSONObject.parseObject(returnMsg, Result.class);
                    //code==0 说明认证通过
                    if (result.getCode() == 0) {
                        map.put(orgCode, key);
                        flag=true;
                    }else{//解密失败
                        flag=false;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                flag=false;
            }finally {
                //5.关闭资源
                if(httpGet!=null){
                    httpGet.releaseConnection();
                }
               if(httpClient!=null){
                   httpClient.getConnectionManager().shutdown();
               }
            }
        }
        return flag;
    };
    /**
     * 用来处理一批event对象集合，集合大小与flume启动配置有关，和transactionCapacity大小保持一致。一般直接调用 Event intercept(Event event) 处理每一个event数据。
     *
     * @param events
     * @return
     */
    @Override
    public List<Event> intercept(List<Event> events) {
        //System.out.printf("----------intercept(List<Event> events)方法执行  \n");
        /*
        	这里编写对于event对象集合的处理代码，一般都是遍历event的对象集合，对于每一个event对象，调用 Event intercept(Event event) 方法，然后根据返回值是否为null，
        	来将其添加到新的集合中。
         */
        List<Event> results = new ArrayList<>();
        Event event;
        for (Event e : events) {
            event = intercept(e);
            if (event != null) {
                results.add(event);
            }
        }
        return results;
    }
    /**
     * 该方法主要用来销毁拦截器对象值执行，一般是一些释放资源的处理
     */
    @Override
    public void close() {
        logger.info("----------自定义拦截器close方法执行 \n");
    }
    /**
     * 通过该静态内部类来创建自定义对象供flume使用，实现Interceptor.Builder接口，并实现其抽象方法
     */
    public static class Builder implements Interceptor.Builder {

        /**
         * 该方法主要用来返回创建的自定义类拦截器对象
         *
         * @return
         */
        @Override
        public Interceptor build() {
            logger.info("----------build方法执行 \n");
            return new CustomInterceptor();
        }
        /**
         * 用来接收flume配置自定义拦截器参数
         *
         * @param context 通过该对象可以获取flume配置自定义拦截器的参数
         */
        @Override
        public void configure(Context context) {
            logger.info("----------configure方法执行 \n");
            /*
            通过调用context对象的getString方法来获取flume配置自定义拦截器的参数，方法参数要和自定义拦截器配置中的参数保持一致+
             */
            param = context.getString("param");
        }
    }
}
