package com.dhlk.light.service.util;

import com.dhlk.systemconst.Const;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/6/19
 */
@Component
public class HeaderUtil {

    @Autowired
    private HttpServletRequest request;

    public Integer tenantId() {
        return Convert.stringToInteger(request.getHeader(LedConst.DHLK_TENANTID));
    }
    public String cloudToken() {
        return request.getHeader(Const.TOKEN_HEADER);
    }
}