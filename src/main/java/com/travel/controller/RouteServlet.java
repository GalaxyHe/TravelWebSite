package com.travel.controller;

import com.travel.bean.PageBean;
import com.travel.bean.Route;
import com.travel.bean.User;
import com.travel.service.FavoriteService;
import com.travel.service.RouteService;
import com.travel.service.impl.FavoriteServiceImpl;
import com.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author He
 * @Date 2019/7/23
 * @Time 22:04
 * @Description TODO
 **/


@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * @author He
     * @Description 分页查询
     * @Date 22:05 2019/7/23
     * @Param HttpServletRequest req, HttpServletResponse resp
     **/
    public void pageQuery(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currentPageStr = req.getParameter("currentPage");
        String pageSizeStr = req.getParameter("currentSize");
        String cidStr = req.getParameter("cid");
        String rname = req.getParameter("rname");



        int cid = 0;
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }

        //当前的页码，默认为第1页
        int currentPage;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }

        //每页显示的条数，默认每页显示5条记录
        int pageSize;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }


        //3.调用Service完成后台封装PageBean
        //将PageBean对象的总记录数/总记录数/当前页码封装完成
        PageBean<Route> pb;
        pb = routeService.pageQuery(cid, currentPage, pageSize, rname);


        //4.将pageBean对象直接返回给前台页面,发送json对象
        writeValue(pb,resp);
    }

    /**
     * @author He
     * @Description 获取某条旅游线路的具体信息
     * @Date 22:37 2019/7/24
     * @Param HttpServletRequest req,HttpServletResponse resp
     **/
    public void findOne(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //1.接收id
        String rid = req.getParameter("rid");
        //2.调用service层查询route对象
        Route route = routeService.getOne(rid);
        //3.转化为Json返回客户端
        writeValue(route, resp);

    }


    /**
     * @return
     * @author He
     * @Description 判断当前登录用户是否收藏过某条线路
     * @Date 9:45 2019/7/25
     * @Param
     **/
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取线路id
        String rid = request.getParameter("rid");

        //2.获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        //如果用户尚未登录，则user为null，将uid设置为0，数据库中没有记录
        if (user == null) {
            uid = 0;
        } else {
            uid = user.getUid();
        }

        //调用service层查询是否收藏
        boolean flag = favoriteService.isFavorite(rid, uid);

        //写回客户端
        writeValue(flag,response);
    }

    /**
      * @author He
      * @Description 用户添加收藏
      * @Date 10:32 2019/7/25
      * @Param
      * @return
      **/
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) {
        //获取rid和uid
        String rid = request.getParameter("rid");

        //2.获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        //如果用户尚未登录，则user为null，将uid设置为0，数据库中没有记录
        if (user == null) {
           return;
        } else {
            uid = user.getUid();
        }

        //调用service进行添加的操作
        favoriteService.setFavorite(rid,uid);

    }


    /**
      * @author He
      * @Description 展示用户的个人收藏
      * @Date 11:45 2019/7/27
      * @Param
      * @return
      **/

    public void showuserFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //从session中取到当前登录用户
        User user = (User) request.getSession().getAttribute("user");
        int uid;

        //判断用户是否登录
        if(user == null){
            return;
        }else {
            uid = user.getUid();
        }

        List<Route> routeList = favoriteService.getMyFavoriteRoute(uid);
        writeValue(routeList,response);
    }
}
