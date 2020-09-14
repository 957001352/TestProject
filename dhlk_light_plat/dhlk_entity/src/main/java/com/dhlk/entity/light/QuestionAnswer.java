package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xkliu
 * @date 2020/6/4
 * <p>
 * 问题解决方案实体类
 */
@Data
@ApiModel(value = "questionAnswer", description = "问题解决方案")
public class QuestionAnswer implements Serializable {

    /**
     * Id
     */
    @ApiModelProperty(value = "新增为空/修改传值", hidden = true)
    private String id;

    /**
     * 解决方案
     */
    @ApiModelProperty(value = "解决方案")
    private String answer;

    /**
     * 处理人
     */
    @ApiModelProperty(value = "处理人", hidden = true)
    private Integer dealUser;

    /**
     * 处理时间
     */
    @ApiModelProperty(value = "处理时间", hidden = true)
    private String dealTime;

    /**
     * 意见反馈Id
     */
    @ApiModelProperty(value = "意见反馈Id")
    private String questionId;

    /**
     * 处理人名字
     */
    @ApiModelProperty(value = "处理人名字",hidden = true)
    private String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getDealUser() {
        return dealUser;
    }

    public void setDealUser(Integer dealUser) {
        this.dealUser = dealUser;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
