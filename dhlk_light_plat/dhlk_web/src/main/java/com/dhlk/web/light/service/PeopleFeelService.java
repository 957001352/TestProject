package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.PeopleFeel;
import com.dhlk.entity.light.PeopleFeelInfo;
import com.dhlk.web.light.service.fbk.PeopleFeelServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xkliu
 * @date 2020/6/30
 * <p>
 * 人感service
 */
@FeignClient(value = "light-service/peopleFeel", fallback = PeopleFeelServiceFbk.class)
public interface PeopleFeelService {

    /**
     * 保存
     *
     * @param peopleFeel
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody PeopleFeel peopleFeel);

    /**
     * 查询一条
     */
    @GetMapping("/findOne")
    Result findOne();

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    Result delete(@RequestParam(value = "ids") String ids);

    /**
     * 人感控制
     * @return
     */
    @PostMapping(value = "/peopleFeelingContro")
    Result peopleFeelContro(@RequestBody InfoBox<PeopleFeelInfo> infoBox);

    @GetMapping(value = "/memoryPeopleFeel")
    Result memoryPeopleFeel();
}
