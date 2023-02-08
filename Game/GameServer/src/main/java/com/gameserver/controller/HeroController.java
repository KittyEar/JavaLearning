package com.gameserver.controller;

import com.gameserver.pojo.db.Hero;
import com.gameserver.pojo.msg.SRet;
import com.gameserver.pojo.msg.client.hero.CCreateHeroMsg;
import com.gameserver.pojo.msg.client.hero.CHeroLvUpMsg;
import com.gameserver.pojo.msg.client.hero.CHeroStarUpMsg;
import com.gameserver.service.HeroService;
import com.gameserver.utils.DateUtils;
import com.gameserver.utils.ServletUtils;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;


@Slf4j
@RestController("/hero")
public class HeroController {
    @Autowired
    private HeroService heroService;
    @PostMapping("/create")
    @ApiImplicitParam(name = "token", paramType = "header",dataType = "String")
    public SRet<?> create(HttpServletRequest request, @RequestBody CCreateHeroMsg cCreateHeroMsg){
//        调用HeroService的createHero方法
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        heroService.createHero(playerId, cCreateHeroMsg.getHeroTypeId());
        return SRet.success();
    }

    @PostMapping("/getHeroList")
    @ApiImplicitParam(name = "token", paramType = "header",dataType = "String")
    public SRet<?> getHeroLists(HttpServletRequest request){
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
//        通过heroService.getHeroList获取列表,在内存中
        return heroService.getHeroList(playerId);
    }
    @PostMapping("/lvUp")
    @ApiImplicitParam(name = "token", paramType = "header",dataType = "String")//要传英雄id
    public SRet<Hero> lvUp(HttpServletRequest request, @RequestBody CHeroLvUpMsg cHeroLvUpMsg){
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        return heroService.lvUp(playerId, cHeroLvUpMsg.getHeroId());
    }
    @PostMapping("/starUp")
    @ApiImplicitParam(name = "token", paramType = "header",dataType = "String")
    public SRet<Hero> starUp(HttpServletRequest request, @RequestBody CHeroStarUpMsg cHeroStarUpMsg){
        Integer playerId = ServletUtils.getPlayerIdByRequest(request);
        int[] consumeHeroId = new int[3];
        consumeHeroId[0] = cHeroStarUpMsg.getConsumeHeroId1();//消耗品id，其实就是英雄id。
        consumeHeroId[1] = cHeroStarUpMsg.getConsumeHeroId2();
        consumeHeroId[2] = cHeroStarUpMsg.getConsumeHeroId3();
        return heroService.starUp(playerId, cHeroStarUpMsg.getHeroId(), consumeHeroId[0], consumeHeroId[1], consumeHeroId[2]);
    }
    @PostMapping("/test")
    public String test(){
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(String.valueOf(new SimpleDateFormat(DateUtils.YYYY_MM_DD_HH_MM_SS)));
        return "test";
    }
}
