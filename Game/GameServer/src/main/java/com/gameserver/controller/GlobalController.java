package com.gameserver.controller;

import com.gameserver.cache.PlayerCache;
import com.gameserver.pojo.msg.HttpStatus;
import com.gameserver.pojo.msg.SRet;
import com.gameserver.pojo.msg.client.global.CCloseServerMsg;
import com.gameserver.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

//关服
@RestController("/global")
public class GlobalController {
    private final String CLOSE_SERVER_PASSWORD = "HDosdfjoi*((&#$*(_jsldf702314sfd";
    @Autowired
    GlobalService globalService;

    @Autowired
    PlayerCache playerCache;

    @PostMapping("/closeSever")
    public SRet<?> closeSever(HttpServletRequest request, @RequestBody CCloseServerMsg cCloseServerMsg){
        if (cCloseServerMsg.getPwd().equals(CLOSE_SERVER_PASSWORD)){
            // 设置服务器状态为已经关闭
            globalService.isClose = true;

            // 将内存数据保存在数据库中
            playerCache.saveAll();
            return SRet.success();
        }
        return SRet.error(HttpStatus.CLOSE_SERVER_PWD_ERROR.getCode(), HttpStatus.CLOSE_SERVER_PWD_ERROR.getMsg());
    }
}
