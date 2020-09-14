package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.FaultCode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @创建人 wangq
 * @创建时间 2020/6/4
 * @描述 故障代码service
 */
public interface FaultCodeService {

    Result save(FaultCode faultCode);

    Result delete(String ids);

    Result findList( String name,String code,Integer pageNum, Integer pageSize);
}
