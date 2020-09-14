package com.dhlk.web.basicmodule.controller;

import com.dhlk.web.basicmodule.service.AttachmentService;
import com.dhlk.domain.BaseFile;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.FileUpDownUtils;
import com.dhlk.utils.ResultUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/31
 */
@RestController
@RequestMapping(value = "/attachment")
@Api(description = "附件管理")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    /**
     * 附件上传(单文件)
     * @param file
     * @return
     */
    @ApiOperation("附件上传")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result upload(@RequestPart(value = "file") MultipartFile file,
                         @RequestParam(value = "isAdd",required = false,defaultValue= "false") boolean isAdd,
                         @RequestParam(value = "dataId",required = false) String dataId) {
        return attachmentService.upload(file, isAdd, dataId);
    }

    /**
     * 附件上传(多文件)
     * @param files
     * @return
     */
    @ApiOperation("多附件上传")
    @PostMapping(value = "/uploadMore", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result uploadMore(@RequestParam(value = "files") MultipartFile[] files,
                             @RequestParam(value = "dataId",required = false) String dataId) {
        for(int i=0; i<files.length; i++){
            Result result = attachmentService.upload(files[i], true, dataId);
            BaseFile baseFile = (BaseFile) result.getData();
            dataId =  CheckUtils.isNull(dataId)?baseFile.getDataId():dataId;
        }
        return ResultUtils.success(dataId);
    }

    /**
     * 根据附件ID下载
     * @param id
     */
    @ApiOperation("根据附件id附件下载")
    @GetMapping(value = "/down")
    public Result down(@RequestParam(value = "id") Integer id, HttpServletResponse response) {
        return FileUpDownUtils.down(attachmentService.findById(id), response);
    }

    /**
     * 根据关联数据ID附件下载
     * @param id
     */
    @ApiOperation("根据数据id附件下载")
    @GetMapping(value = "/downByDataId")
    public Result downByDataId(@RequestParam(value = "id") String id, HttpServletResponse response) {
        return FileUpDownUtils.down(attachmentService.findByDataId(id), response);
    }

    /**
     * 根据附件路径下载
     * @param path
     */
    @ApiOperation("根据附件路径下载")
    @GetMapping(value = "/downByPath")
    public Result downByPath(@RequestParam(value = "path") String path, HttpServletResponse response) {
        return FileUpDownUtils.down(path, response);
    }

    /**
     * 根据关联数据查询附件
     * @param dataId
     * @return
     */
    @ApiOperation("根据数据id查询附件列表")
    @GetMapping(value = "/findByDataId")
    public Result findByDataId(@RequestParam(value = "dataId") String dataId) {
        return ResultUtils.success(attachmentService.findByDataId(dataId));
    }

    /**
     * 根据附件ID查询附件
     * @param id
     * @return
     */
    @ApiOperation("根据附件ID查询附件")
    @GetMapping(value = "/findById")
    public Result findById(@RequestParam(value = "id") Integer id) {
        return ResultUtils.success(attachmentService.findById(id));
    }

    /**
     * 根据附件ID删除附件
     * @param id
     * @return
     */
    @ApiOperation("根据附件ID删除附件")
    @DeleteMapping(value = "/deleteById")
    public Result deleteById(@RequestParam(value = "id") Integer id) {
        return attachmentService.deleteById(id);
    }

    /**
     * 根据关联数据删除附件
     * @param dataId
     * @return
     */
    @ApiOperation("根据数据id删除附件")
    @DeleteMapping(value = "/deleteByDataId")
    public Result deleteByDataId(@RequestParam(value = "dataId") String dataId) {
        return attachmentService.deleteByDataId(dataId);
    }
}
