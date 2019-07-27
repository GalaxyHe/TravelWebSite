package com.travel.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.bean.ResultInfo;
import com.travel.bean.User;
import com.travel.service.impl.UserServiceImpl;
import com.travel.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/*
 * @author He
 * @Date 2019/7/23
 * @Time 19:34
 * @Description TODO
 **/
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    /**
     * 注册功能
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        //加入验证码的操作
        //获取前台页面中的验证码
        String check = request.getParameter("check");
        //从session域中获取服务器端存储随机的验证码
        String  checkcode_server = (String) request.getSession().
                getAttribute("CHECKCODE_SERVER");
        //一次性验证,防止验证码被复用

        request.getSession().removeAttribute("CHECKCODE_SERVER");
        //如果验证码不一致,后面代码不执行了
        //防止nullPointerException
        if(checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)){

            //验证码存在问题
            //创建响应结果对象
            ResultInfo resultInfo = new ResultInfo() ;
            //设置响应数
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码存在问题!!");
            //创建json的解析对象 (jackson的jar包核心类)
            ObjectMapper mapper = new ObjectMapper() ;
            String json = mapper.writeValueAsString(resultInfo);
            //设置服务器响应格式 :json类型
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            //不执行了
            return ;
        }


        //1.获取参数数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装User对象
        User user = new User() ;
        //使用commons-beanutils工具类 :封装Javabean
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.调用Service,查询是否存在该用户
        UserService userService = new UserServiceImpl() ;
        boolean flag = userService.setUser(user) ;
        //创建响应结果对象:ResultInfo
        ResultInfo resultInfo = new ResultInfo() ;
        //判断用户注册是否成功
        if(flag){
            //注册成功了
            resultInfo.setFlag(true);
        }else{
            //注册失败
            resultInfo.setFlag(false);
            //提示信息
            resultInfo.setErrorMsg("注册失败!!!");
        }
        //4.服务器将数据响应到前台,(resultInfo)
        //json:jsonLib /Gson/fastJson
        //创建json的解析对象 (jackson的jar包核心类)
        ObjectMapper mapper = new ObjectMapper() ;
        String json = mapper.writeValueAsString(resultInfo);
        //设置服务器响应格式 :json类型
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
}

    /**
     * 用户激活
     *
     * @param request
     * @param response
     * @throws IOException
     */

    public void active(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        //1.接收激活码
        String code = request.getParameter("code");
        //2.判断激活码是否为空
        if (code != null) {
            //存在.调用service完成激活功能
            UserService userService = new UserServiceImpl();
            boolean flag = userService.active(code);

            //3.提示信息 (直接向浏览器响应内容)
            String msg;
            if (flag) {
                //激活成功了
                msg = "您已经激活成功了,请<a href='http://localhost:8080/travelsite/login.html'>登录</a>";
                //http://localhost/travel/login.html
            } else {
                //激活失败
                msg = "激活失败,请联系管理人员";
            }
            //设置中文乱码
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);

        }

    }


    /**
     * 用户登录功能
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        //验证码逻辑同注册逻辑
        //1.接收前台参数:username,password
        Map<String, String[]> map = request.getParameterMap();
        //2.封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用service层完成查询用户
        UserService userService = new UserServiceImpl();
        User u = userService.findByUsernameAndPassword(user);
        //创建响应结果对象
        ResultInfo info = new ResultInfo();
        //4.判断查询的u是否存在
        if (u == null) {
            //不存在,提示 用户名或者密码不匹配
            info.setFlag(false);
            info.setErrorMsg("用户名或者密码不匹配!!");
        }
        //如果用户存在,但是没有被激活
        if (u != null && !"Y".equals(u.getStatus())) {
            //尚未激活
            info.setFlag(false);
            info.setErrorMsg("您当前用户名尚未激活!!");
        }
        //判断登录成功
        if (u != null && "Y".equals(u.getStatus())) {
            //应该将u存储到session域中
            request.getSession().setAttribute("user", u);
            info.setFlag(true);
        }

        /*//将当前响应对象解析成json串发送前台
        ObjectMapper mapper = new ObjectMapper();
        //writerValueAsString(mapper,响应结果对象)
        //writerValue(字节输出流,响应结果对象)
        //需要设置响应格式:支持json格式
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);*/
        writeValue(info,response);

    }


    /**
     * 查询用户
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void findUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //1.直接从session域中获取用户对象
        Object user = request.getSession().getAttribute("user");
        writeValue(user, response);
    }


    /**
     * 用户退出功能
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        //获取session对象,手动删除sesison
        request.getSession().invalidate();

        //重定向到login.html
        //http://localhost/travle/login.html
        response.sendRedirect(request.getContextPath() + "/login.html");

    }

}
