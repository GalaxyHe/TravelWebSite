package com.travel.service;

import com.travel.bean.Route;

import java.util.List;

public interface FavoriteService {
    boolean isFavorite(String rid,Integer uid);

    void setFavorite(String rid, int uid);

    List<Route> getMyFavoriteRoute(int uid);
}
