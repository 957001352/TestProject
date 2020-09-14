package com.dhlk.web.basicmodule.service;

import com.dhlk.web.basicmodule.service.fbk.AttachmentServiceFbk;
import com.dhlk.web.config.FeignMultipartConfig;
import com.dhlk.domain.BaseFile;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/31
 */
@FeignClient(value = "basicmodule-service/attachment", fallback = AttachmentServiceFbk.class,configuration = FeignMultipartConfig.class)
public interface AttachmentService {

    /**
     * 附件上传
     * @param file
     * @param isAdd
     * @param dataId
     * @return
     */
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result upload(@RequestPart(value = "file") MultipartFile file,
                  @RequestParam(value = "isAdd",required = false,defaultValue= "false") boolean isAdd,
                  @RequestParam(value = "dataId", required = false) String dataId);

    /**
     * 保存附件记录
     * @param baseFile
     * @return
     */
    @PostMapping(value = "/saveRecord")
    Result saveRecord(@RequestBody BaseFile baseFile);

    /**
     * 根据关联数据查询附件
     * @param dataId
     * @return
     */
    @GetMapping(value = "/findByDataId")
    List<BaseFile> findByDataId(@RequestParam(value = "dataId") String dataId);

    /**
     * 根据附件ID查询附件
     * @param id
     * @return
     */
    @GetMapping(value = "/findById")
    BaseFile findById(@RequestParam(value = "id") Integer id);

    /**
     * 根据附件ID删除附件
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteById")
    Result deleteById(@RequestParam(value = "id") Integer id);

    /**
     * 根据关联数据删除附件
     * @param dataId
     * @return
     */
    @DeleteMapping(value = "/deleteByDataId")
    Result  deleteByDataId(@RequestParam(value = "dataId") String dataId);
}
