package com.travel.service;

import com.travel.bean.PageBean;
import com.travel.bean.Route;

public interface RouteService {
    PageBean<Route> pageQuery(int cid,int currentPage,int pageSize,String rname);

    Route getOne(String rid);

}
