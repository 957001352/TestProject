package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.Menu;
import com.dhlk.entity.basicmodule.Permissions;
import com.dhlk.entity.basicmodule.TenantMenu;
import com.dhlk.entity.basicmodule.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface MenuDao {

    Integer insert(Menu menu);

    Integer update(Menu menu);

    Integer delete(Integer id);

    Menu selectMenuById(Integer id);

    Menu selectMenuByCode(String code);

    Integer deleteMenuByIds(List<String> ids);

    List<Menu> findList(Integer parentId, String code, String name, String url, Integer status);

    Integer isEnable(@Param("id") Integer id, @Param("status") Integer status);

    /*
     * 判断菜单code是否重复
     */
    Integer isRepeatCode(Menu menu);


    List<Menu> findTreeByUserId(Integer userId);

    List<Menu> getMenuCheckedList(@Param("tenantId") Integer tenantId,@Param("roleId") String roleId);
    //保存租户拥有的菜单
    Integer insertTenantAndMenu(List<TenantMenu> tenantMenu);

    /**
     * 删除租户拥有的菜单
     * @param tenantId
     * @return
     */
    public Integer deleteByTenantId(Integer tenantId);

    List<Menu> getMenuCheckedListByTeanantId(Integer tenantId);

}
