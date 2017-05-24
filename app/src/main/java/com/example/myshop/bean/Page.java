package com.example.myshop.bean;

import java.util.List;

/**
 * Created by 刘博良 on 2017/4/22.
 */

public class Page {
    private  int currentPage;
    private  int pageSize;
    private  int totalPage;
    private  int totalCount;

    private List<Wares> list;

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setList(List<Wares> list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<Wares> getList() {
        return list;
    }
}
