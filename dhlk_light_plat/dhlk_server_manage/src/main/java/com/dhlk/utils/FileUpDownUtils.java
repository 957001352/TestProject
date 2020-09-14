package com.dhlk.utils;


import com.dhlk.domain.BaseFile;
import com.dhlk.domain.Result;
import com.dhlk.enums.SystemEnums;
import com.dhlk.exception.MyException;
import com.dhlk.systemconst.Const;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipOutputStream;

/**
 * Content:文件上传工具类
 * Author:jpdong
 * Date:2020/3/5
 */
public class FileUpDownUtils {
    /**
     * 获取生成的ID
     * @return
     */
    public String getId(){
        String id = UUID.randomUUID().toString();
        return id.replaceAll("-", "");
    }
    /**
     * 文件保存路径
     */
    private String uploadFileSavePath;
    /**
     * 允许上传文件的大小
     */
    private Long allowFileSize;
    /**
     * 允许上传文件的类型，之间用|分割
     */
    private String allowFileType;
    /**
     * 当前文件的上传类型，允许为空，当为空时，自行获取文件的类型，依据获取到的文件类型进行限制性判断
     */
    private String currentUploadFileType;

    /**
    * 文件名称
    */
    private String currentFileName;

    /**
     * 当前上传文件
     */
    private MultipartFile currentUploadFile;

    /**
     * 初始化
     *
     * @param uploadFileSavePath 文件保存路径
     * @param currentUploadFile  当前上传文件
     * @throws MyException 异常
     */
    public FileUpDownUtils(String uploadFileSavePath, MultipartFile currentUploadFile) throws MyException {
        if (CheckUtils.isNull(uploadFileSavePath)) {
            throw new MyException(SystemEnums.NO_EXITS_FILE_SAVEPATH);
        }
        this.uploadFileSavePath = uploadFileSavePath;
        this.currentUploadFile = currentUploadFile;
        this.currentFileName=currentUploadFile.getOriginalFilename();
        this.currentUploadFileType = FileUtils.getInstance().getFileExName(currentFileName);
        this.allowFileSize = Long.parseLong(Const.ALLOWUPLOADFILESIZE.toString());
        this.allowFileType = Const.UNLIMITFILENAME;
    }
    /**
     * 初始化
     *
     * @param uploadFileSavePath 文件保存路径
     * @param currentUploadFile  当前上传文件
     * @param allowFileSize      允许上传的最大的大小(MB)
     * @param allowFileType      允许上传的文件类型，可以为空
     * @throws MyException 异常
     */
    public FileUpDownUtils(String uploadFileSavePath, MultipartFile currentUploadFile, Long allowFileSize, String allowFileType) throws MyException {
        if (CheckUtils.isNull(uploadFileSavePath)) {
            throw new MyException(SystemEnums.NO_EXITS_FILE_SAVEPATH);
        }
        this.uploadFileSavePath = uploadFileSavePath;
        this.currentUploadFile = currentUploadFile;
        this.currentFileName=currentUploadFile.getOriginalFilename();
        this.currentUploadFileType = FileUtils.getInstance().getFileExName(currentFileName);
        if (!CheckUtils.isNumeric(allowFileSize + "")) {
            throw new MyException(SystemEnums.PARMS_ILLEGAL);
        }
        if (allowFileSize == null || allowFileSize == 0) {
            this.allowFileSize = Long.parseLong(Const.ALLOWUPLOADFILESIZE.toString());
        } else {
            this.allowFileSize = allowFileSize;
        }
        if (CheckUtils.isNull(allowFileType)) {
            this.allowFileType = Const.UNLIMITFILENAME;
        } else {
            this.allowFileType = allowFileType;
        }
    }

    /**
     * 文件类型检查
     *
     * @return true/false
     */
    public Boolean allowFileTypeCheck() throws MyException {
        Boolean re = false;
        String exFileName = FileUtils.getInstance().getFileExName(currentFileName);
        if (CheckUtils.isNull(exFileName.toLowerCase())) {
            return false;
        }
//        re = FileUtils.getInstance().allowFileTypeCheck(exFileName.toLowerCase(), this.allowFileType);
        return true;
    }


    /**
     * 文件大小检查
     *
     * @return true/false
     * @throws MyException 异常
     */
    public Boolean allFileSizeCheck() throws MyException {
        if (this.currentUploadFile == null) {
            throw new MyException(SystemEnums.PARMS_ILLEGAL);
        }
        return FileUtils.getInstance().allowFileSizeCheck(currentUploadFile, this.allowFileSize);
    }

    /**
     * 执行文件上传
     *
     * @param file File
     * @return UploadFileResult
     * @throws MyException
     */
    public Result execUploadFile(MultipartFile file) throws MyException {
        //1.检查文件类型是否符合系统允许的类型
        if (!allowFileTypeCheck()) {
            //throw new MyException(SystemEnums.NO_ALLOW_FILETYPE);
            return ResultUtils.error(SystemEnums.NO_ALLOW_FILETYPE.getStateInfo());
        }
        //2.检查文件大小是否符合系统要求
        if (!allFileSizeCheck()) {
            return ResultUtils.error(SystemEnums.NO_ALLOW_FILESIZE.getStateInfo());
            //throw new MyException(SystemEnums.NO_ALLOW_FILESIZE);
        }
        //3.上传文件
        return uploadFile(file);
    }

