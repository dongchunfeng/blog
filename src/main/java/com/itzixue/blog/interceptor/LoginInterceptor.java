package com.itzixue.blog.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mr.Dong
 * @date 2020/5/21 18:24
 **/
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }

    //postHandler是在请求结束之后,视图渲染之前执行的,但只有preHandle方法返回true的时候才会执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }


    //afterCompletion是视图渲染完成之后才执行,同样需要preHandle返回true，
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //该方法通常用于清理资源等工作
    }

}
