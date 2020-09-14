package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.OrgAuth;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description 接口管理
 * @Author lpsong
 * @Date 2020/3/11
 */
@Repository
public interface OrgAuthDao {

    Integer insert(OrgAuth orgAuth);

    Integer update(OrgAuth orgAuth);

    Integer delete(List<String> ids);

    List<OrgAuth> findList(Integer tenantId);


    OrgAuth findAuthByOrg(String tenantCode);

}