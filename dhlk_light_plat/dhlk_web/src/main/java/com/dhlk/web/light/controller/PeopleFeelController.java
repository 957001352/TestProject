package com.dhlk.web.light.controller;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.InfoBox;
import com.dhlk.entity.light.OriginalPower;
import com.dhlk.entity.light.PeopleFeel;
import com.dhlk.entity.light.PeopleFeelInfo;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.PeopleFeelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xkliu
 * @date 2020/6/30
 */
@RestController
@RequestMapping(value = "/peopleFeel")
@Api(description = "人感管理", value = "PeopleFeelController")
public class PeopleFeelController {


    @Autowired
    private PeopleFeelService peopleFeelService;


    /**
     * 新增/修改人感
     *
     * @param peopleFeel
     * @param bindingResult
     * @return
     */
    @ApiOperation("新增/修改人感")
    @PostMapping("/save")
    public Result save(@Valid @RequestBody PeopleFeel peopleFeel, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if (result == null) {
            return peopleFeelService.save(peopleFeel);
        }
        return result;
    }


    /**
     * 人感数据查询
     *
     * @param
     * @return
     */
    @ApiOperation("人感数据查询")
    @GetMapping("/find")
    public Result findOne() {
        return peopleFeelService.findOne();
    }

    /**
     * 删除
     *
     * @param ids
     * @return result
     */
    @ApiOperation("物理删除")
    @GetMapping(value = "/delete")
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
    @ApiOperation("记忆人感")
    @GetMapping(value = "/memoryPeopleFeel")
    public Result memoryIntensity() {
        return peopleFeelService.memoryPeopleFeel();
    }

}
