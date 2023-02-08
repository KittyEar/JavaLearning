package com.gameserver.controller;

import com.gameserver.pojo.msg.SRet;
import com.gameserver.service.TavernService;
import com.gameserver.utils.ServletUtils;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

//酒馆系统
@RestController("/tavern")
public class TavernController {

    @Autowired
    private TavernService tavernService;

    @PostMapping("/getTavernInfo")
    @ApiImplicitParam(name = "token", paramType = "header",dataType = "String")
    public SRet<?> getTavernInfo(HttpServletRequest request){
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        return tavernService.getTavernInfo(playerId);
    }

    @PostMapping("/freeLottery")
    @ApiImplicitParam(name = "token", paramType = "header",dataType = "String")
    public SRet<?> freeLottery(HttpServletRequest request){
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        return tavernService.freeLottery(playerId);
    }

    @PostMapping("/oneCoinLottery")
    @ApiImplicitParam(name = "token", paramType = "header",dataType = "String")
    public SRet<?> oneCoinLottery(HttpServletRequest request){
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        return tavernService.oneCoinLottery(playerId);
    }

    @PostMapping("/tenCoinLottery")
    @ApiImplicitParam(name = "token", paramType = "header",dataType = "String")
    public SRet<?> tenCoinLottery(HttpServletRequest request){
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        return tavernService.tenCoinLottery(playerId);
    }

    @PostMapping("/oneItemLottery")
    @ApiImplicitParam(name = "token", paramType = "header",dataType = "String")
    public SRet<?> oneItemLottery(HttpServletRequest request){
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        return tavernService.oneItemLottery(playerId);
    }

    @PostMapping("/tenItemLottery")
    @ApiImplicitParam(name = "token", paramType = "header",dataType = "String")
    public SRet<?> tenItemLottery(HttpServletRequest request){
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        return tavernService.tenItemLottery(playerId);
    }
}

