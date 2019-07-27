package com.travel.dao.impl;

import com.travel.bean.User;
import com.travel.dao.UserDao;
import com.travel.utils.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author He
 * @Date 2019/7/23
 * @Time 11:07
 * @Description TODO
 **/

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public User findByUsername(String username) {
        User user = null;
        try {
            String sql = "select * from tab_user where username=?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class),username);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public void saveUser(User user) {
        //定义sql语句
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) " +
                "values(?,?,?,?,?,?,?,?,?)";
        template.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode()
        );
    }

    /**
     * 根据激活码查询对应的用户
     * @param code
     */
    @Override
    public User findByCode(String code) {
        User user = null ;
        //准备sql
        try {
            String sql = "select * from tab_user where code = ?" ;
            //查询
            user  = template.queryForObject(sql,
                    new BeanPropertyRowMapper<>(User.class), code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set status = 'Y' where uid = ?";
        template.update(sql,user.getUid());
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            String sql = "select * from tab_user where username=? and password=?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class),username,password);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return user;
    }
}
