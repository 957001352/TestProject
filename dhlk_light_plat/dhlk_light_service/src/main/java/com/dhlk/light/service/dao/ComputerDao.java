package com.dhlk.light.service.dao;

import com.dhlk.entity.light.Computer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComputerDao {
    Integer insert(Computer computer);

    Integer update(Computer computer);

    Integer delete(@Param("ids") String[] ids);

    List<Computer> findList(@Param("name") String name, @Param("tenantId") Integer tenantId);

    Computer findById(Integer id);

    /**
     * 检查sn是否重复
     *
     * @param computer
     * @return
     */
    Integer isRepeatSn(Computer computer);

    /**
     * 检查Ip是否重复
     *
     * @param computer
     * @return
     */
    Integer isRepeatIp(Computer computer);

    /**
     * 检查名称是否重复
     * @param computer
     * @return
     */
    Integer isRepeatName(Computer computer);

    /**
     * 检查一体机是否存在
     * @param id
     * @return
     */
    Integer isExist(Integer id);
}
