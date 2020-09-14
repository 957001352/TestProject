package com.dhlk.entity.light;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 灯操作结果
 * @Author lpsong
 * @Date 2020/6/9
 */
@Data
public class LedRecord {
    @ApiModelProperty(value = "新增为空/修改传值")
    private  Integer id;
    @ApiModelProperty(value = "操作人")
    private  String operator;//操作人
    @ApiModelProperty(value = "操作人ip")
    private  String operateIp;//操作人ip
    @ApiModelProperty(value = "操作时间")
    private  String operateTime;//操作时间
    @ApiModelProperty(value = "时间戳")
    private  String timeKey;//时间戳
    @ApiModelProperty(value = "原始命令")
    private  String commandInfo;//原始命令
    @ApiModelProperty(value = "操作设备")
    private  String ledSn;//操作设备
    @ApiModelProperty(value = "返回结果")
    private  String backResult;//返回结果
    @ApiModelProperty(value = "发送结果")
    private  String operateResult;//发送结果
    @ApiModelProperty(value = "返回时间")
    private  String backTime;//操作时间
    @ApiModelProperty(value = "发送终端")
    private  String source;//操作时间

    private Integer tenantId;
    public LedRecord(){

    }
    //插入发送结果
    public LedRecord(String operator,String operateIp,String timeKey, String commandInfo, String ledSn, String operateResult,String source,String operateTime) {
        this.operator = operator;
        this.timeKey = timeKey;
        this.commandInfo = commandInfo;
        this.ledSn = ledSn;
        this.operateResult = operateResult;
        this.operateIp=operateIp;
        this.source=source;
        this.operateTime=operateTime;
    }
    //插入发送结果
    public LedRecord(String timeKey,String operator, String commandInfo, String ledSn, String operateResult,String source,String operateTime,String backResult,String backTime) {
        this.timeKey = timeKey;
        this.commandInfo = commandInfo;
        this.ledSn = ledSn;
        this.operateResult = operateResult;
        this.source=source;
        this.operator=operator;
        this.operateTime=operateTime;
        this.backResult=backResult;
        this.backTime=backTime;
    }
    //插入发送结果
    public LedRecord(String timeKey,String operator, String commandInfo, String ledSn, String operateResult,String source,String operateTime,String backResult,String backTime,Integer tenantId) {
        this.timeKey = timeKey;
        this.commandInfo = commandInfo;
        this.ledSn = ledSn;
        this.operateResult = operateResult;
        this.source=source;
        this.operator=operator;
        this.operateTime=operateTime;
        this.backResult=backResult;
        this.backTime=backTime;
        this.tenantId=tenantId;
    }
    //插入发送结果
    public LedRecord(String timeKey,String operator, String commandInfo, String ledSn, String operateResult,String source,Integer tenantId,String operateTime) {
        this.timeKey = timeKey;
        this.commandInfo = commandInfo;
        this.ledSn = ledSn;
        this.operateResult = operateResult;
        this.source=source;
        this.operator=operator;
        this.tenantId=tenantId;
        this.operateTime=operateTime;
    }
    //更新返回结果
    public LedRecord(String timeKey, String ledSn, String backResult, String backTime) {
        this.timeKey = timeKey;
        this.ledSn = ledSn;
        this.backResult = backResult;
        this.backTime = backTime;
    }
    public LedRecord(String ledSn, String backResult, String backTime) {
        this.ledSn = ledSn;
        this.backResult = backResult;
        this.backTime = backTime;
    }
}