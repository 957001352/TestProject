package com.dhlk.light.service.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.PeopleFeel;
import com.dhlk.entity.light.PeopleFeelInfo;
import com.dhlk.light.service.service.PeopleFeelService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xkliu
 * @date 2020/6/30
 * <p>
 * 人感配置控制器
 */
@RestController
@RequestMapping(value = "/peopleFeel")
@Api(description = "人感配置", value = "PeopleFeelController")
public class PeopleFeelController {

    @Autowired
    private PeopleFeelService peopleFeelService;

    /**
     * 保存
     *
     * @param peopleFeel
     * @return
     */
    @PostMapping("/save")
    //@RequiresPermissions("peopleFeel:save")
    public Result save(@RequestBody PeopleFeel peopleFeel) {
        return peopleFeelService.save(peopleFeel);
    }


    /**
     * 人感数据查询
     *
     * @param
     * @return
     */
    @GetMapping("/findOne")
    //@RequiresAuthentication
    public Result findOne() {
        return peopleFeelService.findOne();
    }

    /**
     * 删除
     *
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
   // @RequiresAuthentication
    public Result delete(@RequestParam(value = "ids") String ids) {
        return peopleFeelService.delete(ids);
    }

    /**
     * 人感控制
     * @return
     */
    @PostMapping(value = "/peopleFeelingContro")
//    @RequiresAuthentication
    public Result peopleFeelContro(@RequestBody InfoBox<PeopleFeelInfo> infoBox) {
        return peopleFeelService.peopleFeelContro(infoBox);
    }


    /**
     *记忆人感
     */
    @GetMapping(value = "/memoryPeopleFeel")
    public Result memoryIntensity() {
        return peopleFeelService.memoryPeopleFeel();
    }
}
