package com.travel.dao;

import com.travel.bean.RouteImg;

import java.util.List;

public interface RouteImgDao {

    //根据rid来查询图片集
    List<RouteImg> findbyRid(Integer rid);
}
