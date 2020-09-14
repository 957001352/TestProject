package com.dhlk.web.light.service;

import com.dhlk.domain.Result;
import com.dhlk.entity.light.Question;
import com.dhlk.web.light.service.fbk.QuestionServiceFbk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xkliu
 * @date 2020/6/5
 * <p>
 * 意见反馈service
 */
@FeignClient(value = "light-service/question", fallback = QuestionServiceFbk.class)
public interface QuestionService {

    /**
     * 保存问题反馈
     *
     * @param question
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody Question question);

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    @GetMapping(value = "/delete")
    Result delete(@RequestParam(value = "ids") String ids);

    /**
     * 问题反馈列表查询
     *
     * @param sn
     * @param tenantId
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/findList")
    Result findList(@RequestParam(value = "sn", required = false) String sn,
                    @RequestParam(value = "startDate", required = false) String startDate,
                    @RequestParam(value = "endDate", required = false) String endDate,
                    @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);

    /**
     * 获取企业名称
     *
     * @return
     */
    @GetMapping(value = "/getCompanyList")
    Result getCompanyList();

    /**
     * 意见反馈详情查询
     *
     * @return
     */
    @GetMapping(value = "/findQuestionDetail")
    Result findQuestionDetail(@RequestParam(value = "id") String id);
}
