package com.dhlk.basicmodule.service.dao;
import com.dhlk.entity.basicmodule.DeskTop;
import org.springframework.stereotype.Repository;



import java.util.List;

/**
 * @创建人 wangq
 * @创建时间 2020/7/1
 * @描述
 */

@Repository
public interface DeskTopDao {

    Integer insert(List<DeskTop> list);

    Integer delete(List<String> list);

    List<DeskTop> findList(Integer userId);

    Integer deleteAllByUserId(Integer userId);
}
