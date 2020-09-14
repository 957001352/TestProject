package com.dhlk.light.service.util;
/**
 * @Description 定时任务传入mqtt结果
 * @Author lpsong
 * @Date 2020/6/8
 */
public class SchedulerResult {
    private String topic;//主题
    private Integer schedulerId; //任务id
    private Integer status; //任务状态
    private String cloudToken; //访问令牌

    @Override
    public String toString() {
        return "SchedulerResult{" +
                "topic='" + topic + '\'' +
                ", schedulerId=" + schedulerId +
                ", status=" + status +
                ", cloudToken='" + cloudToken + '\'' +
                '}';
    }

    public SchedulerResult() {
    }

    public SchedulerResult(String topic, Integer schedulerId, Integer status,String cloudToken) {
        this.topic = topic;
        this.schedulerId = schedulerId;
        this.status = status;
        this.cloudToken = cloudToken;
    }

    public String getToken() {
        return cloudToken;
    }

    public void setToken(String cloudToken) {
        this.cloudToken = cloudToken;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getSchedulerId() {
        return schedulerId;
    }

    public void setSchedulerId(Integer schedulerId) {
        this.schedulerId = schedulerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
