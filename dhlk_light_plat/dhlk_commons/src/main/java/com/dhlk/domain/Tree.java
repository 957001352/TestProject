package com.dhlk.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
* @Description:    树结构的实体
* @Author:         gchen
* @CreateDate:     2020/4/7 17:12
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tree<T> {

    private String id;

    private String title;//名称

    private String component;

    private List<Tree<T>> children;//子集

    private String parentId;//父级id

    private Boolean checked;

    private boolean hasParent = false;//是否有父级

    private boolean hasChildren = false;//是否有子集

    private Integer staffNum;//机构内的人数


    public void initChildren(){
        this.children = new ArrayList<>();
    }

}
