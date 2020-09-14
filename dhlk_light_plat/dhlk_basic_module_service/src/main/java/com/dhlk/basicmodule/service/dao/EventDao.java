package com.dhlk.basicmodule.service.dao;


import com.dhlk.entity.basicmodule.Event;
import com.dhlk.entity.tb.event.TbEvent;

import java.util.List;

/**
 * 事件Mapper接口
 * 
 * @author jlv
 * @date 2020-04-26
 */
public interface EventDao
{
    /**
     * 查询事件
     * 
     * @param id 事件ID
     * @return 事件
     */
    public Event selectEventById(Long id);

    /**
     * 查询事件列表
     * 
     *
     * @return 事件集合
     */
    public List<Event> selectEventList(String severity, String status, Long startts, Long endts, String tbId, String name);

    /**
     * 新增事件
     * 
     * @param event 事件
     * @return 结果
     */
    public int insertEvent(Event event);

    /**
     * 修改事件
     * 
     * @param event 事件
     * @return 结果
     */
    public int updateEvent(Event event);

    /**
     * 删除事件
     * 
     * @param id 事件ID
     * @return 结果
     */
    public int deleteEventById(Long id);

    /**
     * 批量删除事件
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteEventByIds(String[] ids);
}
