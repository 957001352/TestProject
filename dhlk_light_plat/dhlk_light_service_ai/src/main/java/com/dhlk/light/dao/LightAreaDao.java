package com.dhlk.light.dao;

import com.dhlk.light.entity.LightAera;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/26
 * Time:15:01
 * @Description:
 */
@Repository
public interface LightAreaDao {

    List<LightAera> selectAreaByTenantId(@Param("tenantId") Integer tenantId,@Param("attachPath")String attachmentPath);
}
