package com.dhlk.utils;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.web.multipart.MultipartFile;
import com.dhlk.systemconst.Const;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Content:文件目录类操作类
 * Author:jpdong
 * Date:2020/3/3
 * 注意关注Const.java中的 UNLIMITFILENAME
 */
public class FileUtils {

    /**
     * FileUtils实例化
     * 使用方法：FileUtils.getInstance().isFileName(fileName)...
     *
     * @return new FileUtils
     */
    public static FileUtils getInstance() {
        return new FileUtils();
    }


    //region 文件目录合法性判断

    /**
     * 文件大小检查
     *
     * @param file          文件
     * @param allowFileSize 允许文件大小(MB)
     * @return 返回 true/false
     */
    public Boolean allowFileSizeCheck(File file, Long allowFileSize) {
        if (file == null) {
            return false;
        }
        Long fileSize = FileUtils.getInstance().getFileSize(file);
        if (fileSize > allowFileSize * 1024 * 1024) {
            return false;
        }
        return true;
    }

    public Boolean allowFileSizeCheck(MultipartFile file, Long allowFileSize) {
        if (file == null) {
            return false;
        }
        Long fileSize = file.getSize();
        if (fileSize > allowFileSize * 1024 * 1024) {
            return false;
        }
        return true;
    }

    /**
     * 文件类型检查
     *
     * @param fileType      文件类型
     * @param allowFileType 允许的文件类型，之间用'|'分开
     * @return true/false
     */
    public Boolean allowFileTypeCheck(String fileType, String allowFileType) {
        if (CheckUtils.isNull(fileType)) {
            return false;
        }
        String[] allFileTypeArray = Convert.splitStringByLabel(allowFileType, "|");
        for (String s : allFileTypeArray) {
            if (s.equals(fileType)) {
                return true;
            }
        }
        return false;
    }
    //endregion

    //region 文件目录存在性判断

    /**
     * 判断是否是文件是否存在
     *
     * @param fileName 文件名
     * @return true/false
     */
    public Boolean isExistFile(String fileName) {
        if (CheckUtils.isNull(fileName)) {
            return true;
        }
        return isExistFile(new File(fileName));
    }

