package com.dhlk.entity.light;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 * @author xkliu
 * @date 2020/6/4
 * <p>
 * 意见反馈实体类
 */
@Data
@ApiModel(value = "question", description = "意见反馈")
public class Question implements Serializable {

    /**
     * Id
     */
    @ApiModelProperty(value = "新增为空/修改传值", hidden = true)
    private String id;

    /**
     * 问题编号
     */
    @ApiModelProperty(value = "问题编号",hidden = true)
    private String sn;

    /**
     * 问题概述
     */
    @ApiModelProperty(value = "问题概述")
    private String title;

    /**
     * 问题描述
     */
    @ApiModelProperty(value = "问题描述")
    private String content;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String linkMan;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /**
     * 添加人
     */
    @ApiModelProperty(value = "添加人",hidden = true)
    private Integer createUser;

    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间", hidden = true)
    private String createTime;

    /**
     * 状态  0正常 2 删除
     */
    @ApiModelProperty(value = "状态",hidden = true)
    private Integer status;

    /**
     * 租户对象Id
     */
    @ApiModelProperty(value = "租户对象Id", hidden = true)
    private Integer tenantId;

    /**
     * 租户名称
     */
    @ApiModelProperty(value = "租户名称", hidden = true)
    private String name;

    /**
     * 附件Id
     */
    @ApiModelProperty(value = "附件Id")
    private String fileId;

    /**
     * 附件路径
     */
    @ApiModelProperty(value = "附件路径",hidden = true)
    private String filePath;

    /**
     * 附件后缀
     */
    @ApiModelProperty(value = "附件后缀",hidden = true)
    private String suffix;

    /**
     * 附件名称
     */
    @ApiModelProperty(value = "附件名称",hidden = true)
    private String fileName;

    @ApiModelProperty(hidden = true)
    private List<QuestionAnswer> questionAnswer;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public List<QuestionAnswer> getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(List<QuestionAnswer> questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
