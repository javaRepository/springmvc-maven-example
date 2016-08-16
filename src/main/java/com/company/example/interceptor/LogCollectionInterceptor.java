package com.company.example.interceptor;

import com.company.example.stat.LogStatService;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Repository
public class LogCollectionInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private String staticSourcePath;

    @Resource
    private LogStatService logStatService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getServletPath();//当前访问路径
        //日志记录做统计访问量
        logStatService.logInfo(url);

        request.setAttribute("staticSourcePath", staticSourcePath);
        return true;
    }

}
