package com.gameserver.service;

import com.gameserver.cache.PlayerCache;
import com.gameserver.config.heroConfig.GlobalConfig;
import com.gameserver.config.heroConfig.HeroConfig;
import com.gameserver.dao.HeroDao;
import com.gameserver.exceptions.gameException.hero.*;
import com.gameserver.pojo.cache.PlayerInfo;
import com.gameserver.pojo.db.Hero;
import com.gameserver.pojo.db.mould.HeroMould;
import com.gameserver.pojo.msg.SRet;
import com.gameserver.pojo.msg.server.hero.SGetHeroListMsg;
import com.gameserver.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class HeroService {
    @Autowired
    private HeroDao heroDao;
    @Autowired
    private HeroConfig heroConfig;
    @Autowired
    private PlayerCache playerCache;
    @Autowired
    private GlobalConfig globalConfig;
    @Autowired
    private ItemService itemService;
    // 从数据库中加载数据到内存
    public void initCache(PlayerInfo playerInfo){
        // 访问数据库，获取用户英雄信息
        List<Hero> heroes = heroDao.getHeroes(playerInfo);
        // 将英雄数据放入缓存中
        playerInfo.setHeroes(heroes);
    }
    // 创建英雄
    public void createHero(Integer playerId, Integer typeId){
        HeroMould heroMould = heroConfig.getHeroMould(typeId);//根据模板id获取模板
        if (heroMould==null){
            throw new HeroMouldIdErrorException();
        }
        // 根据模板为player创建新英雄
        Hero hero = new Hero();
        hero.setPlayerId(playerId);
        hero.setHeroName(heroMould.getName());
        hero.setAtt(heroMould.getInitAtt());
        hero.setDef(heroMould.getInitDef());
        hero.setMaxHp(heroMould.getInitMaxHp());
        hero.setLv(1);
        hero.setStar(1);
        hero.setTypeId(typeId);


        // 先保存数据到数据库
        try {
            heroDao.insertHero(hero);
        }catch (Exception e){
            log.error("createHero error: playerId=" + playerId+ " typeId="+typeId);
            throw new HeroCreateErrorException();
        }

        //查找内存中的player
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        // 增加数据到内存
        playerInfo.getHeroes().add(hero);
    }
    // 获取英雄列表
    public SRet<SGetHeroListMsg> getHeroList(Integer playerId){
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Hero> heroes = playerInfo.getHeroes();
        SGetHeroListMsg sGetHeroListMsg = new SGetHeroListMsg();
        sGetHeroListMsg.setHeroList(heroes);
        return SRet.success(sGetHeroListMsg);
    }
    // 升级
    public SRet<Hero> lvUp(Integer playerId, Integer heroId){
        //持有着cache中hero的引用，所以不需要手动添加入cache的操作
        Hero hero = getHero(playerId, heroId);

        // 每星提升20级等级上限
        Integer maxLv = hero.getStar() * globalConfig.getIntValue(GlobalConfig.STAR_LV);
        if (hero.getLv()>=maxLv){//超上限
            throw new HeroLvMaxErrorException();
        }

        // todo:判断道具是否够，执行后续道具逻辑，后续实现
        // 消耗道具升级
        Integer needExpItemNum = hero.getLv()*100;//升级道具递增，一般位升级当前等级的100倍
        Integer expItemTypeId = globalConfig.getIntValue(GlobalConfig.EXP_ITEM_TYPE_ID);
        // 消耗对应玩家对应的道具类型（经验类型）
        boolean b = itemService.delItemByTypeId(playerId, expItemTypeId, needExpItemNum);
        if (!b){
            throw new HeroExpNotEnoughErrorException();
        }

        HeroMould heroMould = heroConfig.getHeroMould(hero.getTypeId());

        // 游戏升级逻辑
        hero.setLv(hero.getLv() + 1);
        hero.setAtt(hero.getAtt() + heroMould.getLvUpAtt());
        hero.setDef(hero.getDef() + heroMould.getLvUpDef());
        hero.setMaxHp(hero.getMaxHp() + heroMould.getLvUpMaxHp());

        return SRet.success(hero);
    }
    // 升星
    public SRet<Hero> starUp(Integer playerId, Integer heroId, Integer consumeHeroId1, Integer consumeHeroId2, Integer consumeHeroId3) {
        Hero hero = getHero(playerId, heroId);

        Integer maxStar = globalConfig.getIntValue(GlobalConfig.MAX_STAR);
        if (hero.getStar() >= maxStar){
            throw new HeroStarMaxErrorException();
        }

        // todo:判断道具是否够，执行后续道具逻辑
        Integer needStarStoneItemNum = hero.getStar()*10;
        Integer starStoneItemTypeId = globalConfig.getIntValue(GlobalConfig.STAR_STONE_ITEM_TYPE_ID);
        boolean b = itemService.delItemByTypeId(playerId, starStoneItemTypeId, needStarStoneItemNum);
        if (!b){
            throw new HeroStarStoneNotEnoughErrorException();
        }
        // 判断英雄是否足够，要消耗一个同星本体，以及两个同星狗粮(其它任意英雄)
        Hero heroConsume1 = getHero(playerId, consumeHeroId1);
        Hero heroConsume2 = getHero(playerId, consumeHeroId2);
        Hero heroConsume3 = getHero(playerId, consumeHeroId3);
        if (!Objects.equals(heroConsume1.getTypeId(), hero.getTypeId()) || !Objects.equals(heroConsume1.getStar(), hero.getStar()) ||
                !Objects.equals(heroConsume2.getStar(), hero.getStar()) || !Objects.equals(heroConsume3.getStar(), hero.getStar())){
            throw new HeroStarUpMaterialErrorException();
        }
        // 删除数据库英雄信息
        try {
            heroDao.deleteHeroById(consumeHeroId1);
            heroDao.deleteHeroById(consumeHeroId2);
            heroDao.deleteHeroById(consumeHeroId3);
        }catch (Exception e){
            log.error("starUp error: playerId=" + playerId+ " heroId="+heroId+ " consumeHeroId1="+consumeHeroId1+ " consumeHeroId2="+consumeHeroId2+ " consumeHeroId3="+consumeHeroId3);
            throw new HeroStarUpDelErrorException();
        }
        // 删除内存英雄信息
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Hero> heroes = playerInfo.getHeroes();
        heroes.remove(heroConsume1);
        heroes.remove(heroConsume2);
        heroes.remove(heroConsume3);

        // 修改内存，为hero升星
        hero.setStar(hero.getStar() + 1);
        return SRet.success(hero);
    }
    //从内存中获取信息
    private Hero getHero(Integer playerId, Integer heroId){
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        List<Hero> heroes = playerInfo.getHeroes();
        for (Hero hero : heroes) {
            if (Objects.equals(hero.getId(), heroId)) {
                return hero;
            }
        }
        throw new HeroIdErrorException();
    }
}
