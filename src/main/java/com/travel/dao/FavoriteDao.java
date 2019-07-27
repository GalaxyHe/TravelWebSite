package com.travel.dao;

import com.travel.bean.Favorite;
import com.travel.bean.Route;

import java.util.List;

public interface FavoriteDao {

    /**
     * @author He
     * @Description 根据rid和uid查询收藏信息
     * @Date 10:04 2019/7/25
     * @Param  Integer rid-收藏的旅游线路的id  uid-当前用户的id
     * @return
     **/
    Favorite findByRidAndUid(Integer rid,Integer uid);

    /**
      * @author He
      * @Description 根据线路id查询收藏次数
      * @Date 10:25 2019/7/25
      * @Param
      * @return
      **/
    Integer findCountByRid(int rid);



    /**
      * @author He
      * @Description 向数据库中插入一条收藏信息
      * @Date 10:38 2019/7/25
      * @Param
      * @return
      **/
    void insertFavorite(Integer rid, int uid);

    /**
      * @author He
      * @Description 通过uid查询用户收藏的旅游线路
      * @Date 12:33 2019/7/27
      * @Param
      * @return
      **/
    List<Route> findFavoriteRoute(int uid);
}
