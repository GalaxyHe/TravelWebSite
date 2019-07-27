package com.travel.service.impl;

import com.travel.bean.Category;
import com.travel.dao.CategoryDao;
import com.travel.dao.impl.CategoryDaoImpl;
import com.travel.service.CategoryService;
import com.travel.utils.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author He
 * @Date 2019/7/23
 * @Time 20:25
 * @Description TODO
 **/

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> getAll() {
        //1.从redis中获取
        Jedis jedis = JedisUtil.getJedis();
        jedis.auth("hg123456");
        //查询sortedset中的分数(cid)和值(cname)
        Set<Tuple> categories = jedis.zrangeWithScores("category", 0, -1);
        List<Category> categoryList;
        //2.判断查询的集合是否为空
        //3.如果为null，需要从MySQL中查询,再将其存入redis数据库中
        if(categories == null||categories.size() == 0){
            //从MySQL中查询
            System.out.println("从MySQL中查询");
            categoryList = categoryDao.findAll();

            //将集合数据保存到redis数据库中
            for (Category category : categoryList) {
                jedis.zadd("category", category.getCid(), category.getCname());
            }
        }else{
            //将set中的数据存入list集合中
            categoryList = new ArrayList<>();
            for (Tuple tuple:categories) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                categoryList.add(category);
            }
        }

        return categoryList;
    }
}
