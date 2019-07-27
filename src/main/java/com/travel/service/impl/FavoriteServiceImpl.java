package com.travel.service.impl;

import com.travel.bean.Favorite;
import com.travel.bean.Route;
import com.travel.dao.FavoriteDao;
import com.travel.dao.impl.FavoriteDaoImpl;
import com.travel.service.FavoriteService;

import java.util.List;

/**
 * @author He
 * @Date 2019/7/25
 * @Time 10:01
 * @Description TODO
 **/

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, Integer uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        return favorite != null;
    }

    @Override
    public void setFavorite(String rid, int uid) {
        favoriteDao.insertFavorite(Integer.parseInt(rid),uid);
    }


    @Override
    public List<Route> getMyFavoriteRoute(int uid) {
        return favoriteDao.findFavoriteRoute(uid);
    }
}
