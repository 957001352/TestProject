package com.dhlk.light.factory.service;

import com.dhlk.entity.light.CloudPeopleFeelStatistics;
import com.dhlk.entity.light.LedPower;
import com.dhlk.entity.light.LocalPeopleFeelStatistics;

import java.util.List;

/**
 * @创建人 wangq
 * @创建时间 2020/6/16
 * @描述
 */
public interface LedPowerService {

    public Integer saveLedPower(List<LedPower> list);

    public void ledEnergyStatistics() throws Exception;

    public void delete();

    public void ledOnLineStatistics() throws Exception;

    public Integer saveLocalPeopleFeel(List<LocalPeopleFeelStatistics> list);

    public Integer saveLocalPeopleFeel(LocalPeopleFeelStatistics localPeopleFeelStatistics);

    public void peopleFeelStatistics() throws Exception;


}
