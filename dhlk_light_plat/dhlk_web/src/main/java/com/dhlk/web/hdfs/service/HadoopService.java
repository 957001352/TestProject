package com.dhlk.web.hdfs.service;

import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: jzhao
 * @Date: 2020/4/21 09:37
 * @Description:
 */
@FeignClient(value = "dhlk-hadoop/hadoop")
public interface HadoopService {
    @GetMapping(value = "/listFile")
    Result listFile(@RequestParam(value = "filePath", required = false) String filePath,
                    @RequestParam(value = "viewType") String viewType,
                    @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);

}
