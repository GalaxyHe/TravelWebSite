package com.travel.Test;

import com.travel.bean.Category;
import com.travel.bean.PageBean;
import com.travel.bean.Route;
import com.travel.bean.User;
import com.travel.dao.impl.UserDaoImpl;
import com.travel.service.CategoryService;
import com.travel.service.FavoriteService;
import com.travel.service.RouteService;
import com.travel.service.impl.CategoryServiceImpl;
import com.travel.service.impl.FavoriteServiceImpl;
import com.travel.service.impl.RouteServiceImpl;
import org.junit.Test;

import java.util.List;

/**
 * @author He
 * @Date 2019/7/24
 * @Time 16:32
 * @Description TODO
 **/

public class TestDB {
    @Test
    public void test(){
        FavoriteService favoriteService = new FavoriteServiceImpl();
        List<Route> routeList = favoriteService.getMyFavoriteRoute(13);
        for(Route r:routeList){
            System.out.println(r);
        }
    }
}