    /**
     * 判断是否是文件是否存在
     *
     * @param file 文件
     * @return true/false
     */
    public Boolean isExistFile(File file) {
        if (file == null || !file.isFile()) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否是目录是否存在
     *
     * @param fileName 文件名
     * @return true/false
     */
    public Boolean isExistDir(String fileName) {
        if (CheckUtils.isNull(fileName)) {
            return true;
        }
        return isExistDir(new File(fileName));
    }

    /**
     * 判断是否是目录是否存在
     *
     * @param file 文件
     * @return true/false
     */
    public Boolean isExistDir(File file) {
        if (file == null || !file.isDirectory()) {
            return false;
        }
        return true;
    }

    //endregion

    //region 文件目录相关操作（例如：创建、追加、新增、删除、系统允许的文件类型检查、大小检查、允许上传文件类型的获取、扩展名等等)

    /**
     * 创建单个目录，若目录存在，则不创建
     *
     * @param dirName 包含路径的单层目录名
     * @return true/false
     */
    public Boolean mkdir(String dirName) throws Exception {
        if (CheckUtils.isNull(dirName)) {
            return false;
        }

        try {
            File file = new File(dirName);
            if (file.exists()) {
                return true;
            }
            boolean bl = file.mkdir();
            return bl;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 删除文件或者目录
     *
     * @param fileName 包含文件或者目录名称
     * @return true/false
     */
    public Boolean deleteFile(String fileName) throws Exception {
        if (CheckUtils.isNull(fileName)) {
            return true;
        }
        try {
            Boolean bl = deleteFile(new File(fileName));
            return bl;
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * 删除文件或者目录
     *
     * @param file 文件或者目录
     * @return true/false
     */
    public Boolean deleteFile(File file) throws Exception {
        if (file == null) {
            return true;
        }
        try {
            boolean bl = file.delete();
            return bl;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 追加文件
     *
     * @param fileName 包含路径的文件名
     * @param str      字符内容
     */
    public void appendFile(String fileName, String str) {
        if (CheckUtils.isNull(fileName)) {
            return;
        }
        str = CheckUtils.isNull(str) ? "" : str;
        appendFile(new File(fileName), str);
    }

    /**
     * 追加文件
     *
     * @param file 文件
     * @param str  字符内容
     */
    public void appendFile(File file, String str) {
        //todo
    }

    /**
     * 追加文件
     *
     * @param fileName 包含路径的文件名
     * @param str      字符内容
     * @param mode     保存模式：0非追加模式，1追加模式，默认是追加模式
     */
    public void saveFile(String fileName, String str, Integer mode) {
        if (CheckUtils.isNull(fileName)) {
            return;
        }
        str = CheckUtils.isNull(str) ? "" : str;
        saveFile(new File(fileName), str, mode);
    }

    /**
     * 保存文件
     *
     * @param file 文件
     * @param str  字符内容
     * @param mode 保存模式：0非追加模式，1追加模式，默认是追加模式
     */
    public void saveFile(File file, String str, Integer mode) {
        //todo
    }


    /**
     * 基于追加模式保存文件
     *
     * @param fileName 包含路径的文件名
     * @param str      字符内容
     */
    public void saveFile(String fileName, String str) {
        if (CheckUtils.isNull(fileName)) {
            return;
        }
        str = CheckUtils.isNull(str) ? "" : str;
        saveFile(new File(fileName), str, 1);
    }

    /**
     * 基于追加模式保存文件
     *
     * @param file 文件
     * @param str  字符内容
     */
    public void saveFile(File file, String str) {
        if (file == null) {
            return;
        }
        str = CheckUtils.isNull(str) ? "" : str;
        saveFile(file, str, 1);
    }

    /**
     * 读取文本文件
     *
     * @param fileName 包含路径的文件名
     * @return 文件内容
     */
    public String readTxtFile(String fileName) {
        if (CheckUtils.isNull(fileName)) {
            return "";
        }
        return readTxtFile(new File(fileName));
    }

    /**
     * 读取文本文件
     *
     * @param file 文件
     * @return 文件内容
     */
    public String readTxtFile(File file) {
        //todo
        return null;
    }

    /**
     * 获取文件的扩展名
     *
     * @param file File
     * @return 扩展名
     */
    public String getFileExName(File file) {
        String path = file.getPath();
        String[] pathArray = Convert.splitStringByLabel(path, ".");
        if (pathArray.length < 1) {
            return "";
        }
        return pathArray[pathArray.length - 1];
    }

    /**
     * 获取文件的扩展名
     *
     * @param fileName 包含路径的文件名
     * @return 扩展名
     */
    public String getFileExName(String fileName) {
        if (CheckUtils.isNull(fileName)) {
            return "";
        }
        return getFileExName(new File(fileName));
    }

    /**
     * 获得文件的大小
     *
     * @param file File
     * @return 字节
     */
    public Long getFileSize(File file) {
        if (file.exists() && file.isFile()) {
            return file.length();
        }
        return Long.valueOf(0);
    }

    /**
     * 获取文件名
     *
     * @param file File
     * @return 文件名
     */
    public String getFileName(File file) {
        if (file == null) {
            return "";
        }
        String path = file.getPath();
        if (path == null || CheckUtils.isNull(path)) {
            return "";
        }
        String[] pathArrray = Convert.splitStringByLabel(path, "\\");
        if (pathArrray.length > 0) {
            return pathArrray[pathArrray.length - 1];
        } else {
            return path;
        }
    }


    /**
     * 基于当前前缀+yyyy-MM-dd hh:mm:ss+3位随机数生成文件名
     *
     * @param preFileName 前缀
     * @return 前缀+yyyy_MM_dd_hh_mm_ss+3位随机数
     */
    public String getFileNameByCurrentTime(String preFileName) {
        //todo
        return null;
    }

    /**
     * 基于当前前yyyy-MM-dd hh:mm:ss+3位随机数生成文件名
     *
     * @return yyyy_MM_dd_hh_mm_ss+3位随机数
     */
    public String getFileNameByCurrentTime() {
        //todo
        return getFileNameByCurrentTime("");
    }

    /**
     * 获取系统允许使用的文件类型
     *
     * @return 文件类型
     */
    public String getUnLimitFileType() {
        return Const.UNLIMITFILENAME.toLowerCase();
    }

    /**
     * 以数组的形式获取系统允许使用的文件类型
     *
     * @return 文件类型
     */
    public String[] getUnLimitFileTypeArray() {
        String fileType = Const.UNLIMITFILENAME.toLowerCase();
        if (CheckUtils.isNull(fileType)) {
            return null;
        }
        return Convert.splitStringByLabel(fileType, "|");
    }
    //endregion

    /**
     * 多文件打压缩包
     *
     * @param file
     * @param out
     * @throws IOException
     */
    public void compressZip(File file, String name, ZipOutputStream out) throws IOException {
        ZipEntry entry = new ZipEntry(name);
        out.putNextEntry(entry);
        int len;
        byte[] buffer = new byte[1024];
        FileInputStream fis = new FileInputStream(file);
        while ((len = fis.read(buffer)) > 0) {
            out.write(buffer, 0, len);
            out.flush();
        }
        out.closeEntry();
        fis.close();
    }

    /**
     * 获取目录下所有文件
     *
     * @param path   文件路径
     * @param suffix 是否需要后缀
     * @return
     */
    public List<String> loadFileByPath(String path, boolean suffix) {
        File file = new File(path);
        File[] fileList = file.listFiles();
        List<File> newList = Arrays.asList(fileList);
        // 按修改时间排序
        Collections.sort(newList, Comparator.comparing(o -> o.lastModified()));
        List<String> list = new ArrayList<>();
        if (newList != null) {
            for (int i = 0; i < newList.size(); i++) {
                if (newList.get(i).isFile()) {
                    if (suffix) {
                        list.add(newList.get(i).getName());
                        continue;
                    }
                    list.add(newList.get(i).getName().substring(0, newList.get(i).getName().lastIndexOf(".")));
                }
            }
        }
        return list;
    }

    /**
     * 获取目录下所有文件
     *
     * @param path   文件路径
     * @param suffix 是否需要后缀
     * @return
     */
    public List<Map<String, String>> findFileByPath(String path, boolean suffix) {
        List<Map<String, String>> list = new ArrayList<>();
        File[] fileList = new File(path).listFiles();
        if(fileList!=null){
            List<File> newList = Arrays.asList(fileList);
            // 按修改时间排序
            Collections.sort(newList, Comparator.comparing(o -> o.lastModified()));
            Map<String, String> fileMap = null;
            if (newList != null) {
                for (int i = 0; i < newList.size(); i++) {
                    if (newList.get(i).isFile()) {
                        fileMap = new HashMap<>();
                        fileMap.put("filePath", newList.get(i).getPath());
                        fileMap.put("fileSize", formatFileSize(newList.get(i).length()));
                        fileMap.put("fileTime", Convert.formatDateTime(newList.get(i).lastModified(), "yyyy-MM-dd HH:mm:ss"));
                        if (suffix) {
                            fileMap.put("fileName", newList.get(i).getName());
                        } else {
                            fileMap.put("fileName", newList.get(i).getName().substring(0, newList.get(i).getName().lastIndexOf(".")));
                        }
                        list.add(fileMap);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 转换文件大小
     */
    public static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 检测文件是否被占用
     *
     * @return
     */
    public boolean checkFileUsed(String path) {
        try {
            File file = new File(path);
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.close();
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 判断路径是否存在，不存在创建路径
     *
     * @param path
     */
    public void existsMkdir(String path) {
        File file = new File(path);
        // 判断文件父目录是否存在
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }

    public static void existsMkdir(File file) {
        // 判断文件父目录是否存在
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean exists(String path) {
        return new File(path).exists();
    }

    /**
     * 拷贝文件
     *
     * @param original 原文件
     * @param target   目标文件
     * @param isDelete 拷贝后是否删除原文件
     */
    public void copyFile(String original, String target, boolean isDelete) {
        // 判断目标路径是否存在
        existsMkdir(target);
        // 拷贝文件
        FileChannel inputChanel = null;
        FileChannel outChanel = null;
        File file = new File(original);
        File file1 = new File(target);
        try {
            inputChanel = new FileInputStream(file).getChannel();
            outChanel = new FileOutputStream(file1).getChannel();
            outChanel.transferFrom(inputChanel, 0, inputChanel.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputChanel != null) inputChanel.close();
                if (outChanel != null) outChanel.close();
                // 是否删除原文件
                System.err.println(file.exists());
                if (isDelete && file.exists()) file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * HTML导出为WORD
     *
     * @param request
     * @param response
     * @param content
     * @param fileName
     */
    public static void exportWord(HttpServletRequest request, HttpServletResponse response, String content, String fileName) {
        try {
            //word内容
            byte b[] = content.getBytes("utf-8");
            ByteArrayInputStream bais = new ByteArrayInputStream(b);

            //生成word格式
            POIFSFileSystem poifs = new POIFSFileSystem();
            DirectoryEntry directory = poifs.getRoot();
            directory.createDocument("WordDocument", bais);
            request.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");//导出word格式
            response.addHeader("Content-Disposition", "attachment;filename=" +
                    new String((fileName + ".doc").getBytes(), "iso-8859-1"));

            OutputStream ostream = response.getOutputStream();
            poifs.writeFilesystem(ostream);
            bais.close();
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}