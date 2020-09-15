package com.itzixue.blog.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 * @author Mr.Dong
 * @date 2020/5/20 17:11
 **/
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        log.error("Request URL : {} , Exception : {}", request.getRequestURL(), e);

        //如果找到异常就抛出
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", request.getRequestURL());
        mav.addObject("exception", e);
        mav.setViewName("error/error");

        return mav;
    }

}
