package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.DataBroker;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description 接口管理
 * @Author lpsong
 * @Date 2020/3/11
 */
@Repository
public interface DataBrokerDao {

    Integer insert(DataBroker dataBorker);

    Integer update(DataBroker dataBorker);

    Integer delete(List<String> ids);

    List<DataBroker> findList();

}