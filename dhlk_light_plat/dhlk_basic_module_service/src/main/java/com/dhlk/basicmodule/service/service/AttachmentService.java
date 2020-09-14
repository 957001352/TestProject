package com.dhlk.basicmodule.service.service;


import com.dhlk.domain.BaseFile;
import com.dhlk.domain.Result;
import java.util.List;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/31
 */
public interface AttachmentService {

    /**
     * 保存附件记录
     * @param baseFile
     * @return
     */
    Result saveRecord(BaseFile baseFile);

    /**
     * 根据关联数据查询附件
     * @param dataId
     * @return
     */
    List<BaseFile> findByDataId(String dataId);

    /**
     * 根据附件ID查询附件
     * @param id
     * @return
     */
    BaseFile findById(Integer id);

    /**
     * 根据附件ID删除附件
     * @param id
     * @return
     */
    Result deleteById(Integer id);

    /**
     * 根据关联数据删除附件
     * @param dataId
     * @return
     */
    Result deleteByDataId(String dataId);
}
