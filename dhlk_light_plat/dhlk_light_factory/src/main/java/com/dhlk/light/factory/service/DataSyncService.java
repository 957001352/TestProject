package com.dhlk.light.factory.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.SyncDataResult;

/**
 * @author xkliu
 * @date 2020/6/15
 * <p>
 * 数据同步service
 */
public interface DataSyncService {

    /**
     * 同步数据
     *
     * @param token
     * @param code
     * @return
     */
    Result dataSync(String token, String code);

    /**
     * 下载文件到本地
     * @param fileUrl
     * @param targetPath
     * @param fileName
     */
    void downloadFile(String fileUrl, String targetPath, String fileName)throws Exception;

    /**
     * 同步数据
     * @return
     */
    Integer syncData(SyncDataResult syncDataResult);
}
