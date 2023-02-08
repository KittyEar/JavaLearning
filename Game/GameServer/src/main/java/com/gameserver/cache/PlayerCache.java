package com.gameserver.cache;

import com.gameserver.dao.HeroDao;
import com.gameserver.dao.ItemDao;
import com.gameserver.dao.PlayerDao;
import com.gameserver.exceptions.gameException.NotLoginErrorException;
import com.gameserver.pojo.cache.PlayerInfo;
import com.gameserver.pojo.db.Hero;
import com.gameserver.pojo.db.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

//默认单例模式，不会被GC，bean和spring用map存储，k为强引用（我猜是线程），v是bean。
@Component
@Slf4j
public class PlayerCache {
    // 线程安全的hashmap，将player_id与用户内存属性做映射，所以还要创建PlayerInfo结构
    private ConcurrentHashMap<Integer, PlayerInfo> playerInfos = new ConcurrentHashMap<Integer, PlayerInfo>();

    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private HeroDao heroDao;
    @Autowired
    private ItemDao itemDao;
//    @Autowired
//    PlayerCache playerCache;

    public PlayerInfo getPlayerInfo(Integer playerId){
        return getPlayerInfo(playerId, true);
    }
                                                      //是否将异常抛给客户端
    public PlayerInfo getPlayerInfo(Integer playerId, boolean throwException){
        PlayerInfo playerInfo = playerInfos.get(playerId);
        if (playerInfo==null && throwException){
            throw new NotLoginErrorException();
        }
        return playerInfo;
    }

    public ConcurrentHashMap<Integer, PlayerInfo> getPlayerInfos(){
        return playerInfos;
    }
    //信息入缓存
    public void addPlayerInfo(PlayerInfo playerInfo){
        this.playerInfos.put(playerInfo.getBaseProp().getId(), playerInfo);
    }

    //缓存入库
    public void saveOne(PlayerInfo playerInfo){
        // 保存用户基础属性
        playerDao.updateById(playerInfo.getBaseProp());

        // 保存英雄属性
        List<Hero> heroes = playerInfo.getHeroes();
        for (Hero hero : heroes) {
            heroDao.updateById(hero);
        }
        //保存道具
        List<Item> items = playerInfo.getItems();
        for (Item item : items){
            itemDao.updateItemById(item);
        }
    }
    public void saveAll(){
        for (PlayerInfo playerInfo : playerInfos.values()){
            saveOne(playerInfo);
        }
    }
}

