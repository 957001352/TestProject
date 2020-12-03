package com.dhlk.light.service.impl;

import com.dhlk.light.dao.KMeansLightDao;
import com.dhlk.light.dao.LightLocationDao;
import com.dhlk.light.entity.InfoBox;
import com.dhlk.light.entity.KMeansLight;
import com.dhlk.light.entity.LighgtProportion;
import com.dhlk.light.entity.LightLocation;
import com.dhlk.light.service.KMeansLightService;
import com.dhlk.light.util.Result;
import com.dhlk.light.util.ResultEnum;
import com.dhlk.light.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/23
 * Time:10:18
 * @Description:
 */
@Service
public class KMeansLightServiceImpl implements KMeansLightService {

    @Autowired
    private KMeansLightDao kMeansLightDao;

    /**
     * 查询某个租户下聚类之后灯的信息
     *
     * @param factoryCode 租户
     * @return
     */
    @Override
    public Result findAll(String factoryCode) {

        if (factoryCode == null) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }

        List<KMeansLight> list = kMeansLightDao.findList(factoryCode);

        InfoBox infoBox = new InfoBox();

        for (KMeansLight kMeansLight : list) {
            String str = kMeansLight.getClusterOne();
            str = str.replaceAll("\\[", " ");
            str = str.replaceAll("]", " ");
            String[] one = str.split(" , ");
            for (int i = 0; i < one.length; i++) {
                one[i] = one[i].trim().replaceAll(" ", "");
            }
            infoBox.setClusterOne(one);
            System.out.println(one.length);

            String str2 = kMeansLight.getClusterTwo();
            str2 = str2.replaceAll("\\[", " ");
            str2 = str2.replaceAll("]", " ");
            String[] two = str2.split(" , ");
            System.out.println(two.length);

            for (int i = 0; i < two.length; i++) {
                two[i] = two[i].trim().replaceAll(" ", "");
            }
            infoBox.setClusterTwo(two);
            String str3 = kMeansLight.getClusterThree();
            str3 = str3.replaceAll("\\[", " ");
            str3 = str3.replaceAll("]", " ");
            String[] three = str3.split(" , ");
            System.out.println(three.length);
            for (int i = 0; i < three.length; i++) {
                three[i] = three[i].trim().replaceAll(" ", "");
            }
            infoBox.setClusterThree(three);
        }
        return ResultUtils.success(infoBox);
    }

    /**
     * 显示每类灯的占比
     *
     * @param factoryCode
     * @return
     */
    public Result lightProportion(String factoryCode) {

        List<KMeansLight> list = kMeansLightDao.findList(factoryCode);

        LighgtProportion lighgtProportion = new LighgtProportion();

        DecimalFormat decimalFormat = new DecimalFormat("#.0000");

        KMeansLight kMeansLight = list.get(0);

        String str = kMeansLight.getClusterOne();
        str = str.replaceAll("\\[", " ");
        str = str.replaceAll("]", " ");
        String[] one = str.split(" , ");

        String str2 = kMeansLight.getClusterTwo();
        str2 = str2.replaceAll("\\[", " ");
        str2 = str2.replaceAll("]", " ");
        String[] two = str2.split(" , ");

        String str3 = kMeansLight.getClusterThree();
        str3 = str3.replaceAll("\\[", " ");
        str3 = str3.replaceAll("]", " ");
        String[] three = str3.split(" , ");

        double goodCount = one.length;
        double generalCount = two.length;
        double dangerCount = three.length;

        lighgtProportion.setGoodCount((int) goodCount);
        lighgtProportion.setGeneralCount((int) generalCount);
        lighgtProportion.setDangerCount((int) dangerCount);
        double total = goodCount + generalCount + dangerCount;

        double good = goodCount / total;
        lighgtProportion.setGood(Double.parseDouble(decimalFormat.format(good)));

        double general = generalCount / total;
        lighgtProportion.setGeneral(Double.parseDouble(decimalFormat.format(general)));

        double danger = dangerCount / total;
        lighgtProportion.setDnager(Double.parseDouble(decimalFormat.format(danger)));

        return ResultUtils.success(lighgtProportion);
    }

    /**
     * 重点关注的灯的区域信息
     *
     * @param factoryCode
     * @return
     */
    @Override
    public Result focusSnAndArea(String factoryCode) {
        List<KMeansLight> list = kMeansLightDao.findList(factoryCode);
        List<LightLocation> lightLocation = new ArrayList<>();
        if (list.size()>0) {
            KMeansLight kMeansLight = list.get(0);

            String str3 = kMeansLight.getClusterThree();
            str3 = str3.replaceAll("\\[", " ");
            str3 = str3.replaceAll("]", " ");

            String[] three = str3.split(" , ");
            for (int i = 0; i < three.length; i++) {
                three[i] = three[i].trim().replaceAll(" ", "");
            }

            for (String s : three) {
                String[] split = s.split(",");
                LightLocation area = kMeansLightDao.selectAreaBySn(split[2]);
                lightLocation.add(area);
            }
        }
        return ResultUtils.success(lightLocation);
    }
}