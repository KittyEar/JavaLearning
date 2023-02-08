package com.gameserver.service;

import com.gameserver.cache.PlayerCache;
import com.gameserver.config.heroConfig.GlobalConfig;
import com.gameserver.config.heroConfig.HeroConfig;
import com.gameserver.exceptions.gameException.CoinNotEnoughErrorException;
import com.gameserver.exceptions.gameException.tavern.TavernFormatErrorException;
import com.gameserver.exceptions.gameException.tavern.TavernFreeLotteryTimeErrorException;
import com.gameserver.exceptions.gameException.tavern.TavernItemNotEnoughErrorException;
import com.gameserver.pojo.cache.PlayerInfo;
import com.gameserver.pojo.db.Item;
import com.gameserver.pojo.db.Player;
import com.gameserver.pojo.db.mould.HeroMould;
import com.gameserver.pojo.msg.SRet;
import com.gameserver.pojo.msg.server.tavern.*;
import com.gameserver.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class TavernService {

    @Autowired
    private PlayerCache playerCache;

    @Autowired
    private GlobalConfig globalConfig;

    @Autowired
    private HeroConfig heroConfig;

    @Autowired
    private HeroService heroService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ItemService itemService;
    // 打开酒馆获取基础信息
    public SRet<?> getTavernInfo(Integer playerId){
//        PlayerInfo playerInfo = 获取内存用户结构;
//        disFreeTime = 获取免费抽取配置时间间隔(秒);
//        Integer 免费抽卡倒计时（单位：秒） = playerInfo.getLastFreeLotteryTime() + disFreeTime - curTime;
//        if (免费抽卡倒计时<0){
//            免费抽卡倒计时 = 0;
//        }
//
//        // todo: 查询卷轴道具数量，等有道具系统了再来加，先写0
//        SRet.success(...);
        Integer lastFreeTime = getLastFreeLotteryTime(playerId);
        if (lastFreeTime<0) {
            lastFreeTime = 0;
        }

        // todo: 查询卷轴道具数量，等有道具系统了再来加，先写0

        SGetTavernInfoMsg sGetTavernInfoMsg = new SGetTavernInfoMsg();
        sGetTavernInfoMsg.setLastFreeTime(lastFreeTime);
        sGetTavernInfoMsg.setItemNum(0);
        return SRet.success(sGetTavernInfoMsg);
    }


    // 免费单抽
    public SRet<?> freeLottery(Integer playerId){
        Integer lastFreeTime = getLastFreeLotteryTime(playerId);
        if (lastFreeTime>0) {
            // 时间还没有到
            throw new TavernFreeLotteryTimeErrorException();
        }

        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        Player baseProp = playerInfo.getBaseProp();

        // 设置免费抽奖时间为当前时间
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.YYYY_MM_DD_HH_MM_SS);
        String curTimeStr = df.format(new Date());
        baseProp.setLastFreeLotteryTime(curTimeStr);

        // 根据配置的权重随机获取英雄typeId
        Integer heroTypeId = randHeroTypeId();

        // 创建英雄
        heroService.createHero(playerId, heroTypeId);

        SFreeLotteryMsg sFreeLotteryMsg = new SFreeLotteryMsg();
        sFreeLotteryMsg.setHeroTypeId(heroTypeId);
        return SRet.success(sFreeLotteryMsg);
    }

    // 获取免费单抽的倒计时
    private Integer getLastFreeLotteryTime(Integer playerId){
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        Integer freeDisLotteryTime = globalConfig.getIntValue(GlobalConfig.FREE_LOTTERY_TIME);
        String lastFreeLotteryTime = playerInfo.getBaseProp().getLastFreeLotteryTime();

        SimpleDateFormat df = new SimpleDateFormat(DateUtils.YYYY_MM_DD_HH_MM_SS);
        long lastFreeLotteryUnixTime = 0;
        try {
            lastFreeLotteryUnixTime = df.parse(lastFreeLotteryTime).getTime()/1000;
        }catch (Exception e){
            log.error("getTavernInfo error: playerId=" + playerId + " lastFreeLotteryTime=" + lastFreeLotteryTime);
            throw new TavernFormatErrorException();
        }

        Date date = new Date();
        long curTime = date.getTime()/1000;

        long lastFreeTime = lastFreeLotteryUnixTime + freeDisLotteryTime - curTime;
        return (int)lastFreeTime;
    }

    // 元宝单抽
    public SRet<?> oneCoinLottery(Integer playerId){
        // 判断用户元宝并扣除
        Integer oneLotteryCoin = globalConfig.getIntValue(GlobalConfig.ONE_LOTTERY_COIN);
        boolean b = playerService.addCoin(playerId, -oneLotteryCoin);
        if (!b){
            throw new CoinNotEnoughErrorException();
        }

        // 根据配置的权重随机获取英雄typeId
        Integer heroTypeId = randHeroTypeId();
        // 创建英雄
        heroService.createHero(playerId, heroTypeId);

        SOneCoinLotteryMsg sOneCoinLotteryMsg = new SOneCoinLotteryMsg();
        sOneCoinLotteryMsg.setHeroTypeId(heroTypeId);
        sOneCoinLotteryMsg.setNewCoin(playerService.getCoin(playerId));
        return SRet.success(sOneCoinLotteryMsg);
    }

    // 元宝十连抽
    public SRet<?> tenCoinLottery(Integer playerId){
        // 判断用户元宝并扣除
        Integer tenLotteryCoin = globalConfig.getIntValue(GlobalConfig.TEN_LOTTERY_COIN);
        boolean b = playerService.addCoin(playerId, -tenLotteryCoin);
        if (!b){
            throw new CoinNotEnoughErrorException();
        }

        // 根据权重随机获取英雄
        List<Integer> heroTypeIdList = tenLottery(playerId);

        STenCoinLotteryMsg sTenCoinLotteryMsg = new STenCoinLotteryMsg();
        sTenCoinLotteryMsg.setHeroTypeIdList(heroTypeIdList);
        sTenCoinLotteryMsg.setNewCoin(playerService.getCoin(playerId));
        return SRet.success(sTenCoinLotteryMsg);
    }

    // 道具单抽
    public SRet<?> oneItemLottery(Integer playerId){
        // todo: 判断用户抽奖卷轴道具是否足够
        Integer itemTypeId = globalConfig.getIntValue(GlobalConfig.LOTTERY_ITEM_TYPE_ID);
        Item item = itemService.getItemByTypeId(playerId, itemTypeId);
        boolean b = itemService.delItem(item, 1);
        if (!b){
            throw new TavernItemNotEnoughErrorException();
        }
        // 根据配置的权重随机获取英雄typeId
        Integer heroTypeId = randHeroTypeId();
        // 创建英雄
        heroService.createHero(playerId, heroTypeId);

        SOneItemLotteryMsg sOneItemLotteryMsg = new SOneItemLotteryMsg();
        sOneItemLotteryMsg.setHeroTypeId(heroTypeId);

        // todo: 道具系统制作完成后修改
        sOneItemLotteryMsg.setNewItemNum(item.getNum());
        return SRet.success(sOneItemLotteryMsg);
    }

    // 道具十连抽
    public SRet<?> tenItemLottery(Integer playerId){
        // todo: 判断用户抽奖卷轴道具是否足够
        Integer itemTypeId = globalConfig.getIntValue(GlobalConfig.LOTTERY_ITEM_TYPE_ID);
        Item item = itemService.getItemByTypeId(playerId, itemTypeId);
        boolean b = itemService.delItem(item, 10);
        if (!b){
            throw new TavernItemNotEnoughErrorException();
        }
        // 根据权重随机获取英雄
        List<Integer> heroTypeIdList = tenLottery(playerId);

        STenItemLotteryMsg sTenItemLotteryMsg = new STenItemLotteryMsg();
        sTenItemLotteryMsg.setHeroTypeIdList(heroTypeIdList);
        // todo: 道具系统制作完成后修改
        sTenItemLotteryMsg.setNewItemNum(item.getNum());
        return SRet.success(sTenItemLotteryMsg);
    }

    // 十连抽并创建英雄
    private List<Integer> tenLottery(Integer playerId){
        List<Integer> heroTypeIdList = new ArrayList<>();
        for (int i=0; i<10; i++){
            Integer heroTypeId = randHeroTypeId();
            // 创建英雄
            heroService.createHero(playerId, heroTypeId);
            heroTypeIdList.add(heroTypeId);
        }

        return heroTypeIdList;
    }
    /**
     *   @return 随机的英雄模板id
     */
    private Integer randHeroTypeId(){
        // 遍历所有英雄模板，计算总权重
        List<HeroMould> lists = heroConfig.getHeroMouldList();
        Integer allWeight = heroConfig.getAllWeight();
        Random random = new Random();
        // 生成0~allWeight-1随机权重值
        int rand = random.nextInt(allWeight);

        Integer heroTypeId = 0;
        Integer heroWeightB = 0;
        // 再次遍历所有英雄，根据随机权重值，获取在此范围内的英雄模板id
        for (HeroMould heroMould : lists) {
            Integer weight = heroMould.getLotteryWeight();
            if (heroWeightB <= rand && rand < heroWeightB + weight) {
                // 抽中的是这个英雄
                heroTypeId = heroMould.getId();
                break;
            }
            heroWeightB += weight;
        }
        return heroTypeId;
    }
}

