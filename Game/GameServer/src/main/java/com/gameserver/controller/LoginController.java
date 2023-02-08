package com.gameserver.controller;

import com.gameserver.pojo.msg.SRet;
import com.gameserver.pojo.msg.client.login.CLoginMsg;
import com.gameserver.pojo.msg.client.login.CRegMsg;
import com.gameserver.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("/toLogin")
public class LoginController {
    @Autowired
    private LoginService loginService;
    // 注册
    @PostMapping("/register")
    @ResponseBody
    public SRet<?> reg(@RequestBody CRegMsg cRegMsg){
        // 调用LoginService中的reg方法
//        返回值SRegMsg = loginService.reg(账号，密码);
        return loginService.reg(cRegMsg.getAccount(), cRegMsg.getPassword());
    }

    // 登录
    @PostMapping("/login")
    @ResponseBody
    public SRet<?> login(@RequestBody CLoginMsg cLoginMsg){
        // 调用LoginService中的login方法
        return loginService.login(cLoginMsg.getAccount(), cLoginMsg.getPassword());
    }
}
