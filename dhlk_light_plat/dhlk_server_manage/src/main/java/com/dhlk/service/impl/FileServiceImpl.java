package com.dhlk.service.impl;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.dhlk.domain.Result;
import com.dhlk.service.FileService;
import com.dhlk.service.ProcessManageService;
import com.dhlk.service.RedisService;
import com.dhlk.systemconst.Const;
import com.dhlk.utils.FileUpDownUtils;
import com.dhlk.utils.FileUtils;
import com.dhlk.utils.LinuxCommandUtils;
import com.dhlk.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 文件上传
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    RedisService redisService;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private ProcessManageService processManageService;

    @Value("${sqlKeyword.list}")
    private String sqlKeyword;


    @Override
    public Result upload(MultipartFile file, String key, String pid) throws IOException {
        String savePath = (String) redisService.get(key);
        //如果redis中取出来的路径为空,则返回失败
        if (StringUtils.isBlank(savePath)) {
            return ResultUtils.error("上传失败,上传服务路径为空");
        }
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        //上传文件的绝对路径
        String pathVal = savePath + Const.BIAS + fileName;
        String cmd = Const.RM_RF_COMMAND + savePath + Const.BIAS + fileName;
        //如果pid不为空,则进程是活的,先停止进程在删除包
        if(!com.dhlk.utils.StringUtils.isNullOrEmpty(pid)){
            processManageService.stopProcess(pid);
        }
        //先去删除这个路径下的.jar文件,在进行上传
        if("jar".equals(suffix.toLowerCase()) || "sql".equals(suffix.toLowerCase())){
            LinuxCommandUtils.exec(new String[]{"/bin/sh", "-c",cmd});
            if(savePath.contains("dhlk_light")){
                savePath = StringUtils.substringBefore(savePath, "/dhlk_light");
                System.out.println("savePath==="  + savePath);
                pathVal = savePath + Const.BIAS + fileName;
                System.out.println("pathVal==="  + pathVal);
            }
        }

        //删除完之后把redis的值变成 路径加名字 如:/home/java/XXX.jar
        if(!"sql".contains(key)){
            redisService.set(key, pathVal);
        }

        // 上传附件
        FileUpDownUtils fileUpload = new FileUpDownUtils(savePath, file); //原来调用fileUtils.getPath()
        Result reult = fileUpload.execUploadFile(file);

        //若果上传的文件是.zip的,则对文件解压
        if("zip".equals(suffix.toLowerCase())){
            //前端上传zip解压前先吧 DHLK_MOM 目录和 LOAD_DHLK_CONF文件删除,再进行解压
            String fileDirPath = savePath + Const.BIAS + Const.DHLK_MOM;
            String fileCofigPath = savePath + Const.BIAS + Const.LOAD_DHLK_CONF;
            File fileConfig = new File(fileCofigPath);
            File fileDir = new File(fileDirPath);
            if(fileDir.isDirectory()){
                FileUtil.del(fileDir);
            }
            if(fileConfig.isFile()){
                FileUtil.del(fileConfig);
            }

            String originalPath = savePath + Const.BIAS + fileName;
            String laterPath = savePath;
            File files = ZipUtil.unzip(originalPath, laterPath, Charset.forName("GBK"));
            String strName =  StringUtils.substringBefore(fileName,".");
            for (File f:files.listFiles()) {
                if(f.getName().equals(strName)){
                    String path = files.getPath() + "/" + f.getName();
                    FileUtil.del(path);
                }
            }
            File monFile =  ZipUtil.unzip(originalPath, laterPath, Charset.forName("GBK"));
            String  mon = "mv " +  laterPath + "/" + Const.DHLKHW + "/" + Const.DHLK_MOM  + " " +laterPath;
            System.out.println("mon ===" + mon);
            String  config =  "mv " +  laterPath + "/" + Const.DHLKHW + "/" + Const.LOAD_DHLK_CONF  + " " +laterPath;
            System.out.println("config ===" + config);
            for (File f:monFile.listFiles()) {
               if(f.getName().equals(Const.DHLKHW)){
                   LinuxCommandUtils.exec(new String[]{"/bin/sh", "-c",mon});
                   LinuxCommandUtils.exec(new String[]{"/bin/sh", "-c",config});
                   System.out.println("end......");
               }
            }

        }
        if(key.contains("sql")){
            if(!com.dhlk.utils.StringUtils.isNullOrEmpty(sqlKeyword)){
                String[] strs = sqlKeyword.split(",");
                boolean boo = FileUtils.isContainsSqlKey(pathVal,Arrays.asList(strs));
                if(!boo){
                    return ResultUtils.error("sql执行失败，不能执行删除表或库操作！");
                }
            }
           return processManageService.executeSQL(pathVal);
        }
        return reult;
    }


}
