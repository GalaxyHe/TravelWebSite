package com.travel.dao;

import com.travel.bean.Route;

import java.util.List;

public interface RouteDao {
   //根据cid查询总记录数
    Integer findTotalCount(int cid, String rname);


    //根据cid,start,pagesize查询当前页的集合
    List<Route> findByPage(int cid,int start,int pageSize,String rname);

    //查询单个旅游路线对象
    Route findOne(Integer rid);


}
