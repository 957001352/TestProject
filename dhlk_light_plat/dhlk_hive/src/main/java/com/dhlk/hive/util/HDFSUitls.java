package com.dhlk.hive.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.HAUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;

/**
 * 版本        修改时间        编者      备注
 * V1.0        ------        jpdong    原始版本
 * 文件说明: HDFS文件操作类
 **/
public class HDFSUitls {
    /**
     * 上传本地文件到HDFS
     *
     * @param fileName 文件名
     * @param HDFSURL  HDFS URL
     * @param HDFSPath HDFS路径
     */
    public static void upload(String fileName, String HDFSURL, String HDFSPath) throws IOException {
        Configuration conf = new Configuration();
        //conf.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem);
        conf.set("fs.defaultFS", HDFSURL);
        FileSystem fs = FileSystem.get(conf);
        fs.copyFromLocalFile(new Path(fileName), new Path(HDFSPath));
        fs.close();
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名
     * @param HDFSURL  HDFS URL
     * @param HDFSPath HDFS路径
     * @throws IOException
     */
    public static void downLoad(String fileName, String HDFSURL, String HDFSPath) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", HDFSURL);
        FileSystem fs = FileSystem.newInstance(conf);
        fs.copyToLocalFile(new Path(HDFSPath), new Path(fileName));
        fs.close();
    }

    /**
     * 删除文件
     *
     * @param HDFSPath HDFS路径
     * @param HDFSURL  HDFS URL
     * @throws IOException
     */
    public static void removeFile(String HDFSPath, String HDFSURL) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", HDFSURL);
        FileSystem fs = FileSystem.newInstance(conf);
        fs.delete(new Path(HDFSPath), true);
        fs.close();
    }

    /**
     * 创建目录
     *
     * @param HDFSPath HDFS路径
     * @param HDFSURL  HDFS URL
     * @throws IOException
     */
    public static void mkdir(String HDFSPath, String HDFSURL) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.defaultFS", HDFSURL);
        FileSystem fs = FileSystem.newInstance(conf);
        fs.mkdirs(new Path(HDFSPath));
        fs.close();
    }

    /**
     * 获取hadoop 的namenode url
     *
     * @param HDFSURL
     * @return
     */
    public static String getActiveNameNode(String HDFSURL) {
        if (HDFSURL.isEmpty() || HDFSURL.trim().length() < 1) {
            HDFSURL = "hdfs://cluster1";
        }
        Configuration conf = new Configuration();
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.defaultFS", HDFSURL);
        try {
            URI uri = new URI(HDFSURL);
            FileSystem fileSystem = FileSystem.get(uri, conf, "root");
            InetSocketAddress active = HAUtil.getAddressOfActive(fileSystem);
            InetAddress address = active.getAddress();
            return address.getHostAddress() + ":50070";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }


}