    /**
     * 文件上传
     *
     * @param file File
     * @return UploadFileResult
     * @throws MyException
     */
    private Result uploadFile(MultipartFile file) throws MyException {
        if (file == null) {
            return ResultUtils.error(SystemEnums.PARMS_ILLEGAL.getStateInfo());
            //throw new MyException(SystemEnums.PARMS_ILLEGAL);
        }
        // 关联数据ID
        // 上次文件名,组装保存文件名
        String fileName = file.getOriginalFilename();
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String saveName = getId();
        try {
            //例如：C://abc/uuid.tar
            File targetFile = new File(uploadFileSavePath + "/" + fileName);
            // 判断保存目录,不存在创建
            FileUtils.getInstance().existsMkdir(targetFile);
            // 保存文件
            file.transferTo(targetFile);
            // 保存附件记录
            BaseFile baseFile = new BaseFile();
            baseFile.setName(name);
            baseFile.setSaveName(saveName);
            baseFile.setPath(targetFile.getPath());
            baseFile.setSuffix(suffix);
            return ResultUtils.success(baseFile);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failure();
        }
    }

    //region setter/getter方法
    public String getUploadFileSavePath() {
        return uploadFileSavePath;
    }

    public void setUploadFileSavePath(String uploadFileSavePath) {
        this.uploadFileSavePath = uploadFileSavePath;
    }

    public Long getAllowFileSize() {
        return allowFileSize;
    }

    public void setAllowFileSize(Long allowFileSize) {
        this.allowFileSize = allowFileSize;
    }

    public String getAllowFileType() {
        return allowFileType;
    }

    public void setAllowFileType(String allowFileType) {
        this.allowFileType = allowFileType;
    }

    public String getCurrentUploadFileType() {
        return currentUploadFileType;
    }

    public void setCurrentUploadFileType(String currentUploadFileType) {
        this.currentUploadFileType = currentUploadFileType;
    }

    public MultipartFile getCurrentUploadFile() {
        return currentUploadFile;
    }

    public void setCurrentUploadFile(MultipartFile currentUploadFile) {
        this.currentUploadFile = currentUploadFile;
    }

    public String getCurrentFileName() {
        return currentFileName;
    }

    public void setCurrentFileName(String currentFileName) {
        this.currentFileName = currentFileName;
    }


    /**
     * 附件下载
     *
     * @param baseFile
     * @param response
     */
    public static Result down(BaseFile baseFile, HttpServletResponse response) {
        try {
            String name = new String(baseFile.getName().getBytes("UTF-8"), "iso-8859-1");
            File file = new File(baseFile.getPath());
            if (!file.exists()) return ResultUtils.error(-1, "附件不存在!");
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(baseFile.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Length", "" + file.length());
            response.addHeader("Content-Disposition", "attachment;filename=" + name + "." + baseFile.getSuffix());
            // 写出文件
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(buffer);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.error(-1, "下载失败!");
        }
        return ResultUtils.success();
    }

    /**
     * 附件下载
     *
     * @param path
     * @param response
     */
    public static Result down(String path, HttpServletResponse response) {
        try {
            File file = new File(path);
            String name = new String(file.getName().getBytes("UTF-8"), "iso-8859-1");
            if (!file.exists()) return ResultUtils.error(-1, "附件不存在!");
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Length", "" + file.length());
            response.addHeader("Content-Disposition", "attachment;filename=" + name);
            // 写出文件
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(buffer);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.error(-1, "下载失败!");
        }
        return ResultUtils.success();
    }

    /**
     * 附件下载
     *
     * @param baseFileList
     * @param response
     */
    public static Result down(List<BaseFile> baseFileList, HttpServletResponse response) {
        try {
            if (baseFileList.size() <= 0) return ResultUtils.error(-1, "附件不存在!");
            // 文件名转码
            String name = new String("附件".getBytes("UTF-8"), "iso-8859-1");
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=" + name + ".zip");
            ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
            // 压缩下载
            baseFileList.stream().forEach(baseFile -> {
                try {
                    String fileName = baseFile.getName() + "." + baseFile.getSuffix();
                    if (FileUtils.getInstance().equals(baseFile.getPath())) {
                        FileUtils.getInstance().compressZip(new File(baseFile.getPath()), fileName, out);
                        response.flushBuffer();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.error(-1, "下载失败!");
        }
        return ResultUtils.success();
    }

    /**
     * @param logPath
     * @param fileName
     * @param response
     * @return domain.Result
     * @date 2020/4/13 12:01
     * @author jzhao
     * @description 多文件生成压缩包下载
     */
    public static Result downZipFile(String logPath, String fileName, String zipFileName, HttpServletResponse response) {
        //将文件名称转换为集合
        List<String> nameList = Arrays.asList(fileName.split(","));
        //初期化ZIP流
        ZipOutputStream out = null;
        try {
            // 文件名转码
            zipFileName = new String(zipFileName.getBytes("UTF-8"), "iso-8859-1");
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=" + zipFileName + ".zip");
            out = new ZipOutputStream(response.getOutputStream());
            //循环处理传过来的集合
            for (String name : nameList) {
                if (FileUtils.getInstance().isExistFile(logPath + name)) {
                    FileUtils.getInstance().compressZip(new File(logPath + name), name, out);
                    response.flushBuffer();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtils.error(-1, "下载失败!");
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

}
