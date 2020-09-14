package com.dhlk.flume.interceptor.interceptor;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FlumeSinkMysql extends AbstractSink implements Configurable {
    private Connection connect;
    private Statement stmt;
    private String columnName;
    private String url;
    private String user;
    private String password;
    private String tableName;


    // 在整个sink结束时执行一遍
    @Override
    public synchronized void stop() {
        // TODO Auto-generated method stub
        super.stop();
    }

    // 在整个sink开始时执行一遍
    @Override
    public synchronized void start() {
        // TODO Auto-generated method stub
        super.start();
        try {
            connect = DriverManager.getConnection(url, user, password);
            // 连接URL为 jdbc:mysql//服务器地址/数据库名 ，后面的2个参数分别是登陆用户名和密码
            stmt = connect.createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    // 不断循环调用
    @Override
    public Status process() throws EventDeliveryException {
        // TODO Auto-generated method stub

        Channel ch = getChannel();
        Transaction txn = ch.getTransaction();
        Event event = null;
        txn.begin();
        while (true) {
            event = ch.take();
            if (event != null) {
                break;
            }
        }


        try {
            String body = new String(event.getBody());

            JsonParser parser = new JsonParser();
            JsonObject obj = (JsonObject) parser.parse(body);
            ;
            String table = obj.get("table").getAsString();


            //String op_type = obj.get("op_type").getAsString();
            String sql = "";

            sql += "INSERT INTO " + table + " (";
            JsonObject after = (JsonObject) obj.get("after");

            Set<Map.Entry<String, JsonElement>> entry = after.entrySet();
            Iterator<Map.Entry<String, JsonElement>> it = entry.iterator();
            String vs = " values (";
            while (it.hasNext()) {
                Map.Entry<String, JsonElement> elem = it.next();
                String key = elem.getKey();
                String val = elem.getValue().toString();
                sql += key + ", ";
                vs += val + ", ";
            }
            sql = sql.replaceAll(",\\s*$", "");
            vs = vs.replaceAll(",\\s*$", "");
            sql += ") " + vs + ")";


            try {
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            txn.commit();
            return Status.READY;
        } catch (Throwable th) {
            txn.rollback();

            if (th instanceof Error) {
                throw (Error) th;
            } else {
                throw new EventDeliveryException(th);
            }
        } finally {
            txn.close();
        }
    }


    @Override
    public void configure(Context arg0) {

        url = arg0.getString("url");
        Preconditions.checkNotNull(url, "url must be set!!");
        user = arg0.getString("user");
        Preconditions.checkNotNull(user, "user must be set!!");
        password = arg0.getString("password");
        Preconditions.checkNotNull(password, "password must be set!!");

    }
}
