package com.dhlk.hadoop.utils;

import com.alibaba.fastjson.JSONObject;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.FileInfo;
import com.dhlk.utils.DateUtils;
import com.dhlk.utils.FileUtils;
import com.dhlk.utils.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/4/15
 */
@Component
public class HadoopUtils {
    @Value("${hdfs.path}")
    private String path;
    @Value("${hdfs.username}")
    private String username;
    private static String hdfsPath;
    private static String hdfsName;
    private static final int bufferSize = 1024 * 1024 * 64;

    /**
     * 获取HDFS配置信息
     *
     * @return
     */
    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", hdfsPath);
        return configuration;
    }

    /**
     * 获取HDFS文件系统对象
     *
     * @return
     * @throws Exception
     */
    public static FileSystem getFileSystem() {
        // 客户端去操作hdfs时是有一个用户身份的，默认情况下hdfs客户端api会从jvm中获取一个参数作为自己的用户身份
        // DHADOOP_USER_NAME=hadoop
        // 也可以在构造客户端fs对象时，通过参数传递进去
        FileSystem fileSystem = null;
        try {
            fileSystem = FileSystem.get(new URI(hdfsPath), getConfiguration(), hdfsName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileSystem;
    }

    /**
     * 在HDFS创建文件夹
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean mkdir(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        if (existFile(path)) {
            return true;
        }
        FileSystem fs = getFileSystem();
        // 目标路径
        Path srcPath = new Path(path);
        boolean isOk = fs.mkdirs(srcPath);
        fs.close();
        return isOk;
    }

    /**
     * 判断HDFS文件是否存在
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean existFile(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        FileSystem fs = getFileSystem();
        Path srcPath = new Path(path);
        boolean isExists = fs.exists(srcPath);
        return isExists;
    }

    /**
     * 读取HDFS目录信息
     *
     * @param path
     * @return
     * @throws Exception
     */
    public Result readPathInfo(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!existFile(path)) {
            return ResultUtils.success(new PageInfo<>());
        }
        FileSystem fs = getFileSystem();
        // 目标路径
        Path newPath = new Path(path);
        FileStatus[] statusList = fs.listStatus(newPath);
        List<FileInfo> list = new ArrayList<>();
        if (null != statusList && statusList.length > 0) {
            for (FileStatus fileStatus : statusList) {
                if (!fileStatus.getPath().getName().contains(".tmp")) {
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFileName(fileStatus.getPath().getName());
                    fileInfo.setFilePath(revrSort(loadParent(fileStatus.getPath(),fileStatus.getPath().getName())));
                    fileInfo.setFileSize(String.valueOf(fileStatus.getLen()));
                    fileInfo.setIsFile(fileStatus.isFile());
                    list.add(fileInfo);
                }
            }
        }
        PageInfo<FileInfo> pageInfo = new PageInfo<>();
        pageInfo.setList(list);
        return ResultUtils.success(pageInfo);
    }
    /**
    * 递归获得父目录
     * @param path
     * @param str
    * @return
    */
    private  String loadParent(Path path, String str){
         if(path.getParent()!=null){
             if(str.equals("")){
                 str=path.getParent().getName();
             }else{
                 str+="/"+path.getParent().getName();
             }
             str=loadParent(path.getParent(),str);
         }
        return str;
    }
    public String revrSort(String path){
        List<String> list=Arrays.asList(path.split("/"));
        Collections.reverse(list);
       return  list.stream().collect(Collectors.joining("/"));
    }
    /**
     * HDFS创建文件
     *
     * @param path
     * @param file
     * @throws Exception
     */
    public static void createFile(String path, MultipartFile file) throws Exception {
        if (StringUtils.isEmpty(path) || null == file.getBytes()) {
            return;
        }
        String fileName = file.getOriginalFilename();
        FileSystem fs = getFileSystem();
        // 上传时默认当前目录，后面自动拼接文件的目录
        Path newPath = new Path(path + "/" + fileName);
        // 打开一个输出流
        FSDataOutputStream outputStream = fs.create(newPath);
        outputStream.write(file.getBytes());
        outputStream.close();
        fs.close();
    }

    /**
     * 读取HDFS文件内容
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static String readFile(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!existFile(path)) {
            return null;
        }
        FileSystem fs = getFileSystem();
        // 目标路径
        Path srcPath = new Path(path);
        FSDataInputStream inputStream = null;
        try {
            inputStream = fs.open(srcPath);
            // 防止中文乱码
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String lineTxt = "";
            StringBuffer sb = new StringBuffer();
            while ((lineTxt = reader.readLine()) != null) {
                sb.append(lineTxt);
            }
            return sb.toString();
        } finally {
            inputStream.close();
            fs.close();
        }
    }

    /**
     * 读取HDFS文件列表
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> listFile(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!existFile(path)) {
            return null;
        }

        FileSystem fs = getFileSystem();
        // 目标路径
        Path srcPath = new Path(path);
        // 递归找到所有文件
        RemoteIterator<LocatedFileStatus> filesList = fs.listFiles(srcPath, true);
        List<Map<String, String>> returnList = new ArrayList<>();
        while (filesList.hasNext()) {
            LocatedFileStatus next = filesList.next();
            String fileName = next.getPath().getName();
            Path filePath = next.getPath();
            Map<String, String> map = new HashMap<>();
            map.put("fileName", fileName);
            map.put("filePath", filePath.toString());
            returnList.add(map);
        }
        fs.close();
        return returnList;
    }

    /**
     * HDFS重命名文件
     *
     * @param oldName
     * @param newName
     * @return
     * @throws Exception
     */
    public static boolean renameFile(String oldName, String newName) throws Exception {
        if (StringUtils.isEmpty(oldName) || StringUtils.isEmpty(newName)) {
            return false;
        }
        FileSystem fs = getFileSystem();
        // 原文件目标路径
        Path oldPath = new Path(oldName);
        // 重命名目标路径
        Path newPath = new Path(newName);
        boolean isOk = fs.rename(oldPath, newPath);
        fs.close();
        return isOk;
    }

    /**
     * 删除HDFS文件
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean deleteFile(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        if (!existFile(path)) {
            return false;
        }
        FileSystem fs = getFileSystem();
        Path srcPath = new Path(path);
        boolean isOk = fs.deleteOnExit(srcPath);
        fs.close();
        return isOk;
    }

    /**
     * 上传HDFS文件
     *
     * @param path
     * @param uploadPath
     * @throws Exception
     */
    public static void uploadFile(String path, String uploadPath) throws Exception {
        if (StringUtils.isEmpty(path) || StringUtils.isEmpty(uploadPath)) {
            return;
        }
        FileSystem fs = getFileSystem();
        // 上传路径
        Path clientPath = new Path(path);
        // 目标路径
        Path serverPath = new Path(uploadPath);

        // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
        fs.copyFromLocalFile(false, clientPath, serverPath);
        fs.close();
    }

    /**
     * 下载HDFS文件
     *
     * @param path
     * @param downloadPath
     * @throws Exception
     */
    public static void downloadFile(String path, String downloadPath) throws Exception {
        if (StringUtils.isEmpty(path) || StringUtils.isEmpty(downloadPath)) {
            return;
        }
        FileSystem fs = getFileSystem();
        // 上传路径
        Path clientPath = new Path(path);
        // 目标路径
        Path serverPath = new Path(downloadPath);

        // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
        fs.copyToLocalFile(false, clientPath, serverPath);
        fs.close();
    }

    /**
     * @param filePath
     * @param response
     * @return domain.Result
     * @date 2020/4/17 15:25
     * @author jzhao
     * @description 下载HDFS文件（zip文件）
     */
    public static Result downHdfsFile(String filePath, HttpServletResponse response) {
        List<String> filePathList = Arrays.asList(filePath.split(","));
        //初期化ZIP流
        ZipOutputStream out = null;
        try {
            // 文件名转码
            String name="hdfs_"+ DateUtils.getNowTime();
            //String name = new String("FDFS文件".getBytes("UTF-8"), "iso-8859-1");
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=" + name + ".zip");
            out = new ZipOutputStream(response.getOutputStream());
            FileSystem fs = getFileSystem();
            for (String path : filePathList) {
                //zip压缩包下载
                getFdfsFile(path, out, fs, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //最后关闭ZIP流
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param filePath
     * @param out
     * @param fs
     * @param response
     * @return void
     * @date 2020/4/17 18:51
     * @author jzhao
     * @description 下载压缩包
     */
    public static void getFdfsFile(String filePath, ZipOutputStream out, FileSystem fs, HttpServletResponse response) {
        try {
            FileStatus[] fileStatulist = fs.listStatus(new Path(filePath));
            for (int i = 0; i < fileStatulist.length; i++) {
                String fileName = fileStatulist[i].getPath().getName();
                //判断是否为文件还是文件夹
                if (fileStatulist[i].isFile()) {
                    if (fileName.contains(".tmp")) {
                        continue;
                    }
                    Path path = fileStatulist[i].getPath();
                    FSDataInputStream inputStream = fs.open(path);
                    String name = filePath.replaceAll(hdfsPath,"") + "/" + fileName;
                    out.putNextEntry(new ZipEntry(name));
                    IOUtils.copyBytes(inputStream, out, 1024);
                    inputStream.close();
                } else {
                    //为文件夹，继续递归，找到最终的文件
                    getFdfsFile(fileStatulist[i].getPath().toString(), out, fs, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * HDFS文件复制
     *
     * @param sourcePath
     * @param targetPath
     * @throws Exception
     */
    public static void copyFile(String sourcePath, String targetPath) throws Exception {
        if (StringUtils.isEmpty(sourcePath) || StringUtils.isEmpty(targetPath)) {
            return;
        }
        FileSystem fs = getFileSystem();
        // 原始文件路径
        Path oldPath = new Path(sourcePath);
        // 目标路径
        Path newPath = new Path(targetPath);

        FSDataInputStream inputStream = null;
        FSDataOutputStream outputStream = null;
        try {
            inputStream = fs.open(oldPath);
            outputStream = fs.create(newPath);

            IOUtils.copyBytes(inputStream, outputStream, bufferSize, false);
        } finally {
            inputStream.close();
            outputStream.close();
            fs.close();
        }
    }

    /**
     * 打开HDFS上的文件并返回byte数组
     * @param path
     * @return
     * @throws Exception
     */
//    public static byte[] openFileToBytes(String path) throws Exception {
//        if (StringUtils.isEmpty(path)) {
//            return null;
//        }
//        if (!existFile(path)) {
//            return null;
//        }
//        FileSystem fs = getFileSystem();
//        // 目标路径
//        Path srcPath = new Path(path);
//        try {
//            FSDataInputStream inputStream = fs.open(srcPath);
//            return IOUtils.readFullyToByteArray(inputStream);
//        } finally {
//            fs.close();
//        }
//    }

    /**
     * 打开HDFS上的文件并返回java对象
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static <T extends Object> T openFileToObject(String path, Class<T> clazz) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!existFile(path)) {
            return null;
        }
        String jsonStr = readFile(path);
        return JSONObject.parseObject(jsonStr, clazz);
    }

    public static Result fileList(String path, Integer pageNum, Integer pageSize) {
        //根据地址获取文件列表
        List<FileInfo> infoList = listFileInfo(path);
        if (infoList == null || infoList.size() == 0) {
            PageInfo<FileInfo> pageInfo = new PageInfo<>();
            pageInfo.setList(new ArrayList<>());
            return ResultUtils.success(pageInfo);
        }
        //对文件排序，目的确保分页的时候不重复
        List<FileInfo> sortFile = infoList.stream().sorted((u1, u2) -> u2.getFileName().compareTo(u1.getFileName())).collect(Collectors.toList());
        //计算分页的
        int startNum = (pageNum - 1) * pageSize;
        int endNum = 0;
        if (startNum > infoList.size()) {
            startNum = 0;
        }
        if (pageSize > infoList.size() || (pageNum * pageSize) > infoList.size()) {
            endNum = infoList.size();
        } else {
            endNum = pageNum * pageSize;
        }
        List<FileInfo> subList = sortFile.subList(startNum, endNum);
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<FileInfo> pageInfo = new PageInfo<>(subList);
        pageInfo.setTotal(infoList.size());
        return ResultUtils.success(pageInfo);
    }


    /**
     * @param path
     * @return domain.Result
     * @date 2020/4/17 15:36
     * @author jzhao
     * @description 查询文件列表
     */
    public static List<FileInfo> listFileInfo(String path) {
        List<FileInfo> list = new ArrayList<>();
        try {
            if (StringUtils.isEmpty(path)) {
                path = hdfsPath + "/";
            }
            if (!existFile(path)) {
                return list;
            }
            FileSystem fs = getFileSystem();
            // 目标路径
            Path srcPath = new Path(path);
                // 递归找到所有文件
            RemoteIterator<LocatedFileStatus> filesList = fs.listFiles(srcPath, true);
            while (filesList.hasNext()) {
                LocatedFileStatus next = filesList.next();
                String fileName = next.getPath().getName();
                Path filePath = next.getPath();
                //拿到所有的CSV文件
                if (!fileName.contains(".tmp")) {
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFileName(fileName);
                    fileInfo.setFilePath(filePath.toString().substring(24));
                    fileInfo.setFileSize(FileUtils.formatFileSize(next.getLen()));
                    fileInfo.setIsFile(true);
                    list.add(fileInfo);
                }
            }
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取某个文件在HDFS的集群位置
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static BlockLocation[] getFileBlockLocations(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!existFile(path)) {
            return null;
        }
        FileSystem fs = getFileSystem();
        // 目标路径
        Path srcPath = new Path(path);
        FileStatus fileStatus = fs.getFileStatus(srcPath);
        return fs.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
    }

    @PostConstruct
    public void getPath() {
        hdfsPath = this.path;
    }

    @PostConstruct
    public void getName() {
        hdfsName = this.username;
    }

    public static String getHdfsPath() {
        return hdfsPath;
    }

    public String getUsername() {
        return username;
    }
}