package com.travel.service.impl;

import com.travel.bean.User;
import com.travel.dao.UserDao;
import com.travel.dao.impl.UserDaoImpl;
import com.travel.service.UserService;
import com.travel.utils.MailUtils;
import com.travel.utils.UuidUtil;

/**
 * @author He
 * @Date 2019/7/23
 * @Time 11:09
 * @Description TODO
 **/

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();


    //用户注册
    @Override
    public boolean setUser(User user) {

        User u = userDao.findByUsername(user.getUsername());
        if(u != null){
            //用户名已经存在，注册失败
            return false;
        }


        //设置激活码，唯一字符串
        user.setCode(UuidUtil.getUuid().substring(0,5));
        //设置激活状态
        user.setStatus("N");

        //不存在则保存用户
        userDao.saveUser(user);

        //注册成功了,激活邮件
        //发送邮件
        //收件人(邮箱)  邮件内容  邮件标题
        //public static boolean sendMail(String to, String text, String title)
        //邮件的内容
        String content = "您的邮箱尚未激活,请<a href='http://localhost:8080/travelsite/user/active?code="+user.getCode()+"'>点击激活</a>" ;
        MailUtils.sendMail(user.getEmail(),content,"激活") ;

        return true;
    }

    //激活用户
    @Override
    public boolean active(String code) {
        //1.根据激活查询用户对象
        User user = userDao.findByCode(code);
        if(user != null){
            //2.调用dao层修改激活状态的方法
            //更新用户的状态
            userDao.updateStatus(user);
            return true ;

        }else{
            return false;
        }

    }

    @Override
    public User findByUsernameAndPassword(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword()) ;
    }


}
