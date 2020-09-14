package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.LoginLog;
import com.dhlk.entity.basicmodule.Org;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface LoginLogDao {

    Integer insert(LoginLog loginLog);


    List<LoginLog> findList(@Param("userName") String userName,@Param("ip") String ip,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("tenantId") Integer tenantId);
}
