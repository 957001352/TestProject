package com.dhlk.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: dhlk_server_manage
 * @description:
 * @author: wqiang
 * @create: 2020-07-13 17:31
 **/
public class LinuxCommandUtils {

    public static List<String>  exec(String[] cmds){
        Process pro = null;
        List<String> list = new ArrayList<>();
        try {
            pro = Runtime.getRuntime().exec(cmds);
            pro.waitFor();
            InputStream in = pro.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while((line = read.readLine())!=null){
                list.add(line);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
