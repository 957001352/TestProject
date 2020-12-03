package com.dhlk.web.light.service.fbk;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.*;
import com.dhlk.enums.ResultEnum;
import com.dhlk.utils.ResultUtils;
import com.dhlk.web.light.service.LedService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/4
 */
@Service
public class LedServiceFbk implements LedService {

    @Override
    public Result save(Led led) {
            return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result flashingLed(InfoBox<String> infoBox) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String id) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList(String sn, String areaId,String switchId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result savePower(List<LedPower> list) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result saveOnlineList(List<LedOnline> list) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result saveOnline(LedOnline ledOnline) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result savePeopleList(String json) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result saveSwitchBoundLed(Switch swich) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result setLedBrightness(InfoBox<String> infoBox) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findAreasByLed() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result openOrCloseLed(InfoBox<String> infoBox) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result ledRestart(InfoBox<String> infoBox) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result switchRestart(InfoBox<String> infoBox) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findLedFault(String ledSn) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result<Object> findExportList(String ledSn) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findListByTenantId(Integer tenantId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result update(Led led) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result updateLocation(List<Led> leds) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findLedParamInfos(InfoBox<String> infoBox) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findLedRecord(String sn, String commond, String sendResult, String backResult, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result notAddLed() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result brightnessShow() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result syncLedBrightness(String brightness) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findLedsByArea(String areaId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result refreshParam(InfoBox<String> infoBox) {
        return ResultUtils.success();
    }

    @Override
    public Result showIconSize() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}