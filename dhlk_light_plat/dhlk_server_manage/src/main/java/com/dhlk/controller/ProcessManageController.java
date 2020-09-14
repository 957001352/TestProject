package com.dhlk.controller;

import com.dhlk.domain.Result;
import com.dhlk.service.ProcessManageService;
import com.dhlk.service.RedisService;
import com.dhlk.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @program: dhlk_server_manage
 * @description:
 * @author: wqiang
 * @create: 2020-07-13 10:03
 **/

@RestController
@RequestMapping("processManage")
public class ProcessManageController {

    @Autowired
    ProcessManageService processManageService;

    @Autowired
    RedisService redisService;

    @Value("${upoload.path.sqlPath}")
    String sqlPath;


    /**
     * 获取服务列表  202007013 wqiang
     */
    @CrossOrigin
    @GetMapping("/getAllProcess")
    public Result getAllServer(
            @RequestParam(required = false) String processName,
            @RequestParam(required = false) String runStatus) {
        return processManageService.getAllProcessInfo(processName, runStatus,null);

    }

    /**
     * 保存文件上传的路径到redis中
     *
     * @param key
     * @param value
     * @return
     */
    @CrossOrigin
    @PostMapping("/savePath")
    public Result savePath(@RequestParam String key, @RequestParam String value) {
        return new Result(0, "", redisService.set(key, value));

    }

    /**
     * 停止进程
     *
     * @param pid 进程pid
     * @return
     */
    @CrossOrigin
    @GetMapping("/stop")
    public Result stopProcess(@RequestParam String pid) {
        return processManageService.stopProcess(pid);
    }

    /**
     * 开启服务
     *
     * @param processName
     * @return
     */
    @CrossOrigin
    @GetMapping("/start")
    public Result startProcess(String processName) {
        return processManageService.startProcess(processName);
    }

    /**
     * 获取redis中的文件路径
     *
     * @param key
     * @return
     */
    @CrossOrigin
    @GetMapping("/getPath")
    public Result getPath(@RequestParam String key) {
        if(key.contains("sql")){
            redisService.set("sql",sqlPath);
            return ResultUtils.success(sqlPath);
        }
        String path = (String) redisService.get(key);
        String substring = "";
        if (com.dhlk.utils.StringUtils.isNullOrEmpty(path)) {
            return ResultUtils.error("路径为空，请添加上传路径！");
        }
        if (path.contains(".jar")) {
            substring = StringUtils.substringBefore(path, "/dhlk_light");
            //substring = path.substring(0, path.lastIndexOf("/"));
            //redisService.set(key, substring);
            return new Result(0, "", substring);
        }
        if (path.contains(".zip")) {
            substring = path.substring(0, path.lastIndexOf("/"));
            redisService.set(key, substring);
            return new Result(0, "", substring);
        }

        return new Result(0, "", path);
    }


}