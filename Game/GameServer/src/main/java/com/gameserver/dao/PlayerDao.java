package com.gameserver.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gameserver.config.heroConfig.GlobalConfig;
import com.gameserver.mapper.PlayerMapper;
import com.gameserver.pojo.db.Player;
import com.gameserver.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
@Slf4j
public class PlayerDao {
    @Autowired
    private PlayerMapper playerMapper;
    @Autowired
    private GlobalConfig globalConfig;

    public Player getInfoByAccId(Integer accountId){
        QueryWrapper<Player> wrapper = new QueryWrapper<>();
        wrapper.eq("account_id",accountId);
        return playerMapper.selectOne(wrapper);
    }
    public Player insertPlayer(Integer accountId) {
        //新玩家的初始值
        Integer initHead = globalConfig.getIntValue(GlobalConfig.PLAYER_INIT_HEAD);
        Integer initLv = globalConfig.getIntValue(GlobalConfig.PLAYER_INIT_LV);
        Integer initCoin = globalConfig.getIntValue(GlobalConfig.PLAYER_INIT_COIN);
        Integer initCheckpoint = globalConfig.getIntValue(GlobalConfig.PLAYER_INIT_CHECKPOINT);

        Player player = new Player();
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.YYYY_MM_DD_HH_MM_SS);
        String initTimeStr = df.format(0);
        player
                .setAccountId(accountId)
                .setCoin(initCoin)
                .setLv(initLv)
                .setNickName("player001")
                .setProfile(initHead)
                .setCheckPoint(initCheckpoint)
                .setLastFreeLotteryTime(initTimeStr);//新player直接可免费抽一次
        playerMapper.insert(player);
        //会给playerId赋值，虽然是数据库中自增
        return player;
    }
    public void updateById(Player player){
        playerMapper.updateById(player);
    }
}
