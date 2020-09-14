package com.dhlk.basicmodule.service.dao;

import com.dhlk.domain.BaseFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/31
 */
public interface AttachmentDao {
    /**
     * insert
     * @param baseFile
     * @return
     */
    Integer insert(BaseFile baseFile);

    /**
     * 根据关联数据查询附件
     * @param dataId
     * @return
     */
    List<BaseFile> findByDataId(@Param(value = "dataId") String dataId,
                                @Param(value = "rootPath") String rootPath);

    /**
     * 根据附件ID查询附件
     * @param id
     * @return
     */
    BaseFile findById(@Param(value = "id") Integer id,
                      @Param(value = "rootPath") String rootPath);

    /**
     * 根据附件ID删除附件
     * @param id
     * @return
     */
    Integer deleteById(Integer id);

    /**
     * 根据关联数据删除附件
     * @param dataId
     * @return
     */
    Integer deleteByDataId(String dataId);
}
