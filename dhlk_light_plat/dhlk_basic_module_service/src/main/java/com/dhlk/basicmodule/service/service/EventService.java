package com.dhlk.basicmodule.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.Event;

import java.util.List;

/**
 * 事件Service接口
 *
 * @author jlv
 * @date 2020-04-26
 */
public interface EventService {
    public Result getAlarms(Integer deviceId, String searchStatus, String status, int limit, Long startTime, Long endTime, boolean ascOrder, String offset, Boolean fetchOriginator) throws Exception;
    /**
     * 查询事件
     *
     * @param id 事件ID
     * @return 事件
     */
    public Result selectEventById(Long id);

    /**
     * 查询事件列表
     *
     *
     * @return 事件集合
     */
    public Result selectEventList(String tbId, String searchStatus, Long startTime, Long endTime);

    /**
     * 新增事件
     *
     * @param event 事件
     * @return 结果
     */
    public Result insertEvent(Event event);

    /**
     * 修改事件
     *
     * @param event 事件
     * @return 结果
     */
    public Result updateEvent(Event event);

    /**
     * 批量删除事件
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public Result deleteEventByIds(String ids);

    /**
     * 删除事件信息
     *
     * @param id 事件ID
     * @return 结果
     */
    public Result deleteEventById(Long id);

}
