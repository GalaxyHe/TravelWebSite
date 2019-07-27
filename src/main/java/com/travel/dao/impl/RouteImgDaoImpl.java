package com.travel.dao.impl;

import com.travel.bean.RouteImg;
import com.travel.dao.RouteImgDao;
import com.travel.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author He
 * @Date 2019/7/24
 * @Time 14:19
 * @Description TODO
 **/

public class RouteImgDaoImpl implements RouteImgDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<RouteImg> findbyRid(Integer rid) {
        String sql = "select * from tab_route_img where rid = ? ";
        return template.query(sql,new BeanPropertyRowMapper<>(RouteImg.class),rid);
    }
}
