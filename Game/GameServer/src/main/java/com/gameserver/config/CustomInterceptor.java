package com.gameserver.config;

import com.gameserver.pojo.msg.HttpStatus;
import com.gameserver.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class CustomInterceptor implements HandlerInterceptor {

    @Autowired
    private GlobalService globalService;

    //拦截策略，返回false就拦截，返回true 就不拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (globalService.isClose){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");

            JSONObject retJson = new JSONObject();
            retJson.put("code", HttpStatus.SERVER_CLOSE.getCode());
            retJson.put("msg", HttpStatus.SERVER_CLOSE.getMsg());
            PrintWriter writer = response.getWriter();
            writer.append(retJson.toString());
            return false;
        }
        return true;
    }
}