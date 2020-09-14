package dhlk.proxy.dhlk_proxy.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpUtil {


    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String post(String url, String token , String body) {
        OkHttpClient client = new OkHttpClient();
        String anString = "";
        //组装请求头
        Request.Builder builder = new Request.Builder()
                .url(url);
        if (token != null && token.length()>0){
            Map<String,String> map = new HashMap<>();
            map.put("Cookie","token="+token);
            builder.headers(Headers.of(map));
        }
        builder.post(RequestBody.create(JSON,body));
        Request request = builder.build();
        //该方法容易触发IOException异常
        try {
            //获取返回值
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                anString += response.body().string();
            }else {
                anString+="error!";
            }
            response.body().close();
        }catch (ConnectException ce){
               log.error(ce.getMessage());
        }catch(IOException ie) {
            ie.printStackTrace();
        }

        return anString;
    }
}
