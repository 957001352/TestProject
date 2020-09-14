package com.dhlk.light.factory.util;

import com.alibaba.fastjson.annotation.JSONField;
import com.dhlk.light.factory.enums.LedEnum;
import com.dhlk.utils.HttpContextUtil;
import com.dhlk.utils.IPUtil;

/**
 * @Description 照明设备返回结果
 * @Author lpsong
 * @Date 2020/6/8
 */
public class LedResult<T> {
    private Integer code; //code
    private Integer cmd_type; //命令
    private Integer grp_id=0;
    private Integer dev_type=0;
    private Integer addr_type=0;
    private String ts=String.valueOf(System.currentTimeMillis());//时间戳
    private T data; //Data
    @JSONField(name = "SN")
    private String SN;
    private Integer ver=1;

    public LedResult(){

    }
    public LedResult(LedEnum cmdType, T data) {
        this.cmd_type = cmdType.getState();
        this.data = data;
        this.grp_id = 0;
        this.dev_type = 0;
        this.addr_type=0;
        this.code=0;
    }
    public LedResult(String SN,LedEnum cmdType,long ts){
        this.SN=SN;
        this.cmd_type=cmdType.getState();
        this.grp_id=0;
        this.dev_type=0;
        this.addr_type=0;
        this.code=0;
        this.ts=String.valueOf(ts);
    }
    public LedResult(String SN,LedEnum cmdType, T data,long ts){
        this.SN=SN;
        this.cmd_type=cmdType.getState();
        this.data=data;
        this.grp_id=0;
        this.dev_type=0;
        this.addr_type=0;
        this.code=0;
        this.ts=String.valueOf(ts);

    }

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCmd_type() {
        return cmd_type;
    }

    public void setCmd_type(Integer cmd_type) {
        this.cmd_type = cmd_type;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        data = data;
    }

    public Integer getGrp_id() {
        return grp_id;
    }

    public void setGrp_id(Integer grp_id) {
        this.grp_id = grp_id;
    }

    public Integer getDev_type() {
        return dev_type;
    }

    public void setDev_type(Integer dev_type) {
        this.dev_type = dev_type;
    }

    public Integer getAddr_type() {
        return addr_type;
    }

    public void setAddr_type(Integer addr_type) {
        this.addr_type = addr_type;
    }

    public Integer getVer() {
        return ver;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }
}