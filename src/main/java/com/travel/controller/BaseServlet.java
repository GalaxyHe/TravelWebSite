package com.travel.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author He
 * @Date 2019/7/23
 * @Time 19:35
 * @Description TODO
 **/

public class BaseServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        //   travle/user/add  :获取访问子类Servlet的uri的路径
        String uri = request.getRequestURI();
        System.out.println(uri);
        // 获取uri中的方法名add   travel/user/add
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);//start end
        System.out.println(methodName);

        //1 .获取子类对象
        Class cls = this.getClass();
        //System.out.println(cls);
        //2.调用子类中的方法
        try {
            //暴力反射(获取子类私有的/受保护的方法)
            //忽略访问权限修饰符，获取方法
            //Method method = cls.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            Method method = cls.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //调用方法
            /**
             * 为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查
             */
            //(调用子类的方法,proptected修饰)
            //method.setAccessible(true);
            method.invoke(this, request, response);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * 直接将obj对象转换成json格式,写回到前台
     *
     * @param obj
     * @param response
     * @throws IOException
     */
    void writeValue(Object obj, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        //处理服务器的响应的格式:json格式
        response.setContentType("application/json;charset=utf-8");
        //写回
        mapper.writeValue(response.getOutputStream(), obj);
    }

    /**
     * 将当前对象解析json串返回浏览器
     *
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(obj);
        return json;
    }
}
