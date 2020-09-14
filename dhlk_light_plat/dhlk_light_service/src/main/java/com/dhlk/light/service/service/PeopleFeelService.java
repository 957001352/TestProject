package com.dhlk.light.service.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.PeopleFeel;
import com.dhlk.entity.light.PeopleFeelInfo;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xkliu
 * @date 2020/6/30
 *
 * 人感service
 */
public interface PeopleFeelService {


    /**
     * 保存
     *
     * @param
     * @return
     */
    Result save(@RequestBody PeopleFeel peopleFeel);

    /**
     * 单条查询
     */
    Result findOne();

    /**
     * 物理删除
     *
     * @param ids
     * @return
     */
    Result delete(String ids);

    /**
     * 人感控制
     */
    Result peopleFeelContro(InfoBox<PeopleFeelInfo> infoBox);

    /**
     * 记忆人感数据
     * @return
     */
    Result memoryPeopleFeel();
}
