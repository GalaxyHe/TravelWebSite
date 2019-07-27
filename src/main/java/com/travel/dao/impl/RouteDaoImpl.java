package com.travel.dao.impl;

import com.travel.bean.Route;
import com.travel.dao.RouteDao;
import com.travel.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author He
 * @Date 2019/7/23
 * @Time 22:03
 * @Description TODO
 **/

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Integer findTotalCount(int cid, String rname) {
        //String sql = "select count(*) from tab_route where cid=?";
        // 定义sql模板
        String sql = "select count(*) from tab_route where 1=1 ";
        //拼接sql

        //创建条件List集合
        List params = new ArrayList();
        //创建StringBuffer对象
        StringBuilder sb = new StringBuilder(sql);
        //判断并且给参数赋值
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }

        //此处注意，从前台传过来的时"null"字符串
       if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        //将StringBuffer转换成String
        sql = sb.toString();
        System.out.println(sql);

        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        //准备sql
        //String sql = "SELECT * FROM tab_route where  cid = ? limit ?,?" ;

        // 定义sql模板
        String sql = "select * from tab_route where 1=1 " ;
        //拼接sql

        //创建条件List集合
        List params = new ArrayList() ;
        //创建StringBuffer对象
        StringBuilder sb = new StringBuilder(sql) ;
        //判断并且给参数赋值
        if(cid != 0){
            sb.append("and cid = ? ") ;
            params.add(cid) ;
        }
        if(rname !=null && rname.length()>0 && !"null".equals(rname)){
            sb.append("and rname like ? ") ;
            params.add("%"+rname+"%") ;
        }

        sb.append(" limit ?, ?") ;
        sql = sb.toString() ;


        params.add(start) ;
        params.add(pageSize) ;


        System.out.println(sql);
        //查询
        return template.query(sql, new BeanPropertyRowMapper<>(Route.class),params.toArray()) ;
    }



    @Override
    public Route findOne(Integer rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
    }
}
