package com.travel.dao.impl;

import com.travel.bean.Favorite;
import com.travel.bean.Route;
import com.travel.dao.FavoriteDao;
import com.travel.utils.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

/**
 * @author He
 * @Date 2019/7/25
 * @Time 10:00
 * @Description TODO
 **/

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public Integer findCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid=? ";
        return template.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public void insertFavorite(Integer rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        template.update(sql,rid,new Date(),uid);
    }

    /**
      * @author He
      * @Description 根据rid和uid查询收藏信息
      * @Date 10:04 2019/7/25
      * @Param  Integer rid-收藏的旅游线路的id  uid-当前用户的id
      * @return
      **/
    @Override
    public Favorite findByRidAndUid(Integer rid, Integer uid) {
        Favorite favorite = null;
        try {
            String sql = "select * from tab_favorite where rid=? and uid=? ";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return favorite;
    }

    @Override
    public List<Route> findFavoriteRoute(int uid) {
        String sql = "SELECT * FROM tab_route WHERE tab_route.rid IN ( SELECT rid FROM tab_favorite WHERE uid = ?)";
        return template.query(sql, new BeanPropertyRowMapper<>(Route.class), uid);
    }
}
