package com.dhlk.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 分页工具类
 * @param <T>
 */
@ApiModel(value = "pageBean", description = "分页工具类")
public class PageBean<T> {
    @ApiModelProperty(value = "开始行")
    private int offset = 0;
    private int page;
    private int rows;
    private int limit = 10; //每页行数
    private int pageNow = 1; //当前页
    private int count; //总行数
    private int pageCount; //总页数

    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageCount() {
        return (count+limit-1)/limit;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
        this.pageNow = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
        this.limit = rows;
    }

    public int getOffset() {
        return (pageNow-1)*limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPageNow() {
        return pageNow;
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
