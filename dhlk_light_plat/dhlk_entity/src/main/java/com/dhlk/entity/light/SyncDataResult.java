package com.dhlk.entity.light;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/8/10
 */
@Data
public class SyncDataResult<T> {
    private Integer id;
    private String tableName; //表名
    private T data; // 数据
    private String operate; //insert ,update ,delete 执行操作
    private Long ts=System.currentTimeMillis(); //存入时间
    private String dataId; // 数据联查id
    private Integer tenantId;

    public SyncDataResult(){

    }

    public SyncDataResult(String tableName, T data, String operate) {
        this.tableName = tableName;
        this.data = data;
        this.operate = operate;
    }

    public SyncDataResult(String tableName, T data, String operate,Integer tenantId) {
        this.tableName = tableName;
        this.data = data;
        this.operate = operate;
        this.tenantId = tenantId;
    }
}