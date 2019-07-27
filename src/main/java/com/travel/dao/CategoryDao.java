package com.travel.dao;

import com.travel.bean.Category;

import java.util.List;

public interface CategoryDao {

    List<Category> findAll();
}
