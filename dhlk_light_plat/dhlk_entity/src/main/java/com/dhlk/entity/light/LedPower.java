package com.dhlk.entity.light;

import com.alibaba.fastjson.JSONObject;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.Convert;
import com.dhlk.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description 灯能耗统计
 * @Author lpsong
 * @Date 2020/6/4
 */
@Data
@ApiModel(value="ledPower",description="灯能耗统计")
public class LedPower {
    @ApiModelProperty(value = "新增为空/修改传值",hidden = true)
    private Integer id;

    @ApiModelProperty(value = "灯sn码")
    private String ledSn;

    @ApiModelProperty(value = "电流")
    private BigDecimal electric;

    @ApiModelProperty(value = "电压")
    private BigDecimal voltage;

    @ApiModelProperty(value = "功率")
    private BigDecimal power;

    @ApiModelProperty(value = "继电器状态值")
    private String switchStatus;

    @ApiModelProperty(value = "统计时间",hidden = true)
    private String createTime;

    @ApiModelProperty(value = "电能")
    private BigDecimal energy;

    @ApiModelProperty(value = "灯亮度")
    private Integer ledBrightness ;
    @ApiModelProperty(value = "灯状态",hidden = true)
    private String status;
    @ApiModelProperty(value = "故障码",hidden = true)
    private String fault;

    private Long ts;

    private Integer grpId;//组id


    private Integer peopleStatus;//人感状态

    private Integer linghtStatus;//光感状态

    @ApiModelProperty(value = "租户ID",hidden = true)
    private Integer tenantId;


    public LedPower(JSONObject result) {
        JSONObject data = JSONObject.parseObject(result.get("data")+"");
        if (data.get("on_off").toString().equals("10")) {
            data.put("on_off", "0");
        } else if (data.get("on_off").toString().equals("11")) {
            data.put("on_off", "1");
        }
        this.ledSn = result.get("SN")==null?"":result.get("SN").toString();
        if(data.get("E_V")!=null){
            this.voltage = BigDecimal.valueOf(Double.parseDouble(data.get("E_V").toString()));
        }
        if(data.get("E_I")!=null){
            this.electric = BigDecimal.valueOf(Double.parseDouble(data.get("E_I").toString()));
        }
        if(data.get("E_P")!=null){
            this.power = BigDecimal.valueOf(Double.parseDouble(data.get("E_P").toString()));
        }
        if(data.get("E_E")!=null){
            this.energy = BigDecimal.valueOf(Double.parseDouble(data.get("E_E").toString()));
        }
        if(data.get("brt_ness")!=null){
            this.ledBrightness =Integer.parseInt(data.get("brt_ness").toString());
        }
        if(result.get("grp_id")!=null){
            this.grpId =Integer.parseInt(result.get("grp_id").toString());
        }
        if(!CheckUtils.isNull(result.get("ts"))){
            this.ts=Long.parseLong(result.get("ts").toString());
        }
        this.createTime=Convert.formatDateTime(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss");
        if(data.get("on_off")!=null){
            this.switchStatus=data.get("on_off").toString();
            this.status=data.get("on_off").toString();
        }

    }

    public LedPower() {
        this.createTime= Convert.formatDateTime(DateUtils.getLongCurrentTimeStamp(),"yyyy-MM-dd HH:mm:ss");
    }
}