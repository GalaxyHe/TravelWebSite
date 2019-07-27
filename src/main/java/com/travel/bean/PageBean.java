package com.travel.bean;

import java.util.List;

/**
 * 分页实体类(自定义的分页组件)
 */

//mybatis--->分页插件
    //前端:boostrap:分页组件
public class PageBean<T> {

    private int totalCount ; //总纪录数
    private int totalPage ;//总页数
    private int currentPage ;//当前页码
    private int pageSize ;//每页显示条数

    //当前页中的具体列表数据集合
    private List<T>  list ;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", list=" + list +
                '}';
    }
}
