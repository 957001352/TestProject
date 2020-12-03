package com.dhlk.light.dao;

import com.dhlk.light.entity.KMeansLight;
import com.dhlk.light.entity.LightLocation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/23
 * Time:10:16
 * @Description:
 */
@Repository
public interface KMeansLightDao {
    List<KMeansLight> findList(String facToryCode);

    List<String> selectFactoryCode();

    LightLocation selectAreaBySn(String sn);
}
