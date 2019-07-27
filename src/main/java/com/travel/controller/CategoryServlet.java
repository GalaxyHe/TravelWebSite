package com.travel.controller;

import com.travel.bean.Category;
import com.travel.service.CategoryService;
import com.travel.service.impl.CategoryServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author He
 * @Date 2019/7/23
 * @Time 20:22
 * @Description TODO
 **/
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    private CategoryService categoryService = new CategoryServiceImpl();

    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        List<Category> categoryList = categoryService.getAll();

        //序列化json返回
        writeValue(categoryList,resp);

    }
}
