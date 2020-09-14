package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.NetDevicesSoft;
import com.dhlk.entity.basicmodule.NetFault;
import com.dhlk.entity.basicmodule.OrgAuth;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/4/20
 */
@Repository
public interface NetFaultDao {


    Integer insert(NetFault netFault);

    List<NetFault> findList(@Param("tbId") String tbId,@Param("status") Integer status);

    Integer dealFault(@Param("id") Integer id, @Param("dealUser") Integer dealUser, @Param("status") Integer status);
}
