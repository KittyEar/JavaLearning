package com.gameserver.service;

import com.gameserver.cache.PlayerCache;
import com.gameserver.config.heroConfig.GlobalConfig;
import com.gameserver.dao.PlayerDao;
import com.gameserver.pojo.cache.PlayerInfo;
import com.gameserver.pojo.db.Player;
import com.gameserver.pojo.msg.HttpStatus;
import com.gameserver.pojo.msg.SRet;
import com.gameserver.pojo.msg.server.player.SGetInfo;
import com.gameserver.utils.JwtUtil;
import com.gameserver.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class PlayerService {
    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private PlayerCache playerCache;
    @Autowired
    private HeroService heroService;
    @Autowired
    private GlobalConfig globalConfig;
    @Autowired
    private ItemService itemService;
    public SRet<?> getPlayerInfo(HttpServletRequest request){
        //获取账号id
        Integer accountId = ServletUtils.getAccIdByRequest(request);
        //根据账号id获取player
        Player player = playerDao.getInfoByAccId(accountId);
        if (player == null){
            try {
                player = playerDao.insertPlayer(accountId);
                //新插入后要重新获取，因为playerId是数据库自动生成的
                //player = playerDao.getInfoByAccId(accountId);
                //log.info(player.toString());
            }catch (Exception e){
                SRet.error(HttpStatus.PLAYER_CREATE_ERROR.getCode(), HttpStatus.PLAYER_CREATE_ERROR.getMsg());
            }
            initPlayerItems(player);
        }
        assert player != null;
        initCache(player);
        String playerToken = JwtUtil.createPlayerToken(player.getId());
        SGetInfo sGetInfo = new SGetInfo();
        //两个对象的相同属性进行赋值
        BeanUtils.copyProperties(player,sGetInfo);
        sGetInfo.setNewToken(playerToken);
        return SRet.success(sGetInfo);
    }
    // 处理心跳，
    public void procHeartBeat(Integer playerId){
        //获取cache中的player
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        long curTime = System.currentTimeMillis();
        //更新缓存中的心跳时间
        playerInfo.setLastHeartBeatTime(curTime);
    }
    // 创建角色时初始化用户身上的道具
    private void initPlayerItems(Player player){
        String initItemsStr = globalConfig.getStringValue(GlobalConfig.PLAYER_INIT_ITEMS);
        String[] split = initItemsStr.split(",");

        for (int i=0; i<split.length; i++){
            Integer itemTypeID = Integer.valueOf(split[i]);
            i = i + 1;
            Integer itemNum = Integer.valueOf(split[i]);

            // 增加道具
            itemService.addItemInDB(player.getId(), itemTypeID, itemNum);
        }
    }
    // 增加或删除元宝
    // return: 返回操作结果
    public boolean addCoin(Integer playerId, Integer coin){
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        Player baseProp = playerInfo.getBaseProp();
        Integer curCoin = baseProp.getCoin();

        if (coin < 0){
            // 扣钱
            if (curCoin<-coin){
                // 元宝不足
                return false;
            }
        }else if(coin == 0){
            return true;
        }

        Integer newCoin = curCoin + coin;
        baseProp.setCoin(newCoin);

        return true;
    }
    public Integer getCoin(Integer playerId){
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        return playerInfo.getBaseProp().getCoin();
    }
    //将player从数据库查出后存入缓存
    private void initCache(Player player){
        // 加载用户基础信息到缓存
        PlayerInfo playerInfo = playerCache.getPlayerInfo(player.getId(), false);
        if (playerInfo != null){//player在缓存中，一般出现在快速重新登录的场景中
            return;
        }
        //不在缓存中
        playerInfo = new PlayerInfo();
        playerInfo.setBaseProp(player);

        // 初始化心跳时间
        long curTime = System.currentTimeMillis();
        playerInfo.setLastHeartBeatTime(curTime);

        // 加载用户英雄信息到缓存
        // 调用英雄模块 HeroService的相关方法去加载
        heroService.initCache(playerInfo);
        // 加载道具信息
        itemService.initCache(playerInfo);

        // 加载其它模块,存入缓存
        playerCache.addPlayerInfo(playerInfo);
        log.info(playerInfo.getBaseProp().toString());
    }
}
