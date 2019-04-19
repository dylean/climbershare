package com.anxi.climbershare.interceptor;

import com.anxi.climbershare.dao.LoginTicketDao;
import com.anxi.climbershare.dao.UserDao;
import com.anxi.climbershare.pojo.HostHolder;
import com.anxi.climbershare.pojo.LoginTicket;
import com.anxi.climbershare.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Author:AnXi
 * 拦截器，用于验证用户身份
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {


    @Autowired
    private LoginTicketDao loginTicketDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        if (httpServletRequest.getCookies() != null) {
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();

                    break;
                }
            }
        }

        if (ticket != null) {//找到ticket
            LoginTicket loginTicket = loginTicketDao.selectByTicket(ticket);
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                //失效返回
                return true;
            }
            int  id = loginTicket.getUserId();
            User user = userDao.selectById(id);

            //将用户信息放入上下文
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && hostHolder.getUser() != null) {
            //送出用户信息
            //保证验证成功的时候，所有页面都有用户信息保持
            User user = hostHolder.getUser();
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

        //清空
        hostHolder.clear();
    }
}
