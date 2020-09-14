package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.Org;
import com.dhlk.entity.basicmodule.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrgDao {

    Integer insert(Org org);

    Integer update(Org org);

    Integer delete(@Param("orgs") List<Org> orgs);

    List<Org> findList(@Param("tenantId")Integer tenantId,@Param("parentId")Integer parentId);

    List<Org> treeList(@Param("status") Integer status,@Param("tenantId") Integer tenantId);

    Org findById(Integer id);

    //查询用户所在顶级机构
    Org findFactoryByOrgId(String orgId);

    //查询该机构下的所有子机构
    List<Org> findUnderOrgById(@Param(value = "id") Integer id);

    /*
     * 根据用户id查询所属机构
     * @param userId
     * @return
     */
    List<Org> findOrgByUserId(Integer userId);

    /*
     * 判断同级机构名是否重复，重复返回1，不重复返回0
     * @param loginName
     * @return
     */
    Integer isRepeatName(Org org);

    String findParentOrgId(Integer orgId);

    /*
     * 判断删除的是否为集团
     * @param loginName
     * @return
     */
    Integer isTopOrg(@Param("orgId")Integer orgId,@Param("tenantId") Integer tenantId);

    List<Org> findByTenantId(Integer id);

    Integer updataStatusByTenantId(Integer id);
}
