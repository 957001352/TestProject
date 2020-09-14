package com.dhlk.hadoop.controller;

import com.dhlk.domain.Result;
import com.dhlk.hadoop.utils.HadoopUtils;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description hadoop文件管理
 * @Author lpsong
 * @Date 2020/4/15
 */
@RestController
@RequestMapping(value = "/hadoop")
public class HadoopController {
    @Autowired
    private HadoopUtils hadoopUtil;
    @Value("${hdfs.token}")
    private String sysToken;

    @Value("${hdfs.directory}")
    private String directory;
    /**
     * 读取文件列表
     *
     * @return
     */
    @GetMapping("/listFile")
    //@RequiresAuthentication
    public Result listFile(@RequestParam(value = "filePath", required = false) String filePath,
                           @RequestParam(value = "viewType") String viewType,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) throws Exception {
        if(viewType.equals("file")){
            return hadoopUtil.fileList(filePath, pageNum, pageSize);
        }else{
            if(CheckUtils.isNull(filePath)){
                filePath=directory;
            }
            return hadoopUtil.readPathInfo(filePath);
        }
    }
    /**
    * 对外提供hdfs历史数据下载token认证
     * @param token
    * @return
    */
    private boolean checkToken(String token){
        for(String str:sysToken.split(",")){
            if(str.equals(token)){
              return true;
            }
        }
        return false;
    }
    /**
    *  对外提供hdfs历史数据浏览
     * @param token
     * @param filePath
    * @return
    */
    @GetMapping(value ="/browseFile")
    public Result browseFile(@RequestParam(value = "token", required = true) String token,
                             @RequestParam(value = "filePath", required = false) String filePath) throws Exception {
        if(checkToken(token)){
            if(CheckUtils.isNull(filePath)){
                filePath=directory;
            }
            return hadoopUtil.readPathInfo(filePath);
        }
        return ResultUtils.error("token认证失败");
    }
    /*
    * 对外提供hdfs历史数据下载
     * @param token
     * @param filePath
     * @param response
    * @return
    */
    @GetMapping("/downloadFile")
    public Result downloadFile(@RequestParam(value = "token", required = true) String token,
                               @RequestParam(value = "filePath", required = true) String filePath, HttpServletResponse response) {
        if(checkToken(token)){
            if(CheckUtils.isNull(filePath)){
                filePath=directory;
            }
            return hadoopUtil.downHdfsFile(filePath, response);
        }
        return ResultUtils.error("token认证失败");
    }
    /**
     * @param response
     * @return domain.Result
     * @date 2020/4/17 15:28
     * @author jzhao
     * @description 下载文件
     */
    @GetMapping("/downFile")
    //@RequiresAuthentication
    public Result downFile(@RequestParam(value = "filePath", required = true) String filePath, HttpServletResponse response) {
        return hadoopUtil.downHdfsFile(filePath, response);
    }

//    public static void main(String[] args) {
//        String[] tokens=new String[]{"dhlk_hadoop_001","dhlk_hadoop_002"};
//        for(String token:tokens){
//            System.out.println(DES.md5(token));
//        }
//    }
}