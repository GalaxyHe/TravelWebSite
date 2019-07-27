package com.travel.service.impl;

import com.travel.bean.PageBean;
import com.travel.bean.Route;
import com.travel.bean.RouteImg;
import com.travel.bean.Seller;
import com.travel.dao.FavoriteDao;
import com.travel.dao.RouteDao;
import com.travel.dao.RouteImgDao;
import com.travel.dao.SellerDao;
import com.travel.dao.impl.FavoriteDaoImpl;
import com.travel.dao.impl.RouteDaoImpl;
import com.travel.dao.impl.RouteImgDaoImpl;
import com.travel.dao.impl.SellerDaoImpl;
import com.travel.service.RouteService;
import java.util.List;


/**
 * @author He
 * @Date 2019/7/23
 * @Time 22:03
 * @Description TODO
 **/

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();


    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        //组装PageBean的信息
        //创PageBean对象
        PageBean<Route> pb = new PageBean<>() ;
        //设置当前页码
        pb.setCurrentPage(currentPage);

        //设置每页显示条数
        pb.setPageSize(pageSize);

        //封装总记录数(查数据库)
        int totalCount = routeDao.findTotalCount(cid,rname) ;
        //System.out.println(totalCount);
        pb.setTotalCount(totalCount);

        //封装列表的数据集合(查数据库)
        //起始条数=(当前页码-1) * 每页显示条数
        //System.out.println("==service层中的rname==="+rname);
        int start = (currentPage-1) * pageSize ;
        List<Route> list = routeDao.findByPage(cid, start, pageSize, rname);
        pb.setList(list);


        //计算总页数
        int totalPage = (totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);

        //返回
        return pb;
    }


    @Override
    public Route getOne(String rid) {
       Route route = routeDao.findOne(Integer.parseInt(rid));

        //2.根据route的id查询图片的集合信息
        List<RouteImg> routeImgList = routeImgDao.findbyRid(route.getRid());

        //3.将集合设置到route对象中
        route.setRouteImgList(routeImgList);

        //4.根据route表中的sid(商家id)获取商家信息
        Seller seller = sellerDao.findbyId(route.getSid());
        route.setSeller(seller);

        //查询收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);

        return route;
    }
}
