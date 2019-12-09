package com.suixingpay.config;

import com.suixingpay.entity.Manager;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 *@Author 孙克强
 */
public class MyHandlerIntercepter implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //获取请求头中的manageId
        String manageId = request.getHeader("manageId");
        //获取session
        HttpSession session = request.getSession();
        //根据manage_id来获取session中的用户
        Manager manager = (Manager) session.getAttribute(manageId);
        if (manager != null) {
            return true;//登录成功，放行
        } else {
            response.sendError(401);
            return false;//登录失败，拦截
        }
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
    }
}
