package com.dhlk.light.service.impl;

import com.dhlk.light.dao.LightAreaDao;
import com.dhlk.light.entity.LightAera;
import com.dhlk.light.service.LightAreaService;
import com.dhlk.light.util.Result;
import com.dhlk.light.util.ResultEnum;
import com.dhlk.light.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Auther :yangwang
 * Data:2020/11/26
 * Time:15:07
 * @Description:
 */
@Service
public class LightAreaServiceImpl implements LightAreaService {

    @Autowired
    private LightAreaDao lightAreaDao;

    @Value("${attachment.path}")
    private String attachmentPath;
    /**
     * 根据租户id查询区域信息
     *
     * @param tenantId
     * @return
     */
    @Override
    public Result selectAreaByTenantId(Integer tenantId) {

        if (tenantId == null) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        } else {
            List<LightAera> list = lightAreaDao.selectAreaByTenantId(tenantId,attachmentPath);
            return ResultUtils.success(list);
        }
    }
}
