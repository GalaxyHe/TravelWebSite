package com.travel.dao.impl;

import com.travel.bean.Seller;
import com.travel.dao.SellerDao;
import com.travel.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author He
 * @Date 2019/7/24
 * @Time 14:26
 * @Description TODO
 **/

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Seller findbyId(Integer sid) {
        String sql = "select * from tab_seller where sid = ? ";

        return template.queryForObject(sql,new BeanPropertyRowMapper<>(Seller.class),sid);
    }
}
