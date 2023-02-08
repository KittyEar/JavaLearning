package com.gameserver.controller;

import com.gameserver.pojo.msg.SRet;
import com.gameserver.service.PlayerService;
import com.gameserver.utils.ServletUtils;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping("/getPlayerInfo")
    @ResponseBody
    @ApiImplicitParam(name = "token", paramType = "header",dataType = "String")
    public SRet<?> getInfo(HttpServletRequest request){
          return playerService.getPlayerInfo(request);
    }
    @PostMapping("/heartBeat")//需要客户端定时发送心跳请求
    @ApiImplicitParam(name = "token", paramType = "header",dataType = "String")
    public SRet<?> heartBeat(HttpServletRequest request){
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        playerService.procHeartBeat(playerId);
        return SRet.success();
    }
}
